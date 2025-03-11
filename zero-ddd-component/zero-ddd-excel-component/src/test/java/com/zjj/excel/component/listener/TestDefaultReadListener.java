package com.zjj.excel.component.listener;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.read.listener.PageReadListener;
import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.excel.component.i18n.I18nData;
import jakarta.annotation.Resource;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月05日 16:32
 */
@SpringBootTest
public class TestDefaultReadListener {

    @Resource
    private ValidatorFactory validatorFactory;

    @Test
    public void testConstructor() {
        new DefaultReadListener<>(I18nData.class);
    }


    @Test
    public void testRead() throws IOException {
        LocaleContextHolder.setLocale(Locale.CHINA);
        DefaultReadListener<I18nData> listener = new DefaultReadListener<>(I18nData.class);
        listener.setInvokeConsumer((t, context) -> {
            System.out.println(t);
        });
        FastExcelFactory.read(".\\testWriteI18n.xlsx")
                .head(I18nData.class)
                .registerReadListener(listener)
                .useDefaultListener(false)
                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doRead();

        LocaleContextHolder.setLocale(Locale.US);
        listener.setInvokeConsumer((t, context) -> {
            System.out.println(t);
        });
        FastExcelFactory.read(".\\testWriteI18nUs.xlsx")
                .head(I18nData.class)
                .registerReadListener(listener)
                .useDefaultListener(false)
                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doRead();

    }


    @Test
    public void testRead1() throws IOException {
        LocaleContextHolder.setLocale(Locale.CHINA);
        DefaultReadListener<I18nData> listener = new DefaultReadListener<>(I18nData.class);
        List<I18nData> list = FastExcelFactory.read(".\\testWriteI18n.xlsx")
                .head(I18nData.class)
                .registerReadListener(listener)
                .useDefaultListener(false)
                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doReadSync();

        System.out.println(list);

        LocaleContextHolder.setLocale(Locale.US);
        listener = new DefaultReadListener<>(I18nData.class);
        list = FastExcelFactory.read(".\\testWriteI18nUs.xlsx")
                .head(I18nData.class)
                .registerReadListener(listener)
                .useDefaultListener(false)
                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doReadSync();

        System.out.println(list);


    }

    @Test
    public void testRead2() throws IOException {
        LocaleContextHolder.setLocale(Locale.CHINA);
        DefaultReadListener<I18nData> listener = new DefaultReadListener<>(I18nData.class);
        PageReadListener<I18nData> pageReadListener = new PageReadListener((Consumer<List>) System.out::println, 2);
        FastExcelFactory.read(".\\testWriteI18n.xlsx")
                .head(I18nData.class)
                .registerReadListener(listener)
                .registerReadListener(pageReadListener)
                .useDefaultListener(false)
                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doRead();
        LocaleContextHolder.setLocale(Locale.US);
        FastExcelFactory.read(".\\testWriteI18nUs.xlsx")
                .head(I18nData.class)
                .registerReadListener(listener)
                .registerReadListener(pageReadListener)
                .useDefaultListener(false)
                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doRead();
    }
}
