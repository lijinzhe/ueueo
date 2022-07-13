package com.ueueo.tenantmanagement.domain;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-20 11:18
 */
@Data
public class TenantEto {
    private ID id;

    private String name;
}
