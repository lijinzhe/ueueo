using System;
using System.Data;

namespace Volo.Abp.Uow;

public interface IAbpUnitOfWorkOptions
{
    boolean IsTransactional;//  { get; }

    IsolationLevel? IsolationLevel;//  { get; }

    /**
     * Milliseconds
    */
    int? Timeout;//  { get; }
}
