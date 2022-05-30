using System;
using Volo.Abp.Auditing;

namespace Volo.Abp.Application.Dtos;

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
*/
[Serializable]
public abstract class AuditedEntityDto : CreationAuditedEntityDto, IAuditedObject
{
     * <inheritdoc />
    public DateTime? LastModificationTime;// { get; set; }

     * <inheritdoc />
    public ID LastModifierId;// { get; set; }
}

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
*/
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
[Serializable]
public abstract class AuditedEntityDto<TPrimaryKey> : CreationAuditedEntityDto<TPrimaryKey>, IAuditedObject
{
     * <inheritdoc />
    public DateTime? LastModificationTime;// { get; set; }

     * <inheritdoc />
    public ID LastModifierId;// { get; set; }
}
