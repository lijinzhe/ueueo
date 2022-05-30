using System;
using Volo.Abp.Auditing;
using Volo.Abp.Data;

namespace Volo.Abp.Application.Dtos;

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
*/
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
[Serializable]
public abstract class ExtensibleCreationAuditedEntityDto<TPrimaryKey> : ExtensibleEntityDto<TPrimaryKey>, ICreationAuditedObject
{
     * <inheritdoc />
    public DateTime CreationTime;// { get; set; }

     * <inheritdoc />
    public ID CreatorId;// { get; set; }

    protected ExtensibleCreationAuditedEntityDto()
        : this(true)
    {

    }

    protected ExtensibleCreationAuditedEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }
}

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
*/
[Serializable]
public abstract class ExtensibleCreationAuditedEntityDto : ExtensibleEntityDto, ICreationAuditedObject
{
     * <inheritdoc />
    public DateTime CreationTime;// { get; set; }

     * <inheritdoc />
    public ID CreatorId;// { get; set; }

    protected ExtensibleCreationAuditedEntityDto()
        : this(true)
    {

    }

    protected ExtensibleCreationAuditedEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }
}
