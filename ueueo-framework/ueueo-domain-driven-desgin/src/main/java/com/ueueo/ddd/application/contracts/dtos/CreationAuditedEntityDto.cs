using System;
using Volo.Abp.Auditing;

namespace Volo.Abp.Application.Dtos;

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject"/> interface.
*/
[Serializable]
public abstract class CreationAuditedEntityDto : EntityDto, ICreationAuditedObject
{
     * <inheritdoc />
    public DateTime CreationTime;// { get; set; }

     * <inheritdoc />
    public ID CreatorId;// { get; set; }
}

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject"/> interface.
*/
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
[Serializable]
public abstract class CreationAuditedEntityDto<TPrimaryKey> : EntityDto<TPrimaryKey>, ICreationAuditedObject
{
     * <inheritdoc />
    public DateTime CreationTime;// { get; set; }

     * <inheritdoc />
    public ID CreatorId;// { get; set; }
}
