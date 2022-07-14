package com.ueueo.datamodels;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-19 21:38
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
