package com.ueueo.multitenancy;

import com.ueueo.util.Check;
import com.ueueo.ID;
import com.ueueo.data.ConnectionStrings;
import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-13 21:11
 */
@Data
public class TenantConfiguration {

    private ID id;
    private String name;
    private Boolean isActive;

    private ConnectionStrings connectionStrings;

    public TenantConfiguration() {
        isActive = true;
    }

    public TenantConfiguration(ID id, @NonNull String name) {
        this();
        this.id = id;
        this.name = Check.notNullOrEmpty(name, "name");
        this.connectionStrings = new ConnectionStrings();
    }
}
