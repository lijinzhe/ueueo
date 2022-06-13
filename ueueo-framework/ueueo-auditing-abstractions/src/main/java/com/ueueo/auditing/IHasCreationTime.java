package com.ueueo.auditing;

import java.util.Date;

/**
 * A standard interface to add CreationTime property.
 *
 * @author Lee
 * @date 2022-05-18 15:28
 */
public interface IHasCreationTime {
    /**
     * Creation time.
     *
     * @return
     */
    Date getCreationTime();

    void setCreationTime(Date creationTime);
}
