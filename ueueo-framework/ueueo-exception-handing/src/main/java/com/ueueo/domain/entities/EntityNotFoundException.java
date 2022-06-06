package com.ueueo.domain.entities;

import com.ueueo.AbpException;
import com.ueueo.ID;
import lombok.Data;

/**
 * This exception is thrown if an entity excepted to be found but not found.
 */
@Data
public class EntityNotFoundException extends AbpException {
    /**
     * Type of the entity.
     */
    public Class<?> entityType;

    /**
     * Id of the Entity.
     */
    public ID id;

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
     */
    public EntityNotFoundException() {

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
     */
    public EntityNotFoundException(Class<?> entityType) {
        this(entityType, null, null);

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
     */
    public EntityNotFoundException(Class<?> entityType, ID id) {
        this(entityType, id, null);

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
     */
    public EntityNotFoundException(Class<?> entityType, ID id, Exception innerException) {
        super(id == null ?
                        String.format("There is no such an entity given id. Entity type: %s", entityType.getName())
                        : String.format("There is no such an entity. Entity type: %s, id: %s", entityType.getName(), id),
                innerException);
        this.entityType = entityType;
        this.id = id;
    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
     *
     * <param name="message">Exception message</param>
     */
    public EntityNotFoundException(String message) {
        super(message);

    }

    /**
     * Creates a new <see cref="EntityNotFoundException"/> object.
     *
     * <param name="message">Exception message</param>
     * <param name="innerException">Inner exception</param>
     */
    public EntityNotFoundException(String message, Exception innerException) {
        super(message, innerException);

    }
}
