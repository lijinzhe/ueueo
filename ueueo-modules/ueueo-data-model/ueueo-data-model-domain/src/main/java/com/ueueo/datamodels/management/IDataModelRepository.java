package com.ueueo.datamodels.management;

import com.ueueo.ID;
import com.ueueo.ddd.domain.repositories.IBasicRepository;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:51
 */
public interface IDataModelRepository extends IBasicRepository<DataModel, ID> {

    DataModel find(String name, String providerName, String providerKey);

    List<DataModel> getList(String providerName, String providerKey);

    List<DataModel> getList(List<String> names, String providerName, String providerKey);
}
