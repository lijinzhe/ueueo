using System;
using Volo.Abp.ObjectExtending;

namespace Volo.Abp.Application.Dtos;

[Serializable]
public abstract class ExtensibleEntityDto<TKey> : ExtensibleObject, IEntityDto<TKey>
{
    /// <summary>
    /// Id of the entity.
    /// </summary>
    public TKey Id;// { get; set; }

    protected ExtensibleEntityDto()
        : this(true)
    {

    }

    protected ExtensibleEntityDto(bool setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }

    @Override public string toString()
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

    protected ExtensibleEntityDto(bool setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }

    @Override public string toString()
    {
        return $"[DTO: {GetType().Name}]";
    }
}
