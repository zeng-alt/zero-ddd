package com.zjj.excel.component.read;

import cn.idev.excel.FastExcelFactory;
import com.zjj.excel.component.handler.I18nHeadWriteHandler;
import com.zjj.excel.component.i18n.I18nData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月04日 09:06
 */
@SpringBootTest
public class TestRead {


    @Test
    public  void testReadI18n() {
        LocaleContextHolder.setLocale(Locale.CHINA);
        List<I18nData> chinaList = FastExcelFactory.read(".\\testWriteI18n.xlsx")
                .head(I18nData.class)
//                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doReadSync();

        LocaleContextHolder.setLocale(Locale.US);
        List<I18nData> usList = FastExcelFactory.read(".\\testWriteI18nUs.xlsx")
                .head(I18nData.class)
//                .autoCloseStream(false)
                // i18n国际化
                .sheet("i18n")
                .doReadSync();


        System.out.println();
    }
}
