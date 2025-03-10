package com.zjj.excel.component.configuration;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.read.builder.ExcelReaderBuilder;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.write.builder.ExcelWriterBuilder;
import com.zjj.excel.component.listener.DefaultReadListener;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月10日 21:04
 */
public interface ExcelTemplate {

    public ExcelReaderBuilder buildReader();

    public ExcelWriterBuilder builderWriter();


    <T> ExcelReaderBuilder read(Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param file File to read.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder read(File file, Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder read(File file, Class<T> tClass, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder read(File file, Class<T> tClass, Class head, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param pathName File path to read.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder read(String pathName, Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder read(String pathName, Class<T> tClass, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder read(String pathName, Class<T> tClass, Class head, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param inputStream Input stream to read.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> tClass, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder read(InputStream inputStream, Class<T> tClass, Class head, ReadListener readListener);

    ExcelWriterBuilder write();

    /**
     * Build excel the write
     *
     * @param file File to write
     * @return Excel writer builder
     */
    ExcelWriterBuilder write(File file);

    /**
     * Build excel the write
     *
     * @param file File to write
     * @param head Annotate the class for configuration information
     * @return Excel writer builder
     */
    ExcelWriterBuilder write(File file, Class head);

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @return Excel writer builder
     */
    ExcelWriterBuilder write(String pathName);

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @param head     Annotate the class for configuration information
     * @return Excel writer builder
     */
    ExcelWriterBuilder write(String pathName, Class head);

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @return Excel writer builder
     */
    ExcelWriterBuilder write(OutputStream outputStream);
    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @param head         Annotate the class for configuration information.
     * @return Excel writer builder
     */
    ExcelWriterBuilder write(OutputStream outputStream, Class head);


    <T> ExcelReaderBuilder dynamicRead(Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param file File to read.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder dynamicRead(File file, Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder dynamicRead(File file, Class<T> tClass, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder dynamicRead(File file, Class<T> tClass, Class head, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param pathName File path to read.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder dynamicRead(String pathName, Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder dynamicRead(String pathName, Class<T> tClass, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    <T> ExcelReaderBuilder dynamicRead(String pathName, Class<T> tClass, Class head, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param inputStream Input stream to read.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder dynamicRead(InputStream inputStream, Class<T> tClass);

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder dynamicRead(InputStream inputStream, Class<T> tClass, ReadListener readListener);

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public <T> ExcelReaderBuilder dynamicRead(InputStream inputStream, Class<T> tClass, Class head, ReadListener readListener);

    ExcelWriterBuilder dynamicWrite();

    /**
     * Build excel the write
     *
     * @param file File to write
     * @return Excel writer builder
     */
    ExcelWriterBuilder dynamicWrite(File file);

    /**
     * Build excel the write
     *
     * @param file File to write
     * @param head Annotate the class for configuration information
     * @return Excel writer builder
     */
    ExcelWriterBuilder dynamicWrite(File file, Class head);

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @return Excel writer builder
     */
    ExcelWriterBuilder dynamicWrite(String pathName);

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @param head     Annotate the class for configuration information
     * @return Excel writer builder
     */
    ExcelWriterBuilder dynamicWrite(String pathName, Class head);

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @return Excel writer builder
     */
    ExcelWriterBuilder dynamicWrite(OutputStream outputStream);
    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @param head         Annotate the class for configuration information.
     * @return Excel writer builder
     */
    ExcelWriterBuilder dynamicWrite(OutputStream outputStream, Class head);

}
