package com.ueueo.threading;

import com.ueueo.IDisposable;

public interface ICancellationTokenProvider {
    CancellationToken getToken();

    IDisposable use(CancellationToken cancellationToken);

    class Extensions{
        public static CancellationToken fallbackToProvider(ICancellationTokenProvider provider, CancellationToken prefferedValue) {
            return prefferedValue == null || prefferedValue == CancellationToken.NONE
                    ? provider.getToken()
                    : prefferedValue;
        }
    }
}
