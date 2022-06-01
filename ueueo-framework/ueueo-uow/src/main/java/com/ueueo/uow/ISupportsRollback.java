package com.ueueo.uow;

import com.ueueo.threading.CancellationToken;

public interface ISupportsRollback {
    void rollback(CancellationToken cancellationToken);
}
