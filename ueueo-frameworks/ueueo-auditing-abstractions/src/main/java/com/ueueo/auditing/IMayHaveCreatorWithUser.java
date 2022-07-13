package com.ueueo.auditing;

import org.springframework.lang.Nullable;

/**
 * This interface can be implemented to store modification information (who and when modified lastly).
 *
 * @author Lee
 * @date 2022-05-18 15:25
 */
public interface IMayHaveCreatorWithUser<TCreator> extends IMayHaveCreator {

    /**
     * Reference to the creator.
     *
     * @return
     */
    @Nullable
    TCreator getCreator();

    void setCreator(TCreator creator);
}
