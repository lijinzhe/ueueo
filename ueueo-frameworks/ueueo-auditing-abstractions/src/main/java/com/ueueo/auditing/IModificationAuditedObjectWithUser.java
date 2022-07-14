package com.ueueo.auditing;

import com.ueueo.ID;
import org.springframework.lang.Nullable;

/**
 * This interface can be implemented to store modification information (who and when modified lastly).
 *
 * @author Lee
 * @date 2022-05-18 15:20
 */
public interface IModificationAuditedObjectWithUser<TUser> {
    /**
     * Last modifier user for this entity.
     *
     * @return
     */
    ID getLastModifierId();

    void setLastModifierId(ID lastModifierId);

    /**
     * Reference to the last modifier user of this entity.
     *
     * @return
     */
    @Nullable
    TUser getLastModifier();

    void setLastModifier(TUser user);
}
