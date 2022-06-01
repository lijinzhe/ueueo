package com.ueueo.threading;

public class CancellationTokenProviderExtensions {

    public static CancellationToken fallbackToProvider(ICancellationTokenProvider provider, CancellationToken prefferedValue) {
        return prefferedValue == null || prefferedValue == CancellationToken.NONE
                ? provider.getToken()
                : prefferedValue;
    }
}
