package com.ueueo.settings;

import com.ueueo.core.AbpException;

import java.util.Collection;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-18 20:33
 */
public interface ISettingProvider {

    String getOrNull(String name)  throws AbpException;

    Collection<SettingValue> getAll(Collection<String> names) throws AbpException;

    Collection<SettingValue> getAll()  throws AbpException;
}
