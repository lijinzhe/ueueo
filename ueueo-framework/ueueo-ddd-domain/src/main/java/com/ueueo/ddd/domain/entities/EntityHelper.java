package com.ueueo.ddd.domain.entities;

import com.ueueo.multitenancy.AsyncLocalCurrentTenantAccessor;
import com.ueueo.multitenancy.IMultiTenant;
import org.omg.CORBA.ObjectHelper;

import java.util.UUID;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-18 16:00
 */
public class EntityHelper {

//    public static void trySetTenantId(IEntity<?> entity)
//    {
//        if(entity instanceof IMultiTenant){
//            IMultiTenant multiTenantEntity = (IMultiTenant)entity;
//            Long tenantId = AsyncLocalCurrentTenantAccessor.Instance.getCurrent().getTenantId();
//            if (!tenantId.equals( multiTenantEntity.getTenantId()))
//            {
//                multiTenantEntity.
//            }
//
//            ObjectHelper.TrySetProperty(
//                    multiTenantEntity,
//                    x => x.TenantId,
//                    () => tenantId
//        }else{
//            return;
//        }
//    }

}
