package com.ueueo.uow;

import com.ueueo.ID;
import com.ueueo.IDisposable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 20:33
 */
public interface IUnitOfWork extends IDatabaseApiContainer, ITransactionApiContainer, IDisposable {
    ID getId();

    Map<String,Object> getItems();

    //TODO: Switch to OnFailed (sync) and OnDisposed (sync) methods to be compatible with OnCompleted
    BiConsumer<IUnitOfWork,UnitOfWorkFailedEventArgs> failed();

    BiConsumer<IUnitOfWork,UnitOfWorkEventArgs> disposed();

    IAbpUnitOfWorkOptions getOptions();

    IUnitOfWork getOuter();

    boolean  isReserved();

    boolean isDisposed();

    boolean isCompleted();

    String getReservationName();

    void setOuter(@Nullable IUnitOfWork outer);

    void initialize(@Nullable AbpUnitOfWorkOptions options);

    void reserve(@NonNull String reservationName);

    void saveChanges();

    void complete( );

    void rollback( );

    void onCompleted(Runnable handler);


    void addOrReplaceLocalEvent(
            UnitOfWorkEventRecord eventRecord,
            Predicate<UnitOfWorkEventRecord> replacementSelector
    );

    void addOrReplaceDistributedEvent(
            UnitOfWorkEventRecord eventRecord,
            Predicate<UnitOfWorkEventRecord> replacementSelector
    );
}
