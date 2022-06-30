package com.ueueo.uow;

import com.ueueo.SystemException;
import lombok.Data;

//TODO: Implement default options!

/**
 * Global (default) unit of work options
 */
@Data
public class AbpUnitOfWorkDefaultOptions {
    /**
     * Default value: <see cref="UnitOfWorkTransactionBehavior.Auto"/>.
     */
    private UnitOfWorkTransactionBehaviorEnum transactionBehavior = UnitOfWorkTransactionBehaviorEnum.Auto;

    private IsolationLevel isolationLevel;

    private Integer timeout;

    AbpUnitOfWorkOptions normalize(AbpUnitOfWorkOptions options) {
        if (options.getIsolationLevel() == null) {
            options.setIsolationLevel(isolationLevel);
        }

        if (options.getTimeout() == null) {
            options.setTimeout(timeout);
        }

        return options;
    }

    public boolean calculateIsTransactional(boolean autoValue) {
        if (UnitOfWorkTransactionBehaviorEnum.Enabled.equals(transactionBehavior)) {
            return true;
        } else if (UnitOfWorkTransactionBehaviorEnum.Disabled.equals(transactionBehavior)) {
            return false;
        } else if (UnitOfWorkTransactionBehaviorEnum.Auto.equals(transactionBehavior)) {
            return autoValue;
        } else {
            throw new SystemException("Not implemented TransactionBehavior value: " + transactionBehavior);
        }
    }
}
