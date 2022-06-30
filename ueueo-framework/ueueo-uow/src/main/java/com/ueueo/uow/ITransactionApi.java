package com.ueueo.uow;

import com.ueueo.disposable.IDisposable;

public interface ITransactionApi extends IDisposable {
    void commit();
}
