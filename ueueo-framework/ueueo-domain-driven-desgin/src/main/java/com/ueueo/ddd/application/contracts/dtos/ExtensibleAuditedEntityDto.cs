using System;
using Volo.Abp.Auditing;
using Volo.Abp.Data;

namespace Volo.Abp.Application.Dtos;

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
*/
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
[Serializable]
public abstract class ExtensibleAuditedEntityDto<TPrimaryKey> : ExtensibleCreationAuditedEntityDto<TPrimaryKey>, IAuditedObject
{
     * <inheritdoc />
    public DateTime? LastModificationTime;// { get; set; }

     * <inheritdoc />
    public ID LastModifierId;// { get; set; }

    protected ExtensibleAuditedEntityDto()
        : this(true)
    {

    }

    protected ExtensibleAuditedEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }
}

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
*/
[Serializable]
public abstract class ExtensibleAuditedEntityDto : ExtensibleCreationAuditedEntityDto, IAuditedObject
{
     * <inheritdoc />
    public DateTime? LastModificationTime;// { get; set; }

     * <inheritdoc />
    public ID LastModifierId;// { get; set; }

    protected ExtensibleAuditedEntityDto()
        : this(true)
    {

    }

    protected ExtensibleAuditedEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }
}
