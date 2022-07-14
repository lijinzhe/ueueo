package com.ueueo.datamodels;

/**
 * @author Lee
 * @date 2021-08-19 21:36
 */
public abstract class DataModelMetaProvider implements IDataModelMetaProvider {

    protected IDataModelStore dataModelStore;

    public IDataModelStore getDataModelStore() {
        return dataModelStore;
    }

    protected DataModelMetaProvider(IDataModelStore dataModelStore) {
        this.dataModelStore = dataModelStore;
    }

}
