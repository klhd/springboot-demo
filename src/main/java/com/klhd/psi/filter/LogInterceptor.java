package com.klhd.psi.filter;

import com.alibaba.fastjson.JSON;
import com.klhd.psi.vo.ResultVO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Aspect
public class LogInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles}")
    private String env;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object doLog(ProceedingJoinPoint process) {
        long start = System.currentTimeMillis();
        //调用被拦截的方法
        Object object = null;
        try {
            object = process.proceed(process.getArgs());
        } catch (Throwable throwable) {
            long errorNum = System.currentTimeMillis();
            logger.error("服务异常，异常编码: "+errorNum, throwable);
            ResultVO rst = new ResultVO();
            rst.setCode(500);
            rst.setSuccess(false);
            rst.setMessage( throwable.getMessage() + " 异常编码: " + errorNum);
            object = rst;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String uri = request.getRequestURI();
        String params = JSON.toJSONString(request.getParameterMap());
        logger.info("method: {}, uri: {}, status: {}, time: {}, params: {}", request.getMethod(), uri, response.getStatus(), (System.currentTimeMillis() - start), params);
        return object;
    }
}
