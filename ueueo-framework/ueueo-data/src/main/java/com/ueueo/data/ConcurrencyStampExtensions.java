package com.ueueo.data;

import com.ueueo.ddd.domain.IHasConcurrencyStamp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-29 14:54
 */
public class ConcurrencyStampExtensions {
    public static void setConcurrencyStampIfNotNull(IHasConcurrencyStamp entity, @Nullable String concurrencyStamp) {
        if (StringUtils.isNotBlank(concurrencyStamp)) {
            entity.setConcurrencyStamp(concurrencyStamp);
        }
    }
}
