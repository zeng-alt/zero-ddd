package com.zjj.excel.component.configuration;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.read.builder.ExcelReaderBuilder;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.write.builder.ExcelWriterBuilder;
import com.zjj.excel.component.handler.I18nHeadWriteHandler;
import com.zjj.excel.component.listener.DefaultReadListener;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月10日 09:05
 */
@RequiredArgsConstructor
public class DefaultExcelTemplate implements ExcelTemplate {

    private final GlobalConfiguration globalConfiguration;

    @Override
    public ExcelWriterBuilder builderWriter() {
        return FastExcelFactory
                .write()
                .registerWriteHandler(new I18nHeadWriteHandler())
                .filedCacheLocation(globalConfiguration.getFiledCacheLocation())
                .autoTrim(globalConfiguration.getAutoTrim())
                .use1904windowing(globalConfiguration.getUse1904windowing())
                .locale(globalConfiguration.getLocale());
    }

    @Override
    public ExcelReaderBuilder buildReader() {

        return FastExcelFactory
                .read()
                .useDefaultListener(false)
                .filedCacheLocation(globalConfiguration.getFiledCacheLocation())
                .autoTrim(globalConfiguration.getAutoTrim())
                .use1904windowing(globalConfiguration.getUse1904windowing())
                .locale(globalConfiguration.getLocale())
                .useScientificFormat(globalConfiguration.getUseScientificFormat());
    }

    @Override
    public <T> ExcelReaderBuilder read(Class<T> tClass) {
        return buildReader().registerReadListener(new DefaultReadListener<>(tClass));
    }

    /**
     * Build excel the read
     *
     * @param file File to read.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(File file, Class<T> tClass) {
        return read(file, tClass, null, null);
    }

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(File file, Class<T> tClass, ReadListener readListener) {
        return read(file, tClass, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(File file, Class<T> tClass, Class head, ReadListener readListener)  {
        ExcelReaderBuilder excelReaderBuilder = buildReader();
        excelReaderBuilder.file(file);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        excelReaderBuilder.registerReadListener(new DefaultReadListener<>(tClass));
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param pathName File path to read.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(String pathName, Class<T> tClass) {
        return read(pathName, tClass, null, null);
    }

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(String pathName, Class<T> tClass, ReadListener readListener) {
        return read(pathName, tClass, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(String pathName, Class<T> tClass, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = buildReader();
        excelReaderBuilder.file(pathName);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        excelReaderBuilder.registerReadListener(new DefaultReadListener<>(tClass));
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param inputStream Input stream to read.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> tClass) {
        return read(inputStream, tClass, null, null);
    }

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> tClass, ReadListener readListener) {
        return read(inputStream, tClass, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> tClass, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = buildReader();
        excelReaderBuilder.file(inputStream);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        excelReaderBuilder.registerReadListener(new DefaultReadListener<>(tClass));
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    @Override
    public ExcelWriterBuilder write() {
        return builderWriter();
    }

    /**
     * Build excel the write
     *
     * @param file File to write
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder write(File file) {
        return write(file, null);
    }

    /**
     * Build excel the write
     *
     * @param file File to write
     * @param head Annotate the class for configuration information
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder write(File file, Class head) {
        ExcelWriterBuilder excelWriterBuilder = builderWriter();
        excelWriterBuilder.file(file);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder write(String pathName) {
        return write(pathName, null);
    }

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @param head     Annotate the class for configuration information
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder write(String pathName, Class head) {
        ExcelWriterBuilder excelWriterBuilder = builderWriter();
        excelWriterBuilder.file(pathName);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder write(OutputStream outputStream) {
        return write(outputStream, null);
    }

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @param head         Annotate the class for configuration information.
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder write(OutputStream outputStream, Class head) {
        ExcelWriterBuilder excelWriterBuilder = builderWriter();
        excelWriterBuilder.file(outputStream);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    @Override
    public <T> ExcelReaderBuilder dynamicRead(Class<T> tClass) {
        return buildReader().registerReadListener(new DefaultReadListener<>(tClass));
    }

    /**
     * Build excel the read
     *
     * @param file File to read.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(File file, Class<T> tClass) {
        return read(file, tClass, null, null);
    }

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(File file, Class<T> tClass, ReadListener readListener) {
        return read(file, tClass, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(File file, Class<T> tClass, Class head, ReadListener readListener)  {
        ExcelReaderBuilder excelReaderBuilder = buildReader();
        excelReaderBuilder.file(file);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        excelReaderBuilder.registerReadListener(new DefaultReadListener<>(tClass));
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param pathName File path to read.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(String pathName, Class<T> tClass) {
        return read(pathName, tClass, null, null);
    }

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(String pathName, Class<T> tClass, ReadListener readListener) {
        return read(pathName, tClass, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(String pathName, Class<T> tClass, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = buildReader();
        excelReaderBuilder.file(pathName);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        excelReaderBuilder.registerReadListener(new DefaultReadListener<>(tClass));
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param inputStream Input stream to read.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(InputStream inputStream, Class<T> tClass) {
        return read(inputStream, tClass, null, null);
    }

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(InputStream inputStream, Class<T> tClass, ReadListener readListener) {
        return read(inputStream, tClass, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    @Override
    public <T> ExcelReaderBuilder dynamicRead(InputStream inputStream, Class<T> tClass, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = buildReader();
        excelReaderBuilder.file(inputStream);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        excelReaderBuilder.registerReadListener(new DefaultReadListener<>(tClass));
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    @Override
    public ExcelWriterBuilder dynamicWrite() {
        return builderWriter();
    }

    /**
     * Build excel the write
     *
     * @param file File to write
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder dynamicWrite(File file) {
        return write(file, null);
    }

    /**
     * Build excel the write
     *
     * @param file File to write
     * @param head Annotate the class for configuration information
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder dynamicWrite(File file, Class head) {
        ExcelWriterBuilder excelWriterBuilder = builderWriter();
        excelWriterBuilder.file(file);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder dynamicWrite(String pathName) {
        return write(pathName, null);
    }

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @param head     Annotate the class for configuration information
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder dynamicWrite(String pathName, Class head) {
        ExcelWriterBuilder excelWriterBuilder = builderWriter();
        excelWriterBuilder.file(pathName);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder dynamicWrite(OutputStream outputStream) {
        return write(outputStream, null);
    }

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @param head         Annotate the class for configuration information.
     * @return Excel writer builder
     */
    @Override
    public ExcelWriterBuilder dynamicWrite(OutputStream outputStream, Class head) {
        ExcelWriterBuilder excelWriterBuilder = builderWriter();
        excelWriterBuilder.file(outputStream);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }
}
