package com.ueueo.security.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentOrganizationUnit {
}
