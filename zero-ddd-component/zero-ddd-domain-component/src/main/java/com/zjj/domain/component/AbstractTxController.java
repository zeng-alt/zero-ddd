package com.zjj.domain.component;

import jakarta.annotation.Resource;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 21:25
 */
public abstract class AbstractTxController {

    @Resource
    protected TransactionTemplate transactionTemplate;

    public void execute(Action action) {
        transactionTemplate.execute((TransactionCallbackWithoutResult) action::run);
    }

    public <T> T execute(Supplier<T> supplier) {
        return (T) transactionTemplate.execute((TransactionCallbackResult) () -> supplier.get());
    }
}
