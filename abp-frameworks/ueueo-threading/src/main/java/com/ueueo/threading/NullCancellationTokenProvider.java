package com.ueueo.threading;

public class NullCancellationTokenProvider extends CancellationTokenProviderBase {
    public static NullCancellationTokenProvider Instance = new NullCancellationTokenProvider();

    @Override
    public CancellationToken getToken() {
        return getOverrideValue().getCancellationToken() != null ?
                getOverrideValue().getCancellationToken() : CancellationToken.NONE;
    }

    private NullCancellationTokenProvider() {
        super(new AmbientDataContextAmbientScopeProvider<>(new AsyncLocalAmbientDataContext()));
    }
}
