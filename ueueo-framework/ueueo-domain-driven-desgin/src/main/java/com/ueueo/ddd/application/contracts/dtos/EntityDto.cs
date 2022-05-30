using System;

namespace Volo.Abp.Application.Dtos;

[Serializable]
public abstract class EntityDto : IEntityDto //TODO: Consider to delete this class
{
    @Override public String toString()
    {
        return $"[DTO: {GetType().Name}]";
    }
}

[Serializable]
public abstract class EntityDto<TKey> : EntityDto, IEntityDto<TKey>
{
    /**
     * Id of the entity.
    */
    public TKey Id;// { get; set; }

    @Override public String toString()
    {
        return $"[DTO: {GetType().Name}] Id = {Id}";
    }
}
