package com.zjj.domain.component.command;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmolecules.architecture.cqrs.Command;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ErrorHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:30
 * @version 1.0
 */
public class SimpleCommandDispatcher implements CommandDispatcher {

    private ListMultimap<String, ApplicationListener<?>> listenersCache;
    private final Map<Class<?>, String> cachedKeys = new ConcurrentHashMap<>();

    @Nullable
    private ErrorHandler errorHandler;
    @Nullable
    private volatile Log lazyLogger;

    @Override
    public void addApplicationListener(String key, ApplicationListener<?> listener) {
        ImmutableListMultimap<String, ApplicationListener<?>> listeners = ImmutableListMultimap.of(key, listener);

        if (listenersCache == null) {
            listenersCache = listeners;
        } else {
            listenersCache = ImmutableListMultimap.<String, ApplicationListener<?>>builder()
                    .putAll(listenersCache)
                    .putAll(listeners)
                    .build();
        }
    }

    @Override
    public void removeApplicationListener(String key) {

        if (listenersCache != null) {
            ImmutableListMultimap.Builder<String, ApplicationListener<?>> builder = ImmutableListMultimap.<String, ApplicationListener<?>>builder();
            for (var entry : listenersCache.entries()) {
                if (!entry.getKey().equals("key1")) {  // 排除掉 "key1" 对应的元素
                    builder.put(entry);
                }
            }
            listenersCache = builder.build();
        }
    }

    public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    /**
     * Return the current error handler for this multicaster.
     * @since 4.1
     */
    @Nullable
    protected ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override
    public void dispatches(ApplicationEvent command) {
        Object source = command.getSource();
        String key = this.cachedKeys.computeIfAbsent(source.getClass(), k -> resolvePolicyKey(source));
        listenersCache.get(key).forEach(listener -> invokeListener(listener, command));
    }

    protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
        ErrorHandler errorHandler = getErrorHandler();
        if (errorHandler != null) {
            try {
                doInvokeListener(listener, event);
            }
            catch (Throwable err) {
                errorHandler.handleError(err);
            }
        }
        else {
            doInvokeListener(listener, event);
        }
    }

    private boolean matchesClassCastMessage(String classCastMessage, Class<?> eventClass) {
        // On Java 8, the message starts with the class name: "java.lang.String cannot be cast..."
        if (classCastMessage.startsWith(eventClass.getName())) {
            return true;
        }
        // On Java 11, the message starts with "class ..." a.k.a. Class.toString()
        if (classCastMessage.startsWith(eventClass.toString())) {
            return true;
        }
        // On Java 9, the message used to contain the module name: "java.base/java.lang.String cannot be cast..."
        int moduleSeparatorIndex = classCastMessage.indexOf('/');
        if (moduleSeparatorIndex != -1 && classCastMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1)) {
            return true;
        }
        // Assuming an unrelated class cast failure...
        return false;
    }

    private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
        try {
            listener.onApplicationEvent(event);
        }
        catch (ClassCastException ex) {
            String msg = ex.getMessage();
            if (msg == null || matchesClassCastMessage(msg, event.getClass()) ||
                    (event instanceof PayloadApplicationEvent payloadEvent &&
                            matchesClassCastMessage(msg, payloadEvent.getPayload().getClass()))) {
                // Possibly a lambda-defined listener which we could not resolve the generic event type for
                // -> let's suppress the exception.
                Log loggerToUse = this.lazyLogger;
                if (loggerToUse == null) {
                    loggerToUse = LogFactory.getLog(getClass());
                    this.lazyLogger = loggerToUse;
                }
                if (loggerToUse.isTraceEnabled()) {
                    loggerToUse.trace("Non-matching event type for listener: " + listener, ex);
                }
            }
            else {
                throw ex;
            }
        }
    }


    private String resolvePolicyKey(Object command) {
        Command annotation = AnnotationUtils.findAnnotation(command.getClass(), Command.class);
        if (annotation == null) {
            return command.getClass().getName();
        }

        String key = annotation.namespace() + "." + annotation.name();
        return ".".equals(key) ? command.getClass().getName() : key;
    }
}
