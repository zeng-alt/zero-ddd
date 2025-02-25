package com.zjj.core.component.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;

import java.net.URI;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 15:32
 */
public class ErrorResponseEntity implements ErrorResponse {

    private final HttpStatusCode status;

    private final HttpHeaders headers = new HttpHeaders();

    private final ProblemDetail body;

    private final String messageDetailCode;

    @Nullable
    private final Object[] messageDetailArguments;

    /**
     * Constructor with an {@link HttpStatusCode}.
     */
    public ErrorResponseEntity(HttpStatusCode status) {
        this(status, null);
    }

    /**
     * Constructor with an {@link HttpStatusCode} and an optional cause.
     */
    public ErrorResponseEntity(HttpStatusCode status, @Nullable Throwable cause) {
        this(status, ProblemDetail.forStatus(status), cause);
    }

    /**
     * Constructor with a given {@link ProblemDetail} instance, possibly a
     * subclass of {@code ProblemDetail} with extended fields.
     */
    public ErrorResponseEntity(HttpStatusCode status, ProblemDetail body, @Nullable Throwable cause) {
        this(status, body, cause, null, null);
    }

    /**
     * Constructor with a given {@link ProblemDetail}, and a
     * {@link org.springframework.context.MessageSource} code and arguments to
     * resolve the detail message with.
     * @since 6.0
     */
    public ErrorResponseEntity(
            HttpStatusCode status, ProblemDetail body, @Nullable Throwable cause,
            @Nullable String messageDetailCode, @Nullable Object[] messageDetailArguments) {

        this.status = status;
        this.body = body;
        this.messageDetailCode = initMessageDetailCode(messageDetailCode);
        this.messageDetailArguments = messageDetailArguments;
    }

    public static ErrorResponseEntity of(HttpStatusCode status, String message) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(status, message);
        return new ErrorResponseEntity(status, body, null, null, null);
    }

    public static ErrorResponseEntity of(String message) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static ErrorResponseEntity of(HttpStatusCode status, String message, String detail) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(status, message);
        body.setDetail(detail);
        return new ErrorResponseEntity(status, body, null, null, null);
    }

    public static ErrorResponseEntity of(String message, String detail) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, message, detail);
    }

    private String initMessageDetailCode(@Nullable String messageDetailCode) {
        return (messageDetailCode != null ?
                messageDetailCode : ErrorResponse.getDefaultDetailMessageCode(getClass(), null));
    }


    @Override
    public HttpStatusCode getStatusCode() {
        return this.status;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    /**
     * Set the {@link ProblemDetail#setType(URI) type} field of the response body.
     * @param type the problem type
     */
    public ErrorResponseEntity setType(URI type) {
        this.body.setType(type);
        return this;
    }

    /**
     * Set the {@link ProblemDetail#setTitle(String) title} field of the response body.
     * @param title the problem title
     */
    public ErrorResponseEntity setTitle(@Nullable String title) {
        this.body.setTitle(title);
        return this;
    }

    /**
     * Set the {@link ProblemDetail#setDetail(String) detail} field of the response body.
     * @param detail the problem detail
     */
    public ErrorResponseEntity setDetail(@Nullable String detail) {
        this.body.setDetail(detail);
        return this;
    }

    /**
     * Set the {@link ProblemDetail#setInstance(URI) instance} field of the response body.
     * @param instance the problem instance
     */
    public ErrorResponseEntity setInstance(@Nullable URI instance) {
        this.body.setInstance(instance);
        return this;
    }

    /**
     * Return the body for the response. To customize the body content, use:
     * <ul>
     * <li>{@link #setType(URI)}
     * <li>{@link #setTitle(String)}
     * <li>{@link #setDetail(String)}
     * <li>{@link #setInstance(URI)}
     * </ul>
     * <p>By default, the status field of {@link ProblemDetail} is initialized
     * from the status provided to the constructor, which in turn may also
     * initialize the title field from the status reason phrase, if the status
     * is well-known. The instance field, if not set, is initialized from the
     * request path when a {@code ProblemDetail} is returned from an
     * {@code @ExceptionHandler} method.
     */
    @Override
    public final ProblemDetail getBody() {
        return this.body;
    }

    @Override
    public String getDetailMessageCode() {
        return this.messageDetailCode;
    }

    @Override
    @Nullable
    public Object[] getDetailMessageArguments() {
        return this.messageDetailArguments;
    }


    public String getMessage() {
        return this.status + (!this.headers.isEmpty() ? ", headers=" + this.headers : "") + ", " + this.body;
    }
}
