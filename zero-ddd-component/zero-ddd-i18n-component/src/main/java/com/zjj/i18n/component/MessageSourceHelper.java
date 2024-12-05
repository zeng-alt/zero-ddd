package com.zjj.i18n.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.Nullable;

import java.util.Locale;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:05
 */
public class MessageSourceHelper implements ApplicationContextAware {

    private MessageSourceHelper() {

    }

    private static MessageSourceAccessor messageSourceAccessor;


    public static MessageSourceHelper create() {
        return new MessageSourceHelper();
    }


    public static String getMessage(String code) throws NoSuchMessageException {
        return messageSourceAccessor.getMessage(code);
    }

    public static String getMessage(String code, String defaultMessage) throws NoSuchMessageException {
        return messageSourceAccessor.getMessage(code, defaultMessage);
    }

    public static String getMessage(String code, Locale locale) throws NoSuchMessageException {
        return messageSourceAccessor.getMessage(code, locale);
    }

    public static String getMessage(String code, String defaultMessage, Locale locale) throws NoSuchMessageException {
        return messageSourceAccessor.getMessage(code, defaultMessage, locale);
    }

    public static String getMessage(String code, String defaultMessage, Object... args) {
        return messageSourceAccessor.getMessage(code, args, defaultMessage);
    }

    public static String getMessage(String code, @Nullable Object... args) {
        return getMessage(code, code, args);
    }


    public static String getMessage(String code, @Nullable Object[] args, String defaultMessage, Locale locale) {
        return messageSourceAccessor.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        messageSourceAccessor = applicationContext.getBean(MessageSourceAccessor.class);
    }
}