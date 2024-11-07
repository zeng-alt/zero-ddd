package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.Response;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月22日 21:41
 */
public class TreeTableResponse<T extends Parent<P>, P extends Comparable<P>> extends Response<Collection<T>> {

    protected TreeTableResponse() {}

    @Override
    protected Response<Collection<T>> setData(Collection<T> data) {

        if (data != null) {
            List<T> result = new LinkedList<>();
            Optional<T> first = data.stream().filter(Parent::isRoot).findFirst();
            T root = first.orElseThrow(() -> new IllegalArgumentException("根节点不存在"));
            result.add(root);

        }
        return super.setData(data);
    }


}
