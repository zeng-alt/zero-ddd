package com.zjj.domain.component.command;

import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.event.TransactionalEventListenerFactory;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月18日 21:34
 * @version 1.0
 */
public class CommandTransactionalEventListenerFactory extends TransactionalEventListenerFactory {

    @NonNull
    private final TransactionTemplate transactionTemplate;


    public CommandTransactionalEventListenerFactory(TransactionTemplate transactionTemplate) {
        Assert.notNull(transactionTemplate, "TransactionTemplate must not be null");
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean supportsMethod(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, CommandHandler.class);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.transaction.event.TransactionalEventListenerFactory#createApplicationListener(java.lang.String, java.lang.Class, java.lang.reflect.Method)
     */
    @Override
    public ApplicationListener<?> createApplicationListener(String beanName, Class<?> type, Method method) {
        return new CommandTransactionalApplicationListenerMethodAdapter(beanName, type, method, transactionTemplate);
    }
}
