using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public interface IUnitOfWorkAccessor
{
    [CanBeNull]
    IUnitOfWork UnitOfWork;//  { get; }

    void SetUnitOfWork(@Nullable IUnitOfWork unitOfWork);
}
