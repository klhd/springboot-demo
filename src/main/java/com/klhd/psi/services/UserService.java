package com.klhd.psi.services;

import com.klhd.psi.annotation.Cache;
import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import com.klhd.psi.config.MyProps;
import com.klhd.psi.config.redis.RedisUtil;
import com.klhd.psi.dao.MenuDao;
import com.klhd.psi.vo.ResultVO;
import com.klhd.psi.vo.menu.Menu;
import com.klhd.psi.vo.menu.MenuQuery;
import com.klhd.psi.vo.user.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2017/9/25.
 */
@RestController
@RequestMapping("/user")
@ControllerPermission(code="aaa", desc="aaaaa desc")
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

    @RequestMapping("/login")
    @Permission(code="www", desc="sss")
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
        logger.info("menu", menus);
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

}
