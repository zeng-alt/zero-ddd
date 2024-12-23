package com.zjj.tenant.datasource.component;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.autoconfigure.component.core.Response;
import com.zjj.core.component.api.ExceptionResponse;
import com.zjj.tenant.database.component.TenantDataSourceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 16:28
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class MultiTenancyExceptionAdvice {

    @ExceptionHandler(TenantDataSourceException.class)
    public Response<Void> exception(TenantDataSourceException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("{} 请求异常: ", requestURI, e);
        return ExceptionResponse.of(e.getCode(), e.getMessage(), request);
    }
}
