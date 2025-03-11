package com.zjj.excel.component.dynamic.constraints;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.lang.annotation.Annotation;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 17:14
 */
@Data
public class SizeImpl implements Size {

     private String message = "{jakarta.validation.constraints.Size.message}";
     private Integer min = 0;
     private Integer max = Integer.MAX_VALUE;

    @JsonCreator
     public SizeImpl(@JsonProperty("name") String message, @JsonProperty("min") String min, @JsonProperty("max") String max) {
         this.message = message;
         this.min = Integer.valueOf(min);
         this.max = Integer.valueOf(max);
     }

    @Override
    public String message() {
        return message;
    }

    @Override
    public Class<?>[] groups() {
        return new Class[0];
    }

    @Override
    public Class<? extends Payload>[] payload() {
        return new Class[0];
    }

    /**
     * @return size the element must be higher or equal to
     */
    @Override
    public int min() {
        return min;
    }

    /**
     * @return size the element must be lower or equal to
     */
    @Override
    public int max() {
        return max;
    }

    /**
     * Returns the annotation interface of this annotation.
     *
     * @return the annotation interface of this annotation
     * @apiNote Implementation-dependent classes are used to provide
     * the implementations of annotations. Therefore, calling {@link
     * Object#getClass getClass} on an annotation will return an
     * implementation-dependent class. In contrast, this method will
     * reliably return the annotation interface of the annotation.
     * @see Enum#getDeclaringClass
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return Size.class;
    }
}
