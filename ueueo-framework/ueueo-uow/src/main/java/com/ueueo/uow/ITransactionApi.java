package com.ueueo.uow;

import com.ueueo.IDisposable;

public interface ITransactionApi extends IDisposable {
    void commit();
}
