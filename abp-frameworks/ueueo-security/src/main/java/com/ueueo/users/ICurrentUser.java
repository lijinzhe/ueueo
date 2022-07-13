package com.ueueo.users;

import com.ueueo.ID;
import com.ueueo.security.claims.Claim;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

/**
 * 登录用户信息接口定义
 *
 * @author Lee
 * @date 2021-08-19 21:53
 */
public interface ICurrentUser extends Principal {

    boolean isAuthenticated();
    /**
     * 用户ID
     *
     * @return
     */
    @Nullable
    ID getId();

    /**
     * 用户名（唯一账号）
     *
     * @return
     */
    @Nullable
    String getUserName();

    @Override
    default String getName(){
        return getUserName();
    }

    /**
     * 用户姓名
     *
     * @return
     */
    @Nullable
    String getFullName();

    /**
     * 别名
     *
     * @return
     */
    @Nullable
    String getSurname();

    /**
     * 手机号
     *
     * @return
     */
    @Nullable
    String getPhoneNumber();

    /**
     * 手机号是否验证
     *
     * @return
     */
    boolean getPhoneNumberVerified();

    /**
     * 电子邮件
     *
     * @return
     */
    @Nullable
    String getEmail();

    /**
     * 邮箱是否验证
     *
     * @return
     */
    boolean getEmailVerified();

    /**
     * 租户ID
     *
     * @return
     */
    ID getTenantId();

    /**
     * 角色列表
     *
     * @return
     */
    List<String> getRoles();

    @Nullable
    Claim findClaim(String claimType);

    @NonNull
    List<Claim> findClaims(String claimType);

    @NonNull
    List<Claim> getAllClaims();

    boolean isInRole(String roleName);

}
