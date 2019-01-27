package com.klhd.psi.services;

import com.google.common.collect.Maps;
import com.klhd.psi.annotation.Cache;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.common.TokenUtil;
import com.klhd.psi.config.MyProps;
import com.klhd.psi.config.redis.RedisUtil;
import com.klhd.psi.dao.*;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.privilege.PrivilegeVO;
import com.klhd.psi.vo.user.UserVO;
import com.klhd.psi.vo.user.UserVOQuery;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    private UserDeptDao userDeptDao;
    @Autowired
    private UserJobDao userJobDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @ApiOperation("用户登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户登录成功"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="username",dataType="String",required=true,value="用户的姓名",defaultValue=""),
            @ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="用户的密码",defaultValue="")
    })
    @Permission(code="login", desc="用户登录")
    @Cache(key = "user_key", expire = 11111)
    public ResultVO login(@RequestParam Map<String, String> map) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();

        UserVOQuery userQuery = new UserVOQuery();
        userQuery.createCriteria().andIdEqualTo(1);
        List<UserVO> list = userDao.selectByExample(userQuery);
        UserVO user = null;
        if (list != null && list.size() > 0) {
            user = list.get(0);
        }else{
            //用户名或者密码不对
        }

        String token = TokenUtil.createToken();
        RedisUtil.setValue(token, "-");
        Cookie cookie = new Cookie("sso_id", token);
        cookie.setPath("/");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);

        //获取用户角色
        //获取用户权限
        List<PrivilegeVO> userPrivList = userExtendDao.getUserPrivList(user.getId());
        //获取用户菜单
        List<Menu> userMenuList = userExtendDao.getUserMenuList(user.getId());


//        userVODao.insert(userVO);

//        System.out.println(menuDao);
//        System.out.println(from);
//        if(1 == 1)
//            throw new IllegalArgumentException("sdsdsdsd");
//        redisUtil.set("test", "value", 1000);
//        resultVO.setResult(redisUtil.get("test"));
        baseUserService.getCurrentUser();
        Map<String, Object> data = Maps.newHashMap();
        user.setPassword(null);
        data.put("userInfo", user);
        data.put("menu", userMenuList);
        data.put("permission", userPrivList);
        data.put("token", token);
        resultVO.setResult(data);
        return resultVO;
    }

    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    @Permission(desc = "用户a", code = "user1")
    public void a(){
        logger.info("2222222222222222");
    }

}
