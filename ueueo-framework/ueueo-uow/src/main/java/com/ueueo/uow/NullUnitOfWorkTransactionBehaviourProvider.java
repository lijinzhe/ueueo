package com.ueueo.uow;

public class NullUnitOfWorkTransactionBehaviourProvider implements IUnitOfWorkTransactionBehaviourProvider {
    @Override
    public Boolean isTransactional() {
        return null;
    }

}
