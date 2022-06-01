package com.ueueo.uow;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Data
public class UnitOfWorkEventArgs extends EventArgs {
    /**
     * Reference to the unit of work related to this event.
     */
    private IUnitOfWork unitOfWork;

    public UnitOfWorkEventArgs(@NonNull IUnitOfWork unitOfWork) {
        Objects.requireNonNull(unitOfWork);
        this.unitOfWork = unitOfWork;
    }
}
