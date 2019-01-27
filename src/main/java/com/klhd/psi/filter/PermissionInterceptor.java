package com.klhd.psi.filter;

import com.klhd.psi.annotation.ControllerPermission;
import com.klhd.psi.annotation.Permission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles}")
    private String profiles;

    @Around("@annotation(com.klhd.psi.annotation.Permission) || @annotation(com.klhd.psi.annotation.ControllerPermission)")
    public Object doCheck(ProceedingJoinPoint process) throws Throwable {
        if("dev".equals(profiles)){
            logger.info("权限校验：开发环境不校验");
            return process.proceed(process.getArgs());
        }
        ControllerPermission controllerPermission = process.getTarget().getClass().getAnnotation(ControllerPermission.class);
        if(controllerPermission == null){
            logger.error("方法添加权限注解后，类上面也要添加权限注解");
        }

        System.out.println(controllerPermission.code() + "  ---- " + controllerPermission.desc());

        MethodSignature ms = (MethodSignature) process.getSignature();
        Method method = ms.getMethod();
        Permission methodOperation = method.getAnnotation(Permission.class);
        String code = methodOperation.code();
        String desc = methodOperation.desc();
        System.out.println(code + "  ---- " + desc);
        return process.proceed(process.getArgs());
    }
}
