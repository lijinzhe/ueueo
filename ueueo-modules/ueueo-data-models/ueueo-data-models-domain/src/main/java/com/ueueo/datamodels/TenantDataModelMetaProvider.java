package com.ueueo.datamodels;

import com.ueueo.multitenancy.ICurrentTenant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-19 21:42
 */
public class TenantDataModelMetaProvider extends DataModelMetaProvider {

    public static final String PROVIDER_NAME = "TENANT";

    protected ICurrentTenant currentTenant;

    public TenantDataModelMetaProvider(IDataModelStore settingStore, ICurrentTenant currentTenant) {
        super(settingStore);
        this.currentTenant = currentTenant;
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public DataModelMeta getOrNull(DataModelDefinition dataModelDefinition) {
        return dataModelStore.getOrNull(dataModelDefinition.getName(), getName(), currentTenant.getId().toString());
    }

    @Override
    public List<DataModelMeta> getAll(List<DataModelDefinition> dataModelDefinitions) {
        List<String> names = dataModelDefinitions.stream().map(DataModelDefinition::getName).collect(Collectors.toList());
        return dataModelStore.getAll(names, getName(), currentTenant.getId().toString());
    }

}
