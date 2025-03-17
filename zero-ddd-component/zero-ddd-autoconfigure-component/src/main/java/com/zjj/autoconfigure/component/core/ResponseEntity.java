package com.zjj.autoconfigure.component.core;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月25日 14:50
 */
public class ResponseEntity<T> extends HttpEntityStatus<T> {


    /**
     * Create a {@code ResponseEntity} with a status code only.
     * @param status the status code
     */
    public ResponseEntity(HttpStatusCode status) {
        this(null, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with a body and status code.
     * @param body the entity body
     * @param status the status code
     */
    public ResponseEntity(@Nullable T body, HttpStatusCode status) {
        this(body, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with headers and a status code.
     * @param headers the entity headers
     * @param status the status code
     */
    public ResponseEntity(MultiValueMap<String, String> headers, HttpStatusCode status) {
        this(null, headers, status);
    }

    /**
     * Create a {@code ResponseEntity} with a status code only.
     * @param status the status code
     */
    public ResponseEntity(Integer status) {
        this(null, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with a body and status code.
     * @param body the entity body
     * @param status the status code
     */
    public ResponseEntity(@Nullable T body, Integer status) {
        this(body, null, status);
    }

    /**
     * Create a {@code ResponseEntity} with headers and a status code.
     * @param headers the entity headers
     * @param status the status code
     */
    public ResponseEntity(MultiValueMap<String, String> headers, Integer status) {
        this(null, headers, status);
    }

    /**
     * Create a {@code ResponseEntity} with a body, headers, and a raw status code.
     * @param body the entity body
     * @param headers the entity headers
     * @param rawStatus the status code value
     * @since 5.3.2
     */
    public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, int rawStatus) {
        this(body, headers, HttpStatusCode.valueOf(rawStatus));
    }

    /**
     * Create a {@code ResponseEntity} with a body, headers, and a status code.
     * @param body the entity body
     * @param headers the entity headers
     * @param statusCode the status code
     */
    public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatusCode statusCode) {
        super(body, headers, statusCode.value());
        Assert.notNull(statusCode, "HttpStatusCode must not be null");
    }


    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        return (other instanceof ResponseEntity<?> otherEntity && ObjectUtils.nullSafeEquals(this.status, otherEntity.status));
    }

    @Override
    public int hashCode() {
        return (29 * super.hashCode() + ObjectUtils.nullSafeHashCode(this.status));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(this.status);
        builder.append(',');
        T body = getBody();
        HttpHeaders headers = getHeaders();
        if (body != null) {
            builder.append(body);
            builder.append(',');
        }
        builder.append(headers);
        builder.append('>');
        return builder.toString();
    }


    // Static builder methods

    /**
     * Create a builder with the given status.
     * @param status the response status
     * @return the created builder
     * @since 4.1
     */
    public static BodyBuilder status(HttpStatusCode status) {
        Assert.notNull(status, "HttpStatusCode must not be null");
        return new DefaultBuilder(status);
    }

    /**
     * Create a builder with the given status.
     * @param status the response status
     * @return the created builder
     * @since 4.1
     */
    public static BodyBuilder status(Integer status) {
        Assert.notNull(status, "status must not be null");
        return new DefaultBuilder(status);
    }

    /**
     * Create a builder with the status set to {@linkplain HttpStatus#OK OK}.
     * @return the created builder
     * @since 4.1
     */
    public static BodyBuilder ok() {
        return status(HttpStatus.OK);
    }

    /**
     * A shortcut for creating a {@code ResponseEntity} with the given body
     * and the status set to {@linkplain HttpStatus#OK OK}.
     * @param body the body of the response entity (possibly empty)
     * @return the created {@code ResponseEntity}
     * @since 4.1
     */
    public static <T> ResponseEntity<T> ok(@Nullable T body) {
        return (ResponseEntity<T>) ok().body(body);
    }

    /**
     * A shortcut for creating a {@code ResponseEntity} with the given body
     * and the {@linkplain HttpStatus#OK OK} status, or an empty body and a
     * {@linkplain HttpStatus#NOT_FOUND NOT FOUND} status in case of an
     * {@linkplain Optional#empty()} parameter.
     * @return the created {@code ResponseEntity}
     * @since 5.1
     */
    public static <T> ResponseEntity<T> of(Optional<T> body) {
        Assert.notNull(body, "Body must not be null");
        return body.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
    }

    public static <T> ResponseEntity<T> of(T body) {
        return ok(body);
    }

    /**
     * Create a new {@link ResponseEntity.HeadersBuilder} with its status set to
     * {@link ProblemDetail#getStatus()} and its body is set to
     * {@link ProblemDetail}.
     * <p><strong>Note:</strong> If there are no headers to add, there is usually
     * no need to create a {@link ResponseEntity} since {@code ProblemDetail}
     * is also supported as a return value from controller methods.
     * @param body the problem detail to use
     * @return the created builder
     * @since 6.0
     */
    public static ResponseEntity.HeadersBuilder<?> of(ProblemDetail body) {
        return new DefaultBuilder(body.getStatus()) {
            @SuppressWarnings("unchecked")
            @Override
            public ResponseEntity<ProblemDetail> build() {
                return (ResponseEntity<ProblemDetail>) body(body);
            }

        };

    }

    /**
     * A shortcut for creating a {@code ResponseEntity} with the given body
     * and the {@linkplain HttpStatus#OK OK} status, or an empty body and a
     * {@linkplain HttpStatus#NOT_FOUND NOT FOUND} status in case of a
     * {@code null} parameter.
     * @return the created {@code ResponseEntity}
     * @since 6.0.5
     */
    public static <T> ResponseEntity<T> ofNullable(@Nullable T body) {
        if (body == null) {
            return notFound().build();
        }
        return ResponseEntity.ok(body);
    }

    /**
     * Create a new builder with a {@linkplain HttpStatus#CREATED CREATED} status
     * and a location header set to the given URI.
     * @param location the location URI
     * @return the created builder
     * @since 4.1
     */
    public static HeadersBuilder created(URI location) {
        return status(HttpStatus.CREATED).location(location);
    }

    /**
     * Create a builder with an {@linkplain HttpStatus#ACCEPTED ACCEPTED} status.
     * @return the created builder
     * @since 4.1
     */
    public static ResponseEntity.BodyBuilder accepted() {
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
    public static ResponseEntity.BodyBuilder badRequest() {
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
    public static ResponseEntity.BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Create a builder with an
     * {@linkplain HttpStatus#INTERNAL_SERVER_ERROR INTERNAL_SERVER_ERROR} status.
     * @return the created builder
     * @since 5.3.8
     */
    public static ResponseEntity.BodyBuilder internalServerError() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity.BodyBuilder fail() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Defines a builder that adds headers to the response entity.
     * @since 4.1
     * @param <B> the builder subclass
     */
    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        /**
         * Add the given, single header value under the given name.
         * @param headerName the header name
         * @param headerValues the header value(s)
         * @return this builder
         * @see HttpHeaders#add(String, String)
         */
        B header(String headerName, String... headerValues);

        /**
         * Copy the given headers into the entity's headers map.
         * @param headers the existing HttpHeaders to copy from
         * @return this builder
         * @since 4.1.2
         * @see HttpHeaders#add(String, String)
         */
        B headers(@Nullable HttpHeaders headers);

        /**
         * Manipulate this entity's headers with the given consumer. The
         * headers provided to the consumer are "live", so that the consumer can be used to
         * {@linkplain HttpHeaders#set(String, String) overwrite} existing header values,
         * {@linkplain HttpHeaders#remove(Object) remove} values, or use any of the other
         * {@link HttpHeaders} methods.
         * @param headersConsumer a function that consumes the {@code HttpHeaders}
         * @return this builder
         * @since 5.2
         */
        B headers(Consumer<HttpHeaders> headersConsumer);

        /**
         * Set the set of allowed {@link HttpMethod HTTP methods}, as specified
         * by the {@code Allow} header.
         * @param allowedMethods the allowed methods
         * @return this builder
         * @see HttpHeaders#setAllow(Set)
         */
        B allow(HttpMethod... allowedMethods);

        /**
         * Set the entity tag of the body, as specified by the {@code ETag} header.
         * @param etag the new entity tag
         * @return this builder
         * @see HttpHeaders#setETag(String)
         */
        B eTag(@Nullable String etag);

        /**
         * Set the time the resource was last changed, as specified by the
         * {@code Last-Modified} header.
         * @param lastModified the last modified date
         * @return this builder
         * @since 5.1.4
         * @see HttpHeaders#setLastModified(ZonedDateTime)
         */
        B lastModified(ZonedDateTime lastModified);

        /**
         * Set the time the resource was last changed, as specified by the
         * {@code Last-Modified} header.
         * @param lastModified the last modified date
         * @return this builder
         * @since 5.1.4
         * @see HttpHeaders#setLastModified(Instant)
         */
        B lastModified(Instant lastModified);

        /**
         * Set the time the resource was last changed, as specified by the
         * {@code Last-Modified} header.
         * <p>The date should be specified as the number of milliseconds since
         * January 1, 1970 GMT.
         * @param lastModified the last modified date
         * @return this builder
         * @see HttpHeaders#setLastModified(long)
         */
        B lastModified(long lastModified);

        /**
         * Set the location of a resource, as specified by the {@code Location} header.
         * @param location the location
         * @return this builder
         * @see HttpHeaders#setLocation(URI)
         */
        B location(URI location);

        /**
         * Set the caching directives for the resource, as specified by the HTTP 1.1
         * {@code Cache-Control} header.
         * <p>A {@code CacheControl} instance can be built like
         * {@code CacheControl.maxAge(3600).cachePublic().noTransform()}.
         * @param cacheControl a builder for cache-related HTTP response headers
         * @return this builder
         * @since 4.2
         * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.2">RFC-7234 Section 5.2</a>
         */
        B cacheControl(CacheControl cacheControl);

        /**
         * Configure one or more request header names (for example, "Accept-Language") to
         * add to the "Vary" response header to inform clients that the response is
         * subject to content negotiation and variances based on the value of the
         * given request headers. The configured request header names are added only
         * if not already present in the response "Vary" header.
         * @param requestHeaders request header names
         * @since 4.3
         */
        B varyBy(String... requestHeaders);

        /**
         * Build the response entity with no body.
         * @return the response entity
         * @see ResponseEntity.BodyBuilder#body(Object)
         */
        <T, R extends HttpEntity<T>> R build();
    }


    /**
     * Defines a builder that adds a body to the response entity.
     * @since 4.1
     */
    public interface BodyBuilder<B extends BodyBuilder<B>> extends HeadersBuilder<B> {

        /**
         * Set the length of the body in bytes, as specified by the
         * {@code Content-Length} header.
         * @param contentLength the content length
         * @return this builder
         * @see HttpHeaders#setContentLength(long)
         */
        <R extends BodyBuilder> R contentLength(long contentLength);

        /**
         * Set the {@linkplain MediaType media type} of the body, as specified by the
         * {@code Content-Type} header.
         * @param contentType the content type
         * @return this builder
         * @see HttpHeaders#setContentType(MediaType)
         */
        <R extends BodyBuilder> R contentType(MediaType contentType);

        /**
         * Set the body of the response entity and returns it.
         * @param <T> the type of the body
         * @param body the body of the response entity
         * @return the built response entity
         */
        <R extends HttpEntity<T>, T> R body(@Nullable T body);
    }


    public static class DefaultBuilder<B extends BodyBuilder<B>> implements BodyBuilder<B> {

        protected final Integer statusCode;

        protected final HttpHeaders headers = new HttpHeaders();


        public DefaultBuilder(int statusCode) {
            this(HttpStatusCode.valueOf(statusCode));
        }

        public DefaultBuilder(HttpStatusCode statusCode) {
            this.statusCode = statusCode.value();
        }


        @Override
        public B header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return (B) this;
        }

        @Override
        public B headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return (B) this;
        }

        @Override
        public B headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return (B) this;
        }

        @Override
        public B allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return (B) this;
        }

        @Override
        public B contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return (B) this;
        }

        @Override
        public B contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return (B) this;
        }

        @Override
        public B eTag(@Nullable String tag) {
            this.headers.setETag(tag);
            return (B) this;
        }

        @Override
        public B lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return (B) this;
        }

        @Override
        public B lastModified(Instant date) {
            this.headers.setLastModified(date);
            return (B) this;
        }

        @Override
        public B lastModified(long date) {
            this.headers.setLastModified(date);
            return (B) this;
        }

        @Override
        public B location(URI location) {
            this.headers.setLocation(location);
            return (B) this;
        }

        @Override
        public B cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return (B) this;
        }

        @Override
        public B varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return (B) this;
        }

        /**
         * Build the response entity with no body.
         *
         * @return the response entity
         * @see BodyBuilder#body(Object)
         */
        @Override
        public <T, R extends HttpEntity<T>> R build() {
            return body(null);
        }

        /**
         * Set the body of the response entity and returns it.
         *
         * @param body the body of the response entity
         * @return the built response entity
         */
        @Override
        public <R extends HttpEntity<T>, T> R body(T body) {
            return (R) new ResponseEntity<>(body, this.headers, this.statusCode);
        }
    }
}
