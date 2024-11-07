package com.zjj.i18n.component.config;

import com.zjj.autoconfigure.component.core.ResponseAdviceProvider;
import jakarta.validation.Validator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月06日 20:50
 */
@AutoConfiguration()
public class LocaleConfiguration {



    /**
     * 使用自定义LocalValidatorFactoryBean，
     * 设置Spring国际化消息源，用户jsr303验证信息实现自定义国际化
     *
     */
    @Bean
    public Validator getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource, ObjectProvider<MessageBaseNameProvider> messageBaseNameProviders) {
        if (messageSource instanceof ResourceBundleMessageSource resourceBundleMessageSource) {
            messageBaseNameProviders.orderedStream().forEach(m -> resourceBundleMessageSource.addBasenames(m.getMessageBaseName()));
        }
        return new MessageSourceAccessor(messageSource);
    }

    @Bean
    public ResponseAdviceProvider i18nResponseAdviceProvider(MessageSourceAccessor messageSourceAccessor) {
        return response -> {
            String message = response.getMessage();
            return response.message(messageSourceAccessor.getMessage(message, message));
        };
    }
}
