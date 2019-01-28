package com.klhd.psi.services;

import com.klhd.psi.common.Constants;
import com.klhd.psi.config.redis.RedisUtil;
import com.klhd.psi.vo.user.UserExtVO;
import com.klhd.psi.vo.user.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class BaseUserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisUtil redisUtil;

    public UserExtVO getCurrentUser(){
        UserExtVO userVO = (UserExtVO) redisUtil.get(getCurrentToken());
        return userVO;
    }

    public String getCurrentToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies){
            if("sso_id".equals(c.getName())){
                token = c.getValue();
                break;
            }
        }
        return token;
    }

    public void restore(UserExtVO userExtVO){
        String token = getCurrentToken();
        redisUtil.set(token, userExtVO, Constants.SESSION_TIME);
    }

}
