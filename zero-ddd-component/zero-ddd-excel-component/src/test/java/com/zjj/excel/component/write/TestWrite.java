package com.zjj.excel.component.write;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.util.ListUtils;
import com.zjj.excel.component.handler.I18nHeadWriteHandler;
import com.zjj.excel.component.i18n.I18nData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 14:48
 */
@SpringBootTest
class TestWrite {

    @Test
    void testWriteI18n() throws FileNotFoundException {
        // 拿到写入流

        LocaleContextHolder.setLocale(Locale.CHINA);
        FastExcelFactory.write(".\\testWriteI18n.xlsx", I18nData.class)
//                .autoCloseStream(false)
                // i18n国际化
                .registerWriteHandler(new I18nHeadWriteHandler())
                .sheet("i18n")
                .doWrite(data());

        LocaleContextHolder.setLocale(Locale.US);
        FastExcelFactory.write(".\\testWriteI18nUs.xlsx", I18nData.class)
//                .autoCloseStream(false)
                // i18n国际化
                .registerWriteHandler(new I18nHeadWriteHandler())
                .sheet("i18n")
                .doWrite(data());
    }

    private List<I18nData> data() {
        List<I18nData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            I18nData data = new I18nData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            data.setIgnore("ignore");
            list.add(data);
        }
        return list;
    }
}
