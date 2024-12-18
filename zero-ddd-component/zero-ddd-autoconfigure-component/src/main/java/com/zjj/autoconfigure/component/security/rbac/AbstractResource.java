package com.zjj.autoconfigure.component.security.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;

import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:52
 */
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AbstractResource implements Resource {

    protected String uri;
    protected String method;

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    @JsonIgnore
    public HttpMethod getHttpMethod() {
        return HttpMethod.valueOf(method);
    }

    @Override
    public String getMethod() {
        return method;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractResource that = (AbstractResource) o;

        if (!Objects.equals(uri, that.uri)) return false;
        return Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }
}
