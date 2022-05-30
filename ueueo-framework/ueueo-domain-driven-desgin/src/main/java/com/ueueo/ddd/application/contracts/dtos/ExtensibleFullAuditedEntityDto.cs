using System;
using Volo.Abp.Auditing;
using Volo.Abp.Data;

namespace Volo.Abp.Application.Dtos;

/**
 * This class can be inherited by DTO classes to implement <see cref="IFullAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
*/
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
[Serializable]
public abstract class ExtensibleFullAuditedEntityDto<TPrimaryKey> : ExtensibleAuditedEntityDto<TPrimaryKey>, IFullAuditedObject
{
     * <inheritdoc />
    public boolean IsDeleted;// { get; set; }

     * <inheritdoc />
    public ID DeleterId;// { get; set; }

     * <inheritdoc />
    public DateTime? DeletionTime;// { get; set; }

    protected ExtensibleFullAuditedEntityDto()
        : this(true)
    {

    }

    protected ExtensibleFullAuditedEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }
}

/**
 * This class can be inherited by DTO classes to implement <see cref="IFullAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
*/
[Serializable]
public abstract class ExtensibleFullAuditedEntityDto : ExtensibleAuditedEntityDto, IFullAuditedObject
{
     * <inheritdoc />
    public boolean IsDeleted;// { get; set; }

     * <inheritdoc />
    public ID DeleterId;// { get; set; }

     * <inheritdoc />
    public DateTime? DeletionTime;// { get; set; }

    protected ExtensibleFullAuditedEntityDto()
        : this(true)
    {

    }

    protected ExtensibleFullAuditedEntityDto(boolean setDefaultsForExtraProperties)
        : base(setDefaultsForExtraProperties)
    {

    }
}
