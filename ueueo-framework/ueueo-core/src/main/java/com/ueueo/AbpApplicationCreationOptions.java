package com.ueueo;

import com.ueueo.modularity.plugins.PlugInSourceList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-27 10:29
 */
public class AbpApplicationCreationOptions {
    @NonNull
    @Getter
    private ApplicationContext applicationContext;

    @NonNull
    @Getter
    private PlugInSourceList plugInSources;

//    /**
//     * The options in this property only take effect when IConfiguration not registered.
//     */
    //    @NonNull
    //    @Getter
    //    public AbpConfigurationBuilderOptions configuration;
    @Getter
    @Setter
    private boolean skipConfigureServices;

    public AbpApplicationCreationOptions(@NonNull ApplicationContext applicationContext) {
        Objects.requireNonNull(applicationContext);
        this.applicationContext = applicationContext;
        this.plugInSources = new PlugInSourceList();
        //        this.configuration = new AbpConfigurationBuilderOptions();
    }
}