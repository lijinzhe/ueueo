package com.ueueo.features;

import com.ueueo.multitenancy.ICurrentTenant;
import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-17 16:56
 */
public class TenantFeatureValueProvider extends FeatureValueProvider {
    public static final String ProviderName = "T";
    @Getter
    protected final ICurrentTenant currentTenant;

    public TenantFeatureValueProvider(IFeatureStore featureStore, ICurrentTenant currentTenant) {
        super(featureStore);
        this.currentTenant = currentTenant;
    }

    @Override
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(FeatureDefinition feature) {
        return featureStore.getOrNull(feature.getName(), getName(), currentTenant.getId().toString());
    }
}
