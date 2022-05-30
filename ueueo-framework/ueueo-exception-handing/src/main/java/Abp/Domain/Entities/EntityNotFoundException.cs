using System;

namespace Volo.Abp.Domain.Entities;

/**
 * This exception is thrown if an entity excepted to be found but not found.
*/
public class EntityNotFoundException : AbpException
{
    /**
     * Type of the entity.
    */
    public Type EntityType;// { get; set; }

    /**
     * Id of the Entity.
    */
    public Object Id;// { get; set; }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
    */
    public EntityNotFoundException()
    {

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
    */
    public EntityNotFoundException(Type entityType)
        : this(entityType, null, null)
    {

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
    */
    public EntityNotFoundException(Type entityType, Object id)
        : this(entityType, id, null)
    {

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
    */
    public EntityNotFoundException(Type entityType, Object id, Exception innerException)
        : base(
            id == null
                ? $"There is no such an entity given id. Entity type: {entityType.FullName}"
                : $"There is no such an entity. Entity type: {entityType.FullName}, id: {id}",
            innerException)
    {
        EntityType = entityType;
        Id = id;
    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
    *
     * <param name="message">Exception message</param>
     */
    public EntityNotFoundException(String message)
        : base(message)
    {

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
    *
     * <param name="message">Exception message</param>
     * <param name="innerException">Inner exception</param>
     */
    public EntityNotFoundException(String message, Exception innerException)
        : base(message, innerException)
    {

    }
}
