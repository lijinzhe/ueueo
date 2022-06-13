package com.ueueo.auditing;

import com.ueueo.ID;

/**
 * This interface can be implemented to store modification information (who and when modified lastly).
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IModificationAuditedObject extends IHasModificationTime{
    /**
     * Last modifier user for this entity.
     *
     * @return
     */
    ID getLastModifierId();

    void setLastModifierId(ID lastModifierId);

}
