package com.ueueo.tenantmanagement.httpapi;

import com.ueueo.tenantmanagement.application.ITenantAppService;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 21:42
 */
public class TenantController {

    private ITenantAppService tenantAppService;

    public TenantController(ITenantAppService tenantAppService) {
        this.tenantAppService = tenantAppService;
    }


}
