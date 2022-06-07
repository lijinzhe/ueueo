package com.ueueo.threading;

import lombok.Getter;

@Getter
public class CancellationTokenOverride {
    private final CancellationToken cancellationToken;

    public CancellationTokenOverride(CancellationToken cancellationToken) {
        this.cancellationToken = cancellationToken;
    }
}
