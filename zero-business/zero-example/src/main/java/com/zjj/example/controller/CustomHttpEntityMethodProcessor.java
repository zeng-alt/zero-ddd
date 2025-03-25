package com.zjj.example.controller;/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Resolves {@link HttpEntity} and {@link RequestEntity} method argument values,
 * as well as return values of type {@link HttpEntity}, {@link ResponseEntity},
 * {@link ErrorResponse} and {@link ProblemDetail}.
 *
 * <p>An {@link HttpEntity} return type has a specific purpose. Therefore, this
 * handler should be configured ahead of handlers that support any return
 * value type annotated with {@code @ModelAttribute} or {@code @ResponseBody}
 * to ensure they don't take over.
 *
 * @author Arjen Poutsma
 * @author Rossen Stoyanchev
 * @author Brian Clozel
 * @author Sam Brannen
 * @since 3.1
 */
@Slf4j
public class CustomHttpEntityMethodProcessor extends AbstractMessageConverterMethodProcessor {

    @Setter
    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;
    private final MessageSourceAccessor messageSourceAccessor;

    protected CustomHttpEntityMethodProcessor(List<HttpMessageConverter<?>> converters, List<ErrorResponse.Interceptor> errorResponseInterceptors, MessageSourceAccessor messageSourceAccessor) {
        super(converters, null, null, errorResponseInterceptors);
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        Class<?> type = returnType.getParameterType();
        return (com.zjj.autoconfigure.component.core.ResponseEntity.class.isAssignableFrom(type) && !RequestEntity.class.isAssignableFrom(type)) || String.class.isAssignableFrom(type);
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        mavContainer.setRequestHandled(true);
        if (returnValue == null) {
            return;
        }

        ServletServerHttpRequest inputMessage = createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = createOutputMessage(webRequest);

        HttpEntity<?> httpEntity;

        if (returnValue instanceof String body) {
            URI requestUri = inputMessage.getURI();
            if ("ok".equals(body)) {
                httpEntity = ResponseEntity.ok().build();
            } else if ("fail".equals(body)) {
                httpEntity = buildProblemDetailResponse(HttpStatus.INTERNAL_SERVER_ERROR, requestUri, "Internal Server Error", null);
            } else if (body.startsWith("ok:")) {
                String message = extractMessage(body);
                httpEntity = ResponseEntity.ok(message);
            } else if (body.startsWith("fail:")) {
                String message = extractMessage(body);
                httpEntity = buildProblemDetailResponse(HttpStatus.INTERNAL_SERVER_ERROR, requestUri, "Internal Server Error", message);
            } else {
                this.requestResponseBodyMethodProcessor.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
                return;
            }
        } else {
            Assert.isInstanceOf(HttpEntity.class, returnValue);
            httpEntity = (HttpEntity<?>) returnValue;
        }

        if (httpEntity.getBody() instanceof ProblemDetail detail) {
            if (detail.getInstance() == null) {
                URI path = URI.create(inputMessage.getServletRequest().getRequestURI());
                detail.setInstance(path);
            }
            if (log.isWarnEnabled() && httpEntity instanceof ResponseEntity<?> responseEntity) {
                if (responseEntity.getStatusCode().value() != detail.getStatus()) {
                    log.warn(returnType.getExecutable().toGenericString() +
                            " returned ResponseEntity: " + responseEntity + ", but its status" +
                            " doesn't match the ProblemDetail status: " + detail.getStatus());
                }
            }
            invokeErrorResponseInterceptors(
                    detail, (returnValue instanceof ErrorResponse response ? response : null));
        }

        HttpHeaders outputHeaders = outputMessage.getHeaders();
        HttpHeaders entityHeaders = httpEntity.getHeaders();
        if (!entityHeaders.isEmpty()) {
            entityHeaders.forEach((key, value) -> {
                if (HttpHeaders.VARY.equals(key) && outputHeaders.containsKey(HttpHeaders.VARY)) {
                    List<String> values = getVaryRequestHeadersToAdd(outputHeaders, entityHeaders);
                    if (!values.isEmpty()) {
                        outputHeaders.setVary(values);
                    }
                }
                else {
                    outputHeaders.put(key, value);
                }
            });
        }

        if (httpEntity instanceof com.zjj.autoconfigure.component.core.ResponseEntity<?> responseEntity) {
            int returnStatus = responseEntity.getStatus();
            outputMessage.getServletResponse().setStatus(returnStatus);
            if (returnStatus == 200) {
                HttpMethod method = inputMessage.getMethod();
                if ((HttpMethod.GET.equals(method) || HttpMethod.HEAD.equals(method))
                        && isResourceNotModified(inputMessage, outputMessage)) {
                    outputMessage.flush();
                    return;
                }
            }
            else if (returnStatus / 100 == 3) {
                String location = outputHeaders.getFirst("location");
                if (location != null) {
                    saveFlashAttributes(mavContainer, webRequest, location);
                }
            }
        }

        // Try even with null body. ResponseBodyAdvice could get involved.
        writeWithMessageConverters(httpEntity.getBody(), returnType, inputMessage, outputMessage);

        // Ensure headers are flushed even if no body was written.
        outputMessage.flush();
    }

    private String extractMessage(String body) {
        String message = body.substring(4);
        if (isEnclosedInBraces(message)) {
            message = messageSourceAccessor.getMessage(getStringInsideBraces(message), message);
        }
        return message;
    }

    private HttpEntity<?> buildProblemDetailResponse(@NonNull HttpStatus status, URI requestUri, String title, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setInstance(requestUri);
        problemDetail.setTitle(title);
        if (message != null) {
            problemDetail.setDetail(message);
        }
        return ResponseEntity.status(status).body(problemDetail);
    }

    /**
     * 判断字符串是否被{}包含
     */
    public boolean isEnclosedInBraces(String str) {
        if (str == null || str.length() < 2) {
            return false;
        }
        return str.startsWith("{") && str.endsWith("}");
    }

    public String getStringInsideBraces(String str) {
        if (str == null || !str.startsWith("{") || !str.endsWith("}")) {
            return str;
        }
        // 获取 { 和 } 的位置
        int start = str.indexOf("{") + 1;
        int end = str.indexOf("}", start);
        return str.substring(start, end);
    }




    private void saveFlashAttributes(ModelAndViewContainer mav, NativeWebRequest request, String location) {
        mav.setRedirectModelScenario(true);
        ModelMap model = mav.getModel();
        if (model instanceof RedirectAttributes redirectAttributes) {
            Map<String, ?> flashAttributes = redirectAttributes.getFlashAttributes();
            if (!CollectionUtils.isEmpty(flashAttributes)) {
                HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);
                HttpServletResponse res = request.getNativeResponse(HttpServletResponse.class);
                if (req != null) {
                    RequestContextUtils.getOutputFlashMap(req).putAll(flashAttributes);
                    if (res != null) {
                        RequestContextUtils.saveOutputFlashMap(location, req, res);
                    }
                }
            }
        }
    }

    private boolean isResourceNotModified(ServletServerHttpRequest request, ServletServerHttpResponse response) {
        ServletWebRequest servletWebRequest =
                new ServletWebRequest(request.getServletRequest(), response.getServletResponse());
        HttpHeaders responseHeaders = response.getHeaders();
        String etag = responseHeaders.getETag();
        long lastModifiedTimestamp = responseHeaders.getLastModified();
        if (request.getMethod() == HttpMethod.GET || request.getMethod() == HttpMethod.HEAD) {
            responseHeaders.remove(HttpHeaders.ETAG);
            responseHeaders.remove(HttpHeaders.LAST_MODIFIED);
        }

        return servletWebRequest.checkNotModified(etag, lastModifiedTimestamp);
    }

    private List<String> getVaryRequestHeadersToAdd(HttpHeaders responseHeaders, HttpHeaders entityHeaders) {
        List<String> entityHeadersVary = entityHeaders.getVary();
        List<String> vary = responseHeaders.get(HttpHeaders.VARY);
        if (vary != null) {
            List<String> result = new ArrayList<>(entityHeadersVary);
            for (String header : vary) {
                for (String existing : StringUtils.tokenizeToStringArray(header, ",")) {
                    if ("*".equals(existing)) {
                        return Collections.emptyList();
                    }
                    for (String value : entityHeadersVary) {
                        if (value.equalsIgnoreCase(existing)) {
                            result.remove(value);
                        }
                    }
                }
            }
            return result;
        }
        return entityHeadersVary;
    }

    /**
     * Whether the given {@linkplain MethodParameter method parameter} is
     * supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }
}
