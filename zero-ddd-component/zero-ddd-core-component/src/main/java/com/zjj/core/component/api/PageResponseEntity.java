package com.zjj.core.component.api;


import com.zjj.autoconfigure.component.core.HttpEntityStatus;
import com.zjj.autoconfigure.component.core.ResponseEntity;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 14:58
 */
public class PageResponseEntity<T> extends HttpEntityStatus<PageEntity<Collection<T>>> {

    /**
     * Create a {@code ResponseEntity} with a status code only.
     * @param status the status code
     */
    protected PageResponseEntity(HttpStatusCode status) {
        this(null, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with a body and status code.
     * @param body the entity body
     * @param status the status code
     */
    protected PageResponseEntity(@Nullable PageEntity<Collection<T>> body, HttpStatusCode status) {
        this(body, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with headers and a status code.
     * @param headers the entity headers
     * @param status the status code
     */
    protected PageResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
        this(null, headers, status);
    }

    /**
     * Create a {@code ResponseEntity} with a status code only.
     * @param status the status code
     */
    protected PageResponseEntity(Integer status) {
        this(null, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with a body, headers, and a raw status code.
     * @param body the entity body
     * @param headers the entity headers
     * @param rawStatus the status code value
     * @since 5.3.2
     */
    protected PageResponseEntity(@Nullable PageEntity<Collection<T>> body, @Nullable MultiValueMap<String, String> headers, int rawStatus) {
        this(body, headers, HttpStatusCode.valueOf(rawStatus));
    }

    protected PageResponseEntity(@Nullable PageEntity<Collection<T>> body, @Nullable MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(body, headers, statusCode.value());
        Assert.notNull(statusCode, "HttpStatusCode must not be null");
    }

    public static PageResponseEntity<Void> of(int pageSize, int pageNum) {
        PageEntity<Collection<Void>> page = new PageEntity<>();
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        page.setData(Collections.emptyList());
        return new PageResponseEntity<>(page, HttpStatus.OK);
    }

    public static <T> PageResponseEntity<T> of(PageEntity<Collection<T>> pageEntity) {
        return new PageResponseEntity<>(pageEntity, HttpStatus.OK);
    }

    public static <T> PageResponseEntity<T> of(T body) {
        return of(new PageEntity<>(List.of(body)));
    }

    public static <T> PageResponseEntity<T> of(Collection<T> data, long totalCount, int pageSize, int pageNum) {
        PageEntity<Collection<T>> page = new PageEntity<>();
        page.setData(data);
        page.setTotal(totalCount);
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        return new PageResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * A shortcut for creating a {@code ResponseEntity} with the given body
     * and the {@linkplain HttpStatus#OK OK} status, or an empty body and a
     * {@linkplain HttpStatus#NOT_FOUND NOT FOUND} status in case of a
     * {@code null} parameter.
     * @return the created {@code ResponseEntity}
     * @since 6.0.5
     */
    public static <T> PageResponseEntity<T> ofNullable(@Nullable PageEntity<Collection<T>> body) {
        if (body == null) {
            return notFound().build();
        }
        return PageResponseEntity.ok(body);
    }

    public static <T> PageResponseEntity<T> ofNullable(@Nullable T... body) {
        if (body == null || body.length == 0) {
            return notFound().build();
        }
        return PageResponseEntity.ok(Arrays.asList(body));
    }

    /**
     * Create a builder with the given status.
     * @param status the response status
     * @return the created builder
     * @since 4.1
     */
    public static PageBodyBuilder status(HttpStatusCode status) {
        Assert.notNull(status, "HttpStatusCode must not be null");
        return new PageBuilder(status);
    }

    /**
     * Create a builder with the given status.
     * @param status the response status
     * @return the created builder
     * @since 4.1
     */
    public static PageBodyBuilder status(Integer status) {
        Assert.notNull(status, "status must not be null");
        return new PageBuilder(status);
    }

    /**
     * Create a builder with the status set to {@linkplain HttpStatus#OK OK}.
     * @return the created builder
     * @since 4.1
     */
    public static PageBodyBuilder ok() {
        return status(HttpStatus.OK);
    }

    /**
     * A shortcut for creating a {@code ResponseEntity} with the given body
     * and the status set to {@linkplain HttpStatus#OK OK}.
     * @param body the body of the response entity (possibly empty)
     * @return the created {@code ResponseEntity}
     * @since 4.1
     */
    public static <T> PageResponseEntity<T> ok(@Nullable PageEntity<Collection<T>> body) {
        return ok().body(body);
    }

    public static <T> PageResponseEntity<T> ok(@Nullable Collection<T> body) {
        return PageResponseEntity.ok(new PageEntity<>(body));
    }

    public static <T> PageResponseEntity<T> ok(@Nullable T body) {
        return ok(List.of(body));
    }

    public static <T> PageResponseEntity<T> of(@Nullable Optional<PageEntity<Collection<T>>> body) {
        Assert.notNull(body, "Body must not be null");
        return body.map(PageResponseEntity::ok).orElseGet(() -> notFound().build());
    }

    public static <T> PageResponseEntity<T> ofNullable(@Nullable T body) {
        if (body == null) {
            return notFound().build();
        }
        return PageResponseEntity.ok(body);
    }

    /**
     * Create a builder with an {@linkplain HttpStatus#ACCEPTED ACCEPTED} status.
     * @return the created builder
     * @since 4.1
     */
    public static PageBodyBuilder accepted() {
        return status(HttpStatus.ACCEPTED);
    }

    /**
     * Create a builder with a {@linkplain HttpStatus#NO_CONTENT NO_CONTENT} status.
     * @return the created builder
     * @since 4.1
     */
    public static ResponseEntity.HeadersBuilder<?> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    /**
     * Create a builder with a {@linkplain HttpStatus#BAD_REQUEST BAD_REQUEST} status.
     * @return the created builder
     * @since 4.1
     */
    public static PageBodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    /**
     * Create a builder with a {@linkplain HttpStatus#NOT_FOUND NOT_FOUND} status.
     * @return the created builder
     * @since 4.1
     */
    public static ResponseEntity.HeadersBuilder<?> notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    /**
     * Create a builder with an
     * {@linkplain HttpStatus#UNPROCESSABLE_ENTITY UNPROCESSABLE_ENTITY} status.
     * @return the created builder
     * @since 4.1.3
     */
    public static PageBodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Create a builder with an
     * {@linkplain HttpStatus#INTERNAL_SERVER_ERROR INTERNAL_SERVER_ERROR} status.
     * @return the created builder
     * @since 5.3.8
     */
    public static PageBodyBuilder internalServerError() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static PageBodyBuilder fail() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public interface PageBodyBuilder extends ResponseEntity.BodyBuilder<PageBodyBuilder> {
        <R extends PageBodyBuilder> R pageSize(Integer pageSize);

        <R extends PageBodyBuilder> R pageNum(Integer pageNum);

        <R extends PageBodyBuilder> R page(Integer pageNum, Integer pageSize);

        public <R extends PageBodyBuilder> R total(Long total);

        <R extends PageResponseEntity<T>, T> R body(@Nullable PageEntity<Collection<T>> body);

        <R extends PageResponseEntity<T>, T> R body(@Nullable Collection<T> body);

        <R extends PageResponseEntity<T>, T> R body();

        public <R extends PageResponseEntity<T>, T> R data(Collection<T> data);

        public <R extends PageResponseEntity<T>, T> R data(T... data);
    }

    protected static class PageBuilder extends ResponseEntity.DefaultBuilder<PageBodyBuilder> implements PageBodyBuilder {

        private final PageEntity<Collection<Object>> data = new PageEntity<>();

        public PageBuilder(int statusCode) {
            this(HttpStatusCode.valueOf(statusCode));
        }

        public PageBuilder(HttpStatusCode statusCode) {
            super(statusCode);
        }


        public PageBuilder pageSize(Integer pageSize) {
            this.data.setPageSize(pageSize);
            return this;
        }

        public PageBuilder pageNum(Integer pageNum) {
            this.data.setPageNum(pageNum);
            return this;
        }

        public PageBuilder page(Integer pageNum, Integer pageSize) {
            this.data.setPageNum(pageNum);
            this.data.setPageSize(pageSize);
            return this;
        }

        public PageBuilder total(Long total) {
            this.data.setTotal(total);
            return this;
        }

        public <R extends PageResponseEntity<T>, T> R data(T... data) {
            return (R) data(Arrays.asList(data));
        }

        public <R extends PageResponseEntity<T>, T> R data(Collection<T> data) {
            this.data.setData((Collection<Object>) data);
            return (R) new PageResponseEntity<>(this.data, this.headers, this.statusCode);
        }

        public <R extends PageResponseEntity<T>, T> R body(PageEntity<Collection<T>> body) {
            return (R) new PageResponseEntity<>(body, this.headers, this.statusCode);
        }

        public <R extends PageResponseEntity<T>, T> R body(@Nullable Collection<T> body) {
            return (R) data(body);
        }

        @Override
        public <R extends HttpEntity<T>, T> R body(T body) {
            return (R) body(new PageEntity<>(List.of(body)));
        }


        @Override
        public <R extends PageResponseEntity<T>, T> R body() {
            return (R) new PageResponseEntity<>(new PageEntity<>(), this.headers, this.statusCode);
        }

        @Override
        public <T, R extends HttpEntity<T>> R build() {
            return (R) body();
        }
    }
}
