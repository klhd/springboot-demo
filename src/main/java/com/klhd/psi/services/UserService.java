package com.klhd.psi.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.common.Constants;
import com.klhd.psi.common.DigestUtil;
import com.klhd.psi.common.TokenUtil;
import com.klhd.psi.config.MyProps;
import com.klhd.psi.config.redis.RedisUtil;
import com.klhd.psi.dao.*;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.user.*;
import io.swagger.annotations.*;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cheng on 2017/9/25.
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户管理")
@ControllerPermission(code="user", desc="用户管理")
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MyProps myProps;
    @Value("${test.fileName}")
    private String from;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private PrivilegeDao privilegeDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserExtendDao userExtendDao;
    @Autowired
    private UserOrgDao userOrgDao;
    @Autowired
    private UserJobDao userJobDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @ApiOperation("获取用户基本信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户登录成功"),
    })
    @RequestMapping(value = "/base", method = RequestMethod.POST)
    @ApiImplicitParams({})
    public ResultVO base() throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        UserVO currentUser = (UserVO)redisUtil.get(baseUserService.getCurrentToken());
        if(currentUser == null){
            resultVO.setCode(401);
            resultVO.setMessage("您没有登录，请登录后操作。");
            return resultVO;
        }
        //获取用户角色
        //获取用户权限
        List<PrivilegeVO> userPrivList = userExtendDao.getUserPrivList(currentUser.getId());
        //获取用户菜单
        List<Menu> userMenuList = userExtendDao.getUserMenuList(currentUser.getId());

        UserExtVO userExtVO = JSONObject.parseObject(JSON.toJSONString(currentUser), UserExtVO.class);
        userExtVO.setMenuList(userMenuList);
        userExtVO.setPermissionList(userPrivList);

        //更改缓存的对象
        baseUserService.restore(userExtVO);

        Map<String, Object> data = Maps.newHashMap();
        currentUser.setPassword(null);
        data.put("userInfo", currentUser);
        data.put("menu", userMenuList);
        data.put("permission", userPrivList);
        data.put("token", baseUserService.getCurrentToken());
        resultVO.setResult(data);
        return resultVO;
    }

    @ApiOperation("用户登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户登录成功"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVO login(@RequestBody @ApiParam(name = "账号和密码", value = "userAccount、password", defaultValue = "{\"userAccount\":\"admin\",\"password\":\"admin\"}", required = true) Map<String, String> map) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        String userAccout = map.get("userAccount");
        String password = map.get("password");
        if(Strings.isBlank(userAccout)){
            resultVO.setCode(501);
            resultVO.setMessage("用户账号不可以为空");
            return resultVO;
        }
        if(Strings.isBlank(password)){
            resultVO.setCode(502);
            resultVO.setMessage("密码不可以为空");
            return resultVO;
        }

        UserVOQuery userQuery = new UserVOQuery();
        userQuery.createCriteria().andUserAccountEqualTo(userAccout);
        userQuery.createCriteria().andPasswordEqualTo(DigestUtil.SHA256(password));
        List<UserVO> list = userDao.selectByExample(userQuery);
        UserVO user = null;
        if (list != null && list.size() > 0) {
            user = list.get(0);
        }else{
            //用户名或者密码不对
            resultVO.setCode(500);
            resultVO.setMessage("用户名或者密码不对");
            return resultVO;
        }

        String token = TokenUtil.createToken();
        redisUtil.set(token, user, Constants.SESSION_TIME);

        Cookie cookie = new Cookie("sso_id", token);
        cookie.setPath("/");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        Map<String, Object> data = Maps.newHashMap();
        user.setPassword(null);
        data.put("userInfo", user);
        data.put("token", token);
        resultVO.setMessage("登录成功");
        resultVO.setResult(data);
        return resultVO;
    }

    @ApiOperation("修改密码")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 500, message = "原密码输入不正确"),
    })
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public ResultVO changePwd(
            @RequestBody @ApiParam(name = "用户对象", value = "必传:oldPwd, newPwd", required = true) UserExtVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();

        UserExtVO currentUser = baseUserService.getCurrentUser();
        if(currentUser.getPassword().equals(DigestUtil.SHA256(record.getOldPwd()))){
            UserVO vo = new UserVO();
            vo.setId(currentUser.getId());
            vo.setPassword(DigestUtil.SHA256(record.getNewPwd()));
            userDao.updateByPrimaryKeySelective(vo);
            resultVO.setMessage("修改成功");
            //更新缓存
            currentUser.setPassword(vo.getPassword());
            baseUserService.restore(currentUser);
        }else{
            resultVO.setCode(500);
            resultVO.setMessage("旧密码输入不对，请重新输入");
        }
        return resultVO;
    }

    @ApiOperation("创建")
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功"),
    })
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Permission(code = "create", desc = "创建")
    public ResultVO create(
            @RequestBody @ApiParam(name = "角色对象", value = "传入:name", required = true) UserVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        String pwd = record.getPassword();
        pwd = DigestUtil.SHA256(pwd);
        record.setPassword(pwd);
        userDao.insert(record);
        return resultVO;
    }

    @ApiOperation("删除")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Permission(code = "delete", desc = "删除")
    public ResultVO delete(
            @RequestBody @ApiParam(name = "角色对象", value = "传入:id", required = true) List<UserVO> list
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        List<Integer> idList = list.stream().map(t -> t.getId()).collect(Collectors.toList());

        UserVOQuery query = new UserVOQuery();
        query.createCriteria().andIdIn(idList);
        userDao.deleteByExample(query);

        UserRoleVOQuery q2 = new UserRoleVOQuery();
        q2.createCriteria().andUserIdIn(idList);
        userRoleDao.deleteByExample(q2);

        return resultVO;
    }

    @ApiOperation("修改")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(code = "update", desc = "修改")
    public ResultVO update(
            @RequestBody @ApiParam(name = "用户对象", value = "必传:id", required = true) UserVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        userDao.updateByPrimaryKeySelective(record);
        return resultVO;
    }

    @ApiOperation("获取用户明细")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    @Permission(code = "getById", desc = "获取用户明细")
    public ResultVO getById(
            @RequestBody @ApiParam(name = "用户对象", value = "必传:id", required = true) UserVO record
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        UserVO userVO = userDao.selectByPrimaryKey(record.getId());
        userVO.setPassword(null);
        resultVO.setResult(userVO);
        return resultVO;
    }


    @ApiOperation("设置用户的角色")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
    })
    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    @Permission(code = "saveUserRole", desc = "设置用户的角色")
    public ResultVO saveUserRole(
            @RequestBody @ApiParam(name = "角色关联对象", value = "必传:userId、roleId", required = true) List<UserRoleVO> list
    ) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        list.forEach(e -> userRoleDao.insert(e));
        return resultVO;
    }

}
