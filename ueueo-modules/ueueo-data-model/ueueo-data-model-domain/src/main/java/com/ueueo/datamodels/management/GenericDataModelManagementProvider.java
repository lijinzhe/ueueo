package com.ueueo.datamodels.management;

import com.ueueo.datamodels.meta.GenericDataModelMetaProvider;

/**
 * @author Lee
 * @date 2022-07-15 11:43
 */
public class GenericDataModelManagementProvider extends DataModelManagementProvider {

    public GenericDataModelManagementProvider(IDataModelManagementStore dataModelManagementStore) {
        super(dataModelManagementStore);
    }

    @Override
    public String getName() {
        return GenericDataModelMetaProvider.PROVIDER_NAME;
    }

    @Override
    protected String normalizeProviderKey(String providerKey) {
        return "";
    }
}
