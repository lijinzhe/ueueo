package com.ueueo.features;

import com.ueueo.security.claims.ICurrentPrincipalAccessor;

/**
 * @author Lee
 * @date 2022-05-17 16:53
 */
public class EditionFeatureValueProvider extends FeatureValueProvider {
    public static final String ProviderName = "E";

    protected final ICurrentPrincipalAccessor principalAccessor;

    public EditionFeatureValueProvider(IFeatureStore featureStore, ICurrentPrincipalAccessor principalAccessor) {
        super(featureStore);
        this.principalAccessor = principalAccessor;
    }

    @Override
    public String getName() {
        return ProviderName;
    }

    @Override
    public String getOrNull(FeatureDefinition feature) {
        Long editionId = principalAccessor.getPrincipal().findEditionId();
        if (editionId == null) {
            return null;
        }
        return featureStore.getOrNull(feature.getName(), getName(), editionId.toString());
    }
}
