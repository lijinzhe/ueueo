package com.ueueo.auditing;

import com.ueueo.ID;
import org.springframework.lang.Nullable;

/**
 * Standard interface for an entity that MUST have a creator.
 *
 * @author Lee
 * @date 2022-05-18 15:25
 */
public interface IMustHaveCreator<TCreator> {
    /**
     * Id of the creator.
     *
     * @return
     */
    ID getCreatorId();

    /**
     * Reference to the creator.
     *
     * @return
     */
    @Nullable
    TCreator getCreator();

}
