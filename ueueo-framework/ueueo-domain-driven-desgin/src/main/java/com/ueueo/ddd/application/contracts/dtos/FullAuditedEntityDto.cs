using System;
using Volo.Abp.Auditing;

namespace Volo.Abp.Application.Dtos;

/**
 * This class can be inherited by DTO classes to implement <see cref="IFullAuditedObject"/> interface.
*/
[Serializable]
public abstract class FullAuditedEntityDto : AuditedEntityDto, IFullAuditedObject
{
     * <inheritdoc />
    public boolean IsDeleted;// { get; set; }

     * <inheritdoc />
    public ID DeleterId;// { get; set; }

     * <inheritdoc />
    public DateTime? DeletionTime;// { get; set; }
}

/**
 * This class can be inherited by DTO classes to implement <see cref="IFullAuditedObject"/> interface.
*/
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
[Serializable]
public abstract class FullAuditedEntityDto<TPrimaryKey> : AuditedEntityDto<TPrimaryKey>, IFullAuditedObject
{
     * <inheritdoc />
    public boolean IsDeleted;// { get; set; }

     * <inheritdoc />
    public ID DeleterId;// { get; set; }

     * <inheritdoc />
    public DateTime? DeletionTime;// { get; set; }
}
