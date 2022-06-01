package com.ueueo.threading;

import com.ueueo.IDisposable;

public interface ICancellationTokenProvider {
    CancellationToken getToken();

    IDisposable use(CancellationToken cancellationToken);
}
