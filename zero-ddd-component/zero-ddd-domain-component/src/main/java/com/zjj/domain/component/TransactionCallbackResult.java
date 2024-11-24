package com.zjj.domain.component;

import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 21:14
 */
public interface TransactionCallbackResult<T> extends TransactionCallback<T> {

    @Nullable
    @Override
    default T doInTransaction(TransactionStatus status) {
        try {
            return doInTransactionResult();
        } catch (Exception e) {
            status.setRollbackOnly();
            throw e;
        }
    }

    T doInTransactionResult();
}
