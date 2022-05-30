using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Uow;

public class NullUnitOfWorkTransactionBehaviourProvider : IUnitOfWorkTransactionBehaviourProvider, ISingletonDependency
{
    public boolean IsTransactional => null;
}
