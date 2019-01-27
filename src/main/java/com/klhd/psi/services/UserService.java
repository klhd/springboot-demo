package com.klhd.psi.services;

import com.klhd.psi.annotation.Cache;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.common.TokenUtil;
import com.klhd.psi.config.MyProps;
import com.klhd.psi.config.redis.RedisUtil;
import com.klhd.psi.dao.*;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.menu.MenuQuery;
import com.klhd.psi.vo.user.UserVO;
import com.klhd.psi.vo.user.UserVOQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2017/9/25.
 */
@RestController
@RequestMapping("/user")
@ControllerPermission(code="aaa", desc="用户管理")
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
    private UserDeptDao userDeptDao;
    @Autowired
    private UserJobDao userJobDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @RequestMapping("/login")
    @Permission(code="login", desc="用户登录")
    @Cache(key = "user_key", expire = 11111)
    public ResultVO login(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) throws Exception {
        ResultVO resultVO = ResultVO.getInstance();
        UserVO userVO = new UserVO();
        userVO.setAddress("2323");
        userVO.setUserAccount("232332");

        Menu menu = new Menu();
        menu.setName("aaaa");
//        menuDao.insert(menu);
        List<Menu> menus = menuDao.selectByExample(new MenuQuery());
        MenuQuery menuQuery = new MenuQuery();
        menuQuery.createCriteria().andNameLike("%a%");
        List<Menu> menus2 = menuDao.selectByExample(menuQuery);
        logger.info("menu", menus);
        resultVO.setResult(menus2);

        UserVOQuery userQuery = new UserVOQuery();
        userQuery.createCriteria().andIdEqualTo(1);
        List<UserVO> userVOS = userDao.selectByExample(userQuery);
        System.out.println(TokenUtil.createToken());
        String token = TokenUtil.createToken();
        RedisUtil.setValue(token, "-");
        Cookie cookie = new Cookie("sso_id", token);
        cookie.setPath("/");
        response.addCookie(cookie);

        Cookie[] cookies = request.getCookies();
        for(Cookie c: cookies){
            System.out.println(c.getName());
            System.out.println(c.getValue());
        }

//        userVODao.insert(userVO);

//        System.out.println(menuDao);
//        System.out.println(from);
//        if(1 == 1)
//            throw new IllegalArgumentException("sdsdsdsd");
//        redisUtil.set("test", "value", 1000);
//        resultVO.setResult(redisUtil.get("test"));
        baseUserService.getCurrentUser();
        return resultVO;
    }
    @RequestMapping("/login1")
    @Permission(desc = "用户a", code = "user1")
    public void a(){
        logger.info("2222222222222222");
    }

}
