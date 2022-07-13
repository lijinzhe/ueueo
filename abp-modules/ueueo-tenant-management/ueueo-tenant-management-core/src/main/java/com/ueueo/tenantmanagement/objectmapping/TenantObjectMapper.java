package com.ueueo.tenantmanagement.objectmapping;

import com.ueueo.ObjectMapping.IObjectMapper;
import com.ueueo.tenantmanagement.application.TenantDto;
import com.ueueo.tenantmanagement.domain.Tenant;
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
