package com.ueueo.tenant.management.objectmapping;

import com.ueueo.ObjectMapping.IObjectMapper;
import com.ueueo.tenant.management.application.TenantDto;
import com.ueueo.tenant.management.domain.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 20:25
 */
@Mapper
public interface TenantObjectMapper extends IObjectMapper<Tenant, TenantDto> {
    TenantObjectMapper INSTANCE = Mappers.getMapper(TenantObjectMapper.class);
}
