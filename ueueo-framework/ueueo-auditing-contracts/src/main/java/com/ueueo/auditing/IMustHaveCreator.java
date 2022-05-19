package com.ueueo.auditing;

import com.ueueo.ID;

/**
 * Standard interface for an entity that MUST have a creator.
 *
 * @author Lee
 * @date 2022-05-18 15:25
 */
public interface IMustHaveCreator {
    /**
     * Id of the creator.
     *
     * @return
     */
    ID getCreatorId();

    void setCreatorId(ID creatorId);
}
