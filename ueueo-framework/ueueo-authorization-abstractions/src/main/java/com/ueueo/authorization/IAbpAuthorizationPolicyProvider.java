package com.ueueo.authorization;

import java.util.List;

/**
 * @author Lee
 * @date 2022-05-29 13:44
 */
public interface IAbpAuthorizationPolicyProvider {
    List<String> getPoliciesNames();
}
