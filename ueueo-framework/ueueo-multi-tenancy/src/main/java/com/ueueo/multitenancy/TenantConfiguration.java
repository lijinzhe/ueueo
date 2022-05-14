package com.ueueo.multitenancy;

import com.ueueo.data.ConnectionStrings;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-05-13 21:11
 */
@Data
public class TenantConfiguration {

    private Long id;
    private String name;
    private Boolean isActive;

    private ConnectionStrings connectionStrings;

    public TenantConfiguration() {
        isActive = true;
    }

    public TenantConfiguration(Long id, @NonNull String name) {
        Assert.notNull(name, "name 不能为空");
        this.id = id;
        this.name = name;
        this.connectionStrings = new ConnectionStrings();
    }
}
