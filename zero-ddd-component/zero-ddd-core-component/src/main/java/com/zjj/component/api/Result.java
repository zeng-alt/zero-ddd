package com.zjj.component.api;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 20:19
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class Result<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Integer SUCCESS = 200;
    private static final Integer WARN    = 601;
    private static final Integer FAIL    = 500;

    private Integer code;
    private String message;
    private T data;

    private Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    private Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    private Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public static <T extends Serializable> Result<T> success() {
        return new Result<T>().setCode(SUCCESS).setMessage("success");
    }

    public static <T extends Serializable> Result<T> success(T data) {
        return new Result<T>().setCode(SUCCESS).setMessage("success").setData(data);
    }

    public static <T extends Serializable> Result<T> fail() {
        return new Result<T>().setCode(FAIL).setMessage("fail");
    }

    public static <T extends Serializable> Result<T> fail(String message) {
        return new Result<T>().setCode(FAIL).setMessage(message).setData(null);
    }

    public static <T extends Serializable> Result<T> warn() {
        return new Result<T>().setCode(WARN).setMessage("warn").setData(null);
    }

    public static <T extends Serializable> Result<T> warn(String message) {
        return new Result<T>().setCode(WARN).setMessage(message).setData(null);
    }

    public static <T extends Serializable> Result<T> warn(T data) {
        return new Result<T>().setCode(WARN).setMessage("warn").setData(data);
    }

    public Result<T> message(String message) {
        return new Result<T>().setCode(code).setMessage(message).setData(data);
    }

    public Result<T> code(int code) {
        return new Result<T>().setCode(code).setMessage(message).setData(data);
    }

    public Result<T> data(T data) {
        return new Result<T>().setCode(code).setMessage(message).setData(data);
    }

    public boolean isError() {
        return Objects.equals(Result.FAIL, code);
    }

    public boolean isSuccess() {
        return Objects.equals(Result.SUCCESS, code);
    }
}
