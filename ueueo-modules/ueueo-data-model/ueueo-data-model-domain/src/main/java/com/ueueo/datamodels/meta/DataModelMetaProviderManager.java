package com.ueueo.datamodels.meta;

import java.util.List;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public class DataModelMetaProviderManager implements IDataModelMetaProviderManager {

    private final List<IDataModelMetaProvider> providers;

    @Override
    public List<IDataModelMetaProvider> getProviders() {
        return providers;
    }

    public DataModelMetaProviderManager(List<IDataModelMetaProvider> providers) {
        this.providers = providers;
    }
}
