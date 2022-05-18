package com.ueueo.data;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-16 11:33
 */
public interface IConnectionStringResolver {

    @NonNull
    String resolve(String connectionStringName);

}
