package com.ueueo.uow;

import com.ueueo.threading.CancellationToken;

public interface ISupportsSavingChanges {
    void saveChanges(CancellationToken cancellationToken);
}
