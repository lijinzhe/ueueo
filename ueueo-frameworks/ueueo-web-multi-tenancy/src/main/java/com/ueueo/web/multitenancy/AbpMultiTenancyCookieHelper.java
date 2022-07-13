package com.ueueo.web.multitenancy;

import com.ueueo.ID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class AbpMultiTenancyCookieHelper {

    public static void setTenantCookie(HttpServletResponse response, ID tenantId, String tenantField) {
        if (response != null) {
            if (tenantId != null) {
                Cookie cookie = new Cookie(tenantField, tenantId.getStringValue());
                cookie.setPath("/");
                cookie.setHttpOnly(false);
                cookie.setMaxAge(Integer.MAX_VALUE);
                response.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie(tenantField, "");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
