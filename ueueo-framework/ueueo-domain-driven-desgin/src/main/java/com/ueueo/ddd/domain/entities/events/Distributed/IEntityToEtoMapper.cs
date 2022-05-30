using JetBrains.Annotations;

namespace Volo.Abp.Domain.Entities.Events.Distributed;

public interface IEntityToEtoMapper
{
    [CanBeNull]
    Object Map(Object entityObj);
}
