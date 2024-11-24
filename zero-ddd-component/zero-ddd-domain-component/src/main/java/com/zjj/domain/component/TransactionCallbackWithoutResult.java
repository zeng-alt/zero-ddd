package com.zjj.domain.component;

import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 16:42
 */
public interface TransactionCallbackWithoutResult extends TransactionCallback<Object> {

    @Nullable
    @Override
    default Object doInTransaction(TransactionStatus status) {
        try {
            doInTransactionWithoutResult();
        } catch (Exception e) {
            status.setRollbackOnly();
            throw e;
        }
        return null;
    }

    void doInTransactionWithoutResult();
}
