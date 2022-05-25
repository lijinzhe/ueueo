package com.ueueo.ddd.domain.entities;

import com.ueueo.ID;
import com.ueueo.multitenancy.AsyncLocalCurrentTenantAccessor;
import com.ueueo.multitenancy.BasicTenantInfo;
import com.ueueo.multitenancy.IMultiTenant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Optional;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-18 16:00
 */
@Slf4j
public final class EntityHelper {

    public static void trySetTenantId(IEntity entity) {
        if (entity instanceof IMultiTenant) {
            IMultiTenant multiTenantEntity = (IMultiTenant) entity;
            ID tenantId = Optional.ofNullable(AsyncLocalCurrentTenantAccessor.Instance.getCurrent())
                    .map(BasicTenantInfo::getTenantId).orElse(null);
            if (!multiTenantEntity.getTenantId().equals(tenantId)) {
                try {
                    BeanUtils.setProperty(multiTenantEntity, "tenantId", tenantId);
                } catch (Exception e) {
                    log.debug("Try to set tenantId error!");
                }
            }
        }
    }

}
