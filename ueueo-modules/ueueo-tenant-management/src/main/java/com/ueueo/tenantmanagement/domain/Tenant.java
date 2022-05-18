package com.ueueo.tenantmanagement.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-18 15:15
 */
public class Tenant {
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private String name;

   private List<TenantConnectionString> ConnectionStrings;
}
