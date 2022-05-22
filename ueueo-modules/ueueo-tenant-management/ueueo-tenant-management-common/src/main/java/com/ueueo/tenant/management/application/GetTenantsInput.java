package com.ueueo.tenant.management.application;

import com.ueueo.ddd.application.contracts.dtos.PagedAndSortedResultRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 13:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetTenantsInput extends PagedAndSortedResultRequestDto {
    private String filter;
}
