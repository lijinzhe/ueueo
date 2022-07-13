package com.ueueo.ddd.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-25 21:19
 */
public interface IHasConcurrencyStamp {
    String getConcurrencyStamp();

    void setConcurrencyStamp(String concurrencyStamp);

    class Extensions{

        public static void setConcurrencyStampIfNotNull(IHasConcurrencyStamp entity, @Nullable String concurrencyStamp) {
            if (StringUtils.isNotBlank(concurrencyStamp)) {
                entity.setConcurrencyStamp(concurrencyStamp);
            }
        }
    }
}
