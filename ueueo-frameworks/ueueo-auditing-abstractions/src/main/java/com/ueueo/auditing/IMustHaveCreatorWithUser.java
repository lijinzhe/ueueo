package com.ueueo.auditing;

import org.springframework.lang.Nullable;

/**
 * Standard interface for an entity that MUST have a creator.
 *
 * @author Lee
 * @date 2022-05-18 15:25
 */
public interface IMustHaveCreatorWithUser<TCreator> extends IMustHaveCreator {

    /**
     * Reference to the creator.
     *
     * @return
     */
    @Nullable
    TCreator getCreator();

    void setCreator(TCreator creator);
}
