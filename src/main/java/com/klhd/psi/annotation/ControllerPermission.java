package com.klhd.psi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerPermission {
    String ATTR_CODE = "code";
    String ATTR_DESC = "desc";

    String code();
    String desc();
}