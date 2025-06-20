package com.zjj.security.abac.component.object;

import com.zjj.security.abac.component.annotation.ObjectMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.CoroutinesUtils;
import org.springframework.core.KotlinDetector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月04日 11:03
 */
@Slf4j
public class AuthorizeObjectMethodAdapter {

    private final String beanName;
    private final Method method;
//    private final Method targetMethod;
    @Nullable
    private ApplicationContext applicationContext;
//    private final List<ResolvableType> declaredEventTypes;


    public AuthorizeObjectMethodAdapter(String beanName, Method method) {
        this.beanName = beanName;
        this.method = BridgeMethodResolver.findBridgedMethod(method);
//        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
//                AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
    }

    /**
     * Initialize this instance.
     */
    void init(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

//    private static List<ResolvableType> resolveDeclaredEventTypes(Method method, @Nullable ObjectMethod ann) {
//        int count = (KotlinDetector.isSuspendingFunction(method) ? method.getParameterCount() - 1 : method.getParameterCount());
//        if (count > 1) {
//            throw new IllegalStateException(
//                    "Maximum one parameter is allowed for event listener method: " + method);
//        }
//
//        if (ann != null) {
//            Class<?>[] classes = ann.classes();
//            if (classes.length > 0) {
//                List<ResolvableType> types = new ArrayList<>(classes.length);
//                for (Class<?> eventType : classes) {
//                    types.add(ResolvableType.forClass(eventType));
//                }
//                return types;
//            }
//        }
//
//        if (count == 0) {
//            throw new IllegalStateException(
//                    "Event parameter is mandatory for event listener method: " + method);
//        }
//        return Collections.singletonList(ResolvableType.forMethodParameter(method, 0));
//    }


    public Object processObject(Object... args) {
//        Object[] args = resolveArguments(event);
        Object result = doInvoke(args);
        if (result != null) {
            return result;
        }

        log.trace("No result object given - no result to handle");
        return null;
    }

//    @Nullable
//    protected Object[] resolveArguments(ApplicationEvent event) {
//        ResolvableType declaredEventType = getResolvableType(event);
//        if (declaredEventType == null) {
//            return null;
//        }
//        if (this.method.getParameterCount() == 0) {
//            return new Object[0];
//        }
//        Class<?> declaredEventClass = declaredEventType.toClass();
//        if (!ApplicationEvent.class.isAssignableFrom(declaredEventClass) &&
//                event instanceof PayloadApplicationEvent<?> payloadEvent) {
//            Object payload = payloadEvent.getPayload();
//            if (declaredEventClass.isInstance(payload)) {
//                return new Object[] {payload};
//            }
//        }
//        return new Object[] {event};
//    }

    protected Object doInvoke(@Nullable Object... args) {
        Object bean = getTargetBean();
        // Detect package-protected NullBean instance through equals(null) check
        if (bean.equals(null)) {
            return null;
        }

        ReflectionUtils.makeAccessible(this.method);
        try {
            if (KotlinDetector.isSuspendingFunction(this.method)) {
                return CoroutinesUtils.invokeSuspendingFunction(this.method, bean, args);
            }
            return this.method.invoke(bean, args);
        }
        catch (IllegalArgumentException ex) {
            assertTargetBean(this.method, bean, args);
            throw new IllegalStateException(getInvocationErrorMessage(bean, ex.getMessage(), args), ex);
        }
        catch (IllegalAccessException ex) {
            throw new IllegalStateException(getInvocationErrorMessage(bean, ex.getMessage(), args), ex);
        }
        catch (InvocationTargetException ex) {
            // Throw underlying exception
            Throwable targetException = ex.getTargetException();
            if (targetException instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            else {
                String msg = getInvocationErrorMessage(bean, "Failed to invoke event listener method", args);
                throw new UndeclaredThrowableException(targetException, msg);
            }
        }
    }

    protected Object getTargetBean() {
        Assert.notNull(this.applicationContext, "ApplicationContext must not be null");
        return this.applicationContext.getBean(this.beanName);
    }



//    private ResolvableType getResolvableType(ApplicationEvent event) {
//        ResolvableType payloadType = null;
//        if (event instanceof PayloadApplicationEvent<?> payloadEvent) {
//            ResolvableType eventType = payloadEvent.getResolvableType();
//            if (eventType != null) {
//                payloadType = eventType.as(PayloadApplicationEvent.class).getGeneric();
//            }
//        }
//        for (ResolvableType declaredEventType : this.declaredEventTypes) {
//            Class<?> eventClass = declaredEventType.toClass();
//            if (!ApplicationEvent.class.isAssignableFrom(eventClass) &&
//                    payloadType != null && declaredEventType.isAssignableFrom(payloadType)) {
//                return declaredEventType;
//            }
//            if (eventClass.isInstance(event)) {
//                return declaredEventType;
//            }
//        }
//        return null;
//    }

    protected String getDetailedErrorMessage(Object bean, @Nullable String message) {
        StringBuilder sb = (StringUtils.hasLength(message) ? new StringBuilder(message).append('\n') : new StringBuilder());
        sb.append("HandlerMethod details: \n");
        sb.append("Bean [").append(bean.getClass().getName()).append("]\n");
        sb.append("Method [").append(this.method.toGenericString()).append("]\n");
        return sb.toString();
    }

    private void assertTargetBean(Method method, Object targetBean, @Nullable Object[] args) {
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        Class<?> targetBeanClass = targetBean.getClass();
        if (!methodDeclaringClass.isAssignableFrom(targetBeanClass)) {
            String msg = "The event listener method class '" + methodDeclaringClass.getName() +
                    "' is not an instance of the actual bean class '" +
                    targetBeanClass.getName() + "'. If the bean requires proxying " +
                    "(for example, due to @Transactional), please use class-based proxying.";
            throw new IllegalStateException(getInvocationErrorMessage(targetBean, msg, args));
        }
    }

    private String getInvocationErrorMessage(Object bean, @Nullable String message, @Nullable Object[] resolvedArgs) {
        StringBuilder sb = new StringBuilder(getDetailedErrorMessage(bean, message));
        sb.append("Resolved arguments: \n");
        for (int i = 0; i < resolvedArgs.length; i++) {
            sb.append('[').append(i).append("] ");
            if (resolvedArgs[i] == null) {
                sb.append("[null] \n");
            }
            else {
                sb.append("[type=").append(resolvedArgs[i].getClass().getName()).append("] ");
                sb.append("[value=").append(resolvedArgs[i]).append("]\n");
            }
        }
        return sb.toString();
    }
}
