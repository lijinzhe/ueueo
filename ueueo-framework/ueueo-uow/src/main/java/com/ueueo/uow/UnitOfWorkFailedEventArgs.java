package com.ueueo.uow;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Used as event arguments on <see cref="IUnitOfWork.Failed"/> event.
 */
@Getter
public class UnitOfWorkFailedEventArgs extends UnitOfWorkEventArgs {
    /**
     * Exception that caused failure. This is set only if an error occurred during <see cref="IUnitOfWork.CompleteAsync"/>.
     * Can be null if there is no exception, but <see cref="IUnitOfWork.CompleteAsync"/> is not called.
     * Can be null if another exception occurred during the UOW.
     */
    @Nullable
    private Exception exception;

    /**
     * True, if the unit of work is manually rolled back.
     */
    private boolean isRolledback;

    /**
     * Creates a new <see cref="UnitOfWorkFailedEventArgs"/> object.
     */
    public UnitOfWorkFailedEventArgs(@NonNull IUnitOfWork unitOfWork, @Nullable Exception exception, boolean isRolledback) {
        super(unitOfWork);
        this.exception = exception;
        this.isRolledback = isRolledback;
    }
}
