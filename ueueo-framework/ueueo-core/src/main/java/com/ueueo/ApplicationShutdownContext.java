package com.ueueo;

import com.ueueo.dependencyinjection.system.IServiceProvider;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * @author Lee
 * @date 2021-08-20 10:42
 */
public class ApplicationShutdownContext {
    @Getter
    private IServiceProvider serviceProvider;

    public ApplicationShutdownContext(@NonNull IServiceProvider serviceProvider) {
        Objects.requireNonNull(serviceProvider);
        this.serviceProvider = serviceProvider;
    }
}
