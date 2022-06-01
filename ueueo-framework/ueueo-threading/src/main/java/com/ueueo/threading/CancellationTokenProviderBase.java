package com.ueueo.threading;

import com.ueueo.IDisposable;
import lombok.Getter;

@Getter
public abstract class CancellationTokenProviderBase implements ICancellationTokenProvider {
    public final String CancellationTokenOverrideContextKey = "Volo.Abp.Threading.CancellationToken.Override";

    protected IAmbientScopeProvider<CancellationTokenOverride> cancellationTokenOverrideScopeProvider;

    protected CancellationTokenOverride getOverrideValue() {
        return cancellationTokenOverrideScopeProvider.getValue(CancellationTokenOverrideContextKey);
    }

    protected CancellationTokenProviderBase(IAmbientScopeProvider<CancellationTokenOverride> cancellationTokenOverrideScopeProvider) {
        this.cancellationTokenOverrideScopeProvider = cancellationTokenOverrideScopeProvider;
    }

    @Override
    public IDisposable use(CancellationToken cancellationToken) {
        return cancellationTokenOverrideScopeProvider.beginScope(
                CancellationTokenOverrideContextKey,
                new CancellationTokenOverride(cancellationToken)
        );
    }
}
