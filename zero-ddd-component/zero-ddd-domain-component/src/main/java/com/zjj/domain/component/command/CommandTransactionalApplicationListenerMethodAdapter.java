package com.zjj.domain.component.command;

import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.transaction.event.*;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月18日 21:37
 * @version 1.0
 */
public class CommandTransactionalApplicationListenerMethodAdapter extends ApplicationListenerMethodAdapter
        implements TransactionalApplicationListener<ApplicationEvent> {
    private final TransactionPhase transactionPhase;

    private final List<SynchronizationCallback> callbacks = new CopyOnWriteArrayList<>();


    /**
     * Construct a new TransactionalApplicationListenerMethodAdapter.
     * @param beanName the name of the bean to invoke the listener method on
     * @param targetClass the target class that the method is declared on
     * @param method the listener method to invoke
     */
    public CommandTransactionalApplicationListenerMethodAdapter(String beanName, Class<?> targetClass, Method method) {
        super(beanName, targetClass, method);
        CommandHandler eventAnn =
                AnnotatedElementUtils.findMergedAnnotation(getTargetMethod(), CommandHandler.class);
        if (eventAnn == null) {
            throw new IllegalStateException("No TransactionalEventListener annotation found on method: " + method);
        }
        this.transactionPhase = TransactionPhase.AFTER_COMMIT;
    }


    @Override
    public TransactionPhase getTransactionPhase() {
        return this.transactionPhase;
    }

    @Override
    public void addCallback(SynchronizationCallback callback) {
        Assert.notNull(callback, "SynchronizationCallback must not be null");
        this.callbacks.add(callback);
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
//        if (TransactionalApplicationListenerSynchronization.register(event, this, this.callbacks)) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("Registered transaction synchronization for " + event);
//            }
//        }
//        else
        if (isDefaultExecution()) {
            if (getTransactionPhase() == TransactionPhase.AFTER_ROLLBACK && logger.isWarnEnabled()) {
                logger.warn("Processing " + event + " as a fallback execution on AFTER_ROLLBACK phase");
            }
            processEvent(event);
        }
        else {
            // No transactional event execution at all
            if (logger.isDebugEnabled()) {
                logger.debug("No transaction is active - skipping " + event);
            }
        }
    }
}
