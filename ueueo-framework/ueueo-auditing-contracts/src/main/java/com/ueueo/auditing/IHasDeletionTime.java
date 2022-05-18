package com.ueueo.auditing;

import com.ueueo.ISoftDelete;

import java.util.Date;

/**
 * A standard interface to add DeletionTime property to a class.
 * It also makes the class soft delete (see <see cref="ISoftDelete"/>).
 *
 * @author Lee
 * @date 2022-05-18 15:29
 */
public interface IHasDeletionTime extends ISoftDelete {
    /**
     * Deletion time.
     *
     * @return
     */
    Date getDeletionTime();

    void setDeletionTime(Date deletionTime);
}
