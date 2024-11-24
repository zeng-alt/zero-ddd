package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.Response;
import com.zjj.autoconfigure.component.core.ResponseEnum;

import java.util.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月22日 21:41
 */
public class TreeTableResponse<T extends Parent<P>, P extends Comparable<P>> extends Response<Collection<T>> {

    protected TreeTableResponse(Collection<T> data) {
        super(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage());
        setData(data);
    }

    public static <T extends Parent<P>, P extends Comparable<P>> TreeTableResponse<T, P> apply(Collection<T> data) {
        return new TreeTableResponse<>(data);
    }


    @Override
    protected Response<Collection<T>> setData(Collection<T> data) {
        List<T> result = new LinkedList<>();
        if (data != null) {

            Optional<T> first = data.stream().filter(Parent::isRoot).findFirst();
            T root = first.orElseThrow(() -> new IllegalArgumentException("根节点不存在"));
            result.add(root);

        }
        return super.setData(result);
    }

}
