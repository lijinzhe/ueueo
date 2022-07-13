package com.ueueo.auditing;

import java.util.Date;

/**
 * A standard interface to add DeletionTime property to a class.
 *
 * @author Lee
 * @date 2022-05-18 15:28
 */
public interface IHasModificationTime {
    /**
     * The last modified time for this entity.
     *
     * @return
     */
    Date getLastModificationTime();

    void setLastModificationTime(Date lastModificationTime);
}
