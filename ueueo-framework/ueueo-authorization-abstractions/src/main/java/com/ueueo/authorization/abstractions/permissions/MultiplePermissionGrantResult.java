package com.ueueo.authorization.abstractions.permissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:01
 */
public class MultiplePermissionGrantResult {

    private Map<String, PermissionGrantResult> result;

    public MultiplePermissionGrantResult() {
        result = new HashMap<>();
    }

    public MultiplePermissionGrantResult(List<String> names) {
        this(names, PermissionGrantResult.Undefined);
    }

    public MultiplePermissionGrantResult(List<String> names, PermissionGrantResult grantResult) {
        Objects.requireNonNull(names);
        result = new HashMap<>();

        for (String name : names) {
            result.put(name, grantResult);
        }
    }

    public Map<String, PermissionGrantResult> getResult() {
        return result;
    }

    public Boolean allGranted() {
        return result.values().stream().allMatch(x -> x.equals(PermissionGrantResult.Granted));
    }

    public Boolean allProhibited() {
        return result.values().stream().allMatch(x -> x.equals(PermissionGrantResult.Prohibited));
    }
}
