package com.zjj.excel.component.processor.mvc;

import com.zjj.excel.component.annotation.ExcelImport;
import com.zjj.excel.component.utils.ExcelHelper;
import com.zjj.excel.component.utils.RxjavaExcelHelper;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.*;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 21:13
 */
public class HttpExcelMethodProcessor extends AbstractMessageConverterMethodArgumentResolver {

    public HttpExcelMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    /**
     * 查询参数是否有@ExceImport注解.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ExcelImport.class);
    }

    /**
     * 拿到带有@ExceImport注解的参数. 把文件流转成对象集合.
     *
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return the resolved argument value, or {@code null} if not resolvable
     * @throws Exception in case of errors with the preparation of argument values
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ExcelImport excelImport = parameter.getParameterAnnotation(ExcelImport.class);
        Type paramType = getHttpExcelType(parameter);
        boolean isMultiple = false;
        if (TypeUtils.isAssignable(Collection.class, paramType) || TypeUtils.isAssignable(Flowable.class, paramType)) {
            paramType = getHttpExcelType(paramType);
            isMultiple = true;
        }
        if (paramType == null) {
            throw new IllegalArgumentException("ExcelEntity parameter '" + parameter.getParameterName() +
                    "' in method " + parameter.getMethod() + " is not parameterized");
        }

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (!(servletRequest instanceof MultipartHttpServletRequest)) {
            return Collections.emptyList();
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) servletRequest;
        List<MultipartFile> files = multipartRequest.getFiles(StringUtils.hasText(excelImport.name()) ? excelImport.name() : parameter.getParameterName());
        if (CollectionUtils.isEmpty(files) && excelImport.required()) {
            throw new IllegalArgumentException("ExcelEntity parameter '" + parameter.getParameterName() +
                    "' in method " + parameter.getMethod() + " is required");
        }


        if (TypeUtils.isAssignable(Collection.class, parameter.getParameterType())) {
            return doInvokeCollection(files, paramType, isMultiple);
        } else if (TypeUtils.isAssignable(Flowable.class, parameter.getParameterType())) {
            return doInvokeFlowable(files, paramType, isMultiple);
        }

        return null;
    }

    private Object doInvokeFlowable(List<MultipartFile> files, Type paramType, boolean isMultiple) throws IOException {
        if (CollectionUtils.isEmpty(files)) {
            return Flowable.empty();
        }

        if (!isMultiple) {
           return RxjavaExcelHelper
                   .importRxjavaExcel(files.iterator().next().getInputStream(), (Class<?>) paramType)
                   .doOnError(e -> logger.error("HttpExcelMethodProcessor" + e));
        }

        return Flowable.fromIterable(files.stream().map(file -> {
            try {
                return RxjavaExcelHelper.importRxjavaExcel(files.iterator().next().getInputStream(), (Class<?>) paramType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList());
    }

    private Object doInvokeCollection(List<MultipartFile> files, Type paramType, boolean isMultiple) throws IOException {
        if (CollectionUtils.isEmpty(files)) {
            return Collections.emptyList();
        }

        if (!isMultiple) {
            return ExcelHelper.importExcel(files.iterator().next().getInputStream(), (Class<?>) paramType);
        }

        List result = new ArrayList();
        for (MultipartFile file : files) {
            result.add(ExcelHelper.importExcel(file.getInputStream(), (Class<?>) paramType));
        }
        return result;
    }

    private Type getHttpExcelType(MethodParameter parameter) {
        Type parameterType = parameter.getGenericParameterType();
        if (parameterType instanceof ParameterizedType type) {
            if (type.getActualTypeArguments().length != 1) {
                throw new IllegalArgumentException("Expected single generic parameter on '" +
                        parameter.getParameterName() + "' in method " + parameter.getMethod());
            }
            return type.getActualTypeArguments()[0];
        }
        else if (parameterType instanceof Class) {
            return Object.class;
        }
        else {
            return null;
        }
    }

    private Type getHttpExcelType(Type parameterType) {

        if (parameterType instanceof ParameterizedType type) {
            if (type.getActualTypeArguments().length != 1) {
                throw new IllegalArgumentException("Expected single generic parameter on '" + type.getTypeName());
            }
            return type.getActualTypeArguments()[0];
        }
        else if (parameterType instanceof Class) {
            return Object.class;
        }
        else {
            return null;
        }
    }
}
