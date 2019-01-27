package com.klhd.psi.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles}")
    private String profiles;

    @Around("@annotation(com.klhd.psi.annotation.Cache)")
    public Object doCheck(ProceedingJoinPoint process) throws Throwable {

        return process.proceed(process.getArgs());
    }
}
