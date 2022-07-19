package com.ueueo.datamodels.management;

import com.ueueo.datamodels.meta.TenantDataModelMetaProvider;
import com.ueueo.multitenancy.ICurrentTenant;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Lee
 * @date 2022-07-15 11:43
 */
public class TenantDataModelManagementProvider extends DataModelManagementProvider {

    private final ICurrentTenant currentTenant;

    public TenantDataModelManagementProvider(IDataModelManagementStore dataModelManagementStore,
                                             ICurrentTenant currentTenant) {
        super(dataModelManagementStore);
        this.currentTenant = currentTenant;
    }

    @Override
    public String getName() {
        return TenantDataModelMetaProvider.PROVIDER_NAME;
    }

    @Override
    protected String normalizeProviderKey(String providerKey) {
        if (StringUtils.isNotBlank(providerKey)) {
            return providerKey;
        }
        return currentTenant.getId().getStringValue();
    }
}
