package com.zjj.excel.component.listener;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.util.ListUtils;
import com.zjj.excel.component.dynamic.AbaDynamicColumn;
import com.zjj.excel.component.dynamic.DynamicEntity;
import com.zjj.excel.component.dynamic.ExcelDynamicProperty;
import com.zjj.excel.component.dynamic.InterfaceDynamicColumn;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 16:32
 */
@SpringBootTest
class TestDynamicReadListener {


    @Test
    void testConstructor() {
        DefaultDynamicReadListener<DemoData, DynamicEntity> listener = new DefaultDynamicReadListener<>(DemoData.class);
        assertThat(listener.getClazz()).isAssignableFrom(DemoData.class);
        assertThat(listener.getDynamicClazz()).isAssignableFrom(DynamicEntity.class);

        DefaultDynamicReadListener<DemoData1, DynamicEntity> listener1 = new DefaultDynamicReadListener<>(DemoData1.class);
        assertThat(listener1.getClazz()).isAssignableFrom(DemoData1.class);
        assertThat(listener1.getDynamicClazz()).isAssignableFrom(DynamicEntity.class);

        DefaultDynamicReadListener<DemoData2, DynamicEntity> listener2 = new DefaultDynamicReadListener<>(DemoData2.class);
        assertThat(listener2.getClazz()).isAssignableFrom(DemoData2.class);
        assertThat(listener2.getDynamicClazz()).isAssignableFrom(DynamicEntity.class);
    }


    @Test
    void testDynamicRead() throws FileNotFoundException {
        DefaultDynamicReadListener<DemoData, DynamicEntity> listener = new DefaultDynamicReadListener<>(DemoData.class);
        File file = ResourceUtils.getFile("classpath:demo" + File.separator + "demo.xlsx");
        FastExcelFactory
                .read(file)
                .autoCloseStream(false)
                .registerReadListener(listener)
                .sheet()
                .doRead();

        assertThat(listener.getList()).isNotNull();
    }

    @Test
    void testDynamicReadAndValidate() throws FileNotFoundException {
        DefaultDynamicReadListener<DemoValidateData, DynamicEntity> listener = new DefaultDynamicReadListener<>(DemoValidateData.class);
        File file = ResourceUtils.getFile("classpath:demo" + File.separator + "demo.xlsx");
        assertThatThrownBy(() ->
                FastExcelFactory
                    .read(file)
                    .autoCloseStream(false)
                    .registerReadListener(listener)
                    .sheet()
                    .doRead()
        )
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("ignore不能为空")
                .hasMessageContaining("字符串标题长度要大于20");

    }

    @Test
    void testDynamicReadAndCustomizeValidate() throws FileNotFoundException {
        DefaultDynamicReadListener<CustomizeValidateData, DynamicEntity> listener = new DefaultDynamicReadListener<>(CustomizeValidateData.class);
        File file = ResourceUtils.getFile("classpath:demo" + File.separator + "demo.xlsx");

        LocaleContextHolder.setLocale(Locale.CHINA);
        assertThatThrownBy(() ->
                FastExcelFactory
                        .read(file)
                        .autoCloseStream(false)
                        .registerReadListener(listener)
                        .sheet()
                        .doRead()
        )
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("动态字段长度要在 20 到 2,147,483,647 个字符之间。")
                .hasMessageContaining("字符串标题长度要大于20");
        LocaleContextHolder.setLocale(Locale.US);
        assertThatThrownBy(() ->
                FastExcelFactory
                        .read(file)
                        .autoCloseStream(false)
                        .registerReadListener(listener)
                        .sheet()
                        .doRead()
        )
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Dynamic fields should be between 20 and 2,147,483,647 characters long.")
                .hasMessageContaining("字符串标题长度要大于20");
    }


    @Test
    void testDynamicReadI18n() throws FileNotFoundException {
        DefaultDynamicReadListener<I18nData, DynamicEntity> listener = new DefaultDynamicReadListener<>(I18nData.class);
        File file = ResourceUtils.getFile("classpath:demo" + File.separator + "demo.xlsx");

        LocaleContextHolder.setLocale(Locale.CHINA);

        FastExcelFactory
                .read(file)
                .autoCloseStream(false)
                .registerReadListener(listener)
                .sheet()
                .doRead();

        List<I18nData> list = listener.getList();
        System.out.println(list);

    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class I18nData implements InterfaceDynamicColumn<DynamicEntity> {

        @ExcelProperty(value = "{string.title}", index = 2)
        private String string;

        @ExcelDynamicProperty
        protected @Delegate List<DynamicEntity> dynamicEntity = new ArrayList<>();

        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class CustomizeValidateData implements InterfaceDynamicColumn<DynamicEntity> {

        @ExcelProperty("字符串标题")
        private String string;

        @ExcelDynamicProperty
        protected @Delegate List<DynamicEntity> dynamicEntity = new ArrayList<>();

        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class DemoValidateData extends AbaDynamicColumn<DynamicEntity> {

        @ExcelProperty("字符串标题")
        @Size(message = "字符串标题长度要大于20", min = 20, max = 50)
        private String string;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        @NotNull(message = "ignore不能为空")
        private String ignore;
    }



    @Getter
    @Setter
    @EqualsAndHashCode
    public static class DemoData extends AbaDynamicColumn<DynamicEntity> {
        @ExcelProperty("字符串标题")
        private String string;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;
    }

    @Data
    public static class DemoData1 implements InterfaceDynamicColumn<DynamicEntity> {
        @ExcelProperty("字符串标题")
        private String string;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;

        @Override
        public boolean add(DynamicEntity dynamicEntity) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public List<DynamicEntity> getDynamicEntity() {
            return null;
        }

        @Override
        public List<List<String>> getData() {
            return null;
        }

        @Override
        public List<List<String>> getHeads() {
            return null;
        }
    }

    public static class DemoData2 extends TempData {
    }

    public static class TempData extends AbaDynamicColumn<DynamicEntity> {
    }




    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
//            data.setDate(new Date());
//            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
