package com.ueueo.datamodels;

/**
 * @author Lee
 * @date 2022-07-14 17:38
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
