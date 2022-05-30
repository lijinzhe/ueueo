using System;
using System.Data;

namespace Volo.Abp.Uow;

public class AbpUnitOfWorkOptions : IAbpUnitOfWorkOptions
{
    /**
     * Default: false.
    */
    public boolean IsTransactional;// { get; set; }

    public IsolationLevel? IsolationLevel;// { get; set; }

    /**
     * Milliseconds
    */
    public int? Timeout;// { get; set; }

    public AbpUnitOfWorkOptions()
    {

    }

    public AbpUnitOfWorkOptions(boolean isTransactional = false, IsolationLevel? isolationLevel = null, int? timeout = null)
    {
        IsTransactional = isTransactional;
        IsolationLevel = isolationLevel;
        Timeout = timeout;
    }

    public AbpUnitOfWorkOptions Clone()
    {
        return new AbpUnitOfWorkOptions
        {
            IsTransactional = IsTransactional,
            IsolationLevel = IsolationLevel,
            Timeout = Timeout
        };
    }
}
