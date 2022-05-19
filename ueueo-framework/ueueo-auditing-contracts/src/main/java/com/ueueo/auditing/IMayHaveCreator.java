package com.ueueo.auditing;

import com.ueueo.ID;

/**
 * This interface can be implemented to store modification information (who and when modified lastly).
 *
 * @author Lee
 * @date 2022-05-18 15:25
 */
public interface IMayHaveCreator {
    /**
     * Id of the creator.
     *
     * @return
     */
    ID getCreatorId();

    void setCreatorId(ID creatorId);
}
