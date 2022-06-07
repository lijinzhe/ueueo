package com.ueueo.uow;

public class AmbientUnitOfWork implements IAmbientUnitOfWork {

    private ThreadLocal<IUnitOfWork> _currentUow;

    @Override
    public IUnitOfWork getUnitOfWork() {
        return _currentUow.get();
    }

    public AmbientUnitOfWork() {
        _currentUow = new ThreadLocal<>();
    }

    @Override
    public void setUnitOfWork(IUnitOfWork unitOfWork) {
        _currentUow.set(unitOfWork);
    }

    @Override
    public IUnitOfWork getCurrentByChecking() {
        IUnitOfWork uow = getUnitOfWork();
        //Skip reserved unit of work
        while (uow != null && (uow.isReserved() || uow.isDisposed() || uow.isCompleted())) {
            uow = uow.getOuter();
        }
        return uow;
    }
}
