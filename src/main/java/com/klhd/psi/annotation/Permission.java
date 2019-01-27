package com.klhd.psi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String ATTR_CODE = "code";
    String ATTR_DESC = "desc";

    String code() default "";
    String desc() default "";
}