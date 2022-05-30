using System;
using Volo.Abp.ObjectExtending;

namespace Volo.Abp.Application.Dtos;

[Serializable]
public abstract class ExtensibleEntityDto<TKey> : ExtensibleObject, IEntityDto<TKey>
{
    /**
     * Id of the entity.
    */
    public TKey Id;// { get; set; }

    protected ExtensibleEntityDto()
        : this(true)
    {

    }

    protected ExtensibleEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }

    @Override public String toString()
    {
        return $"[DTO: {GetType().Name}] Id = {Id}";
    }
}

[Serializable]
public abstract class ExtensibleEntityDto : ExtensibleObject, IEntityDto
{
    protected ExtensibleEntityDto()
        : this(true)
    {

    }

    protected ExtensibleEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }

    @Override public String toString()
    {
        return $"[DTO: {GetType().Name}]";
    }
}
