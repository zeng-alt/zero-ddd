package com.zjj.autoconfigure.component.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 20:19
 * @version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	public static final Integer SUCCESS = 200;

	public static final Integer WARN = 601;

	public static final Integer FAIL = 500;

	private Integer code;
	private String message;
	private List<Object> error;
	protected T data;
	private LocalDateTime time = LocalDateTime.now();

	protected Response() {

	}

	protected Response(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	protected Response<T> setError(List<Object> error) {
		this.error = error;
		return this;
	}

	protected Response<T> setData(T data) {
		this.data = data;
		return this;
	}

	protected Response<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	protected Response<T> setCode(Integer code) {
		this.code = code;
		return this;
	}

	public static <T extends Serializable> Response<T> apply(ResponseEnum responseEnum) {
		return new Response<T>().setCode(responseEnum.getCode()).setMessage(responseEnum.getMessage());
	}

	public static <T extends Serializable> Response<T> success() {
		return new Response<T>().setCode(SUCCESS).setMessage("success");
	}

	public static <T extends Serializable> Response<T> success(T data) {
		return new Response<T>().setCode(SUCCESS).setMessage("success").setData(data);
	}

	public static <T> Response<T> fail() {
		return new Response<T>().setCode(FAIL).setMessage("fail").setError(new ArrayList<>());
	}

	public static <T> Response<T> fail(String message) {
		return new Response<T>().setCode(FAIL).setMessage(message).setData(null).setError(new ArrayList<>());
	}

	public static <T> Response<T> warn() {
		return new Response<T>().setCode(WARN).setMessage("warn").setData(null).setError(new ArrayList<>());
	}

	public static <T> Response<T> warn(String message) {
		return new Response<T>().setCode(WARN).setMessage(message).setData(null);
	}

	public static <T> Response<T> warn(T data) {
		return new Response<T>().setCode(WARN).setMessage("warn").setData(data);
	}

	public Response<T> addError(Object error) {
		this.error.add(error);
		return this;
	}

	public Response<T> message(String message) {
		return new Response<T>().setCode(code).setMessage(message).setData(data);
	}

	public Response<T> code(int code) {
		return new Response<T>().setCode(code).setMessage(message).setData(data);
	}

	public Response<T> data(T data) {
		return new Response<T>().setCode(code).setMessage(message).setData(data);
	}

	public boolean isSuccess() {
		return Objects.equals(Response.SUCCESS, code);
	}

}
