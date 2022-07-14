package com.ueueo.auditing;

import org.springframework.lang.Nullable;

/**
 * Adds navigation property (object reference) to <see cref="ICreationAuditedObject"/> interface.
 *
 * @param <TCreator> Type of the user
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */

public interface ICreationAuditedObjectWithUser<TCreator> extends ICreationAuditedObject {

    /**
     * Reference to the creator.
     *
     * @return
     */
    @Nullable
    TCreator getCreator();

    void setCreator(TCreator creator);
}
