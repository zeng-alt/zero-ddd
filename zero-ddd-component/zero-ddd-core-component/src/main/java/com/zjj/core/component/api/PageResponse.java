package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月26日 19:30
 */
@Getter
@Setter
public class PageResponse<T extends Serializable> extends Response<Collection<T>> {

	private Integer pageNum;

	private Integer pageSize;

	private Long total;

	public Integer getFirstNum() {
		return (pageNum - 1) * pageSize;
	}

	public long getTotalPages() {
		return this.total % this.pageSize == 0 ? this.total / this.pageSize : (this.total / this.pageSize) + 1;
	}

	@Override
	public List<T> getData() {
		if (null == data) {
			return Collections.emptyList();
		}
		if (data instanceof List) {
			return (List<T>) data;
		}
		return new ArrayList<>(data);
	}

	public static <T extends Serializable> PageResponse<T> of(int pageSize, int pageNum) {
		PageResponse<T> response = new PageResponse<>();
		response.setData(Collections.emptyList());
		response.setTotal(0L);
		response.setPageSize(pageSize);
		response.setPageNum(pageNum);
		response.setCode(SUCCESS);
		return response;
	}

	public static <T extends Serializable> PageResponse<T> of(Collection<T> data, long totalCount, int pageSize,
			int pageNum) {
		PageResponse<T> response = new PageResponse<>();
		response.setData(data);
		response.setTotal(totalCount);
		response.setPageSize(pageSize);
		response.setPageNum(pageNum);
		response.setCode(SUCCESS);
		return response;
	}

}
