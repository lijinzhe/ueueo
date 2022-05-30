using System;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public class UnitOfWorkEventArgs : EventArgs
{
    /**
     * Reference to the unit of work related to this event.
    */
    public IUnitOfWork UnitOfWork;//  { get; }

    public UnitOfWorkEventArgs(@Nonnull IUnitOfWork unitOfWork)
    {
        Objects.requireNonNull(unitOfWork, nameof(unitOfWork));

        UnitOfWork = unitOfWork;
    }
}
