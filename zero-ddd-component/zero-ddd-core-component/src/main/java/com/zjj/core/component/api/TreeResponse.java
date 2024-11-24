package com.zjj.core.component.api;

import com.zjj.autoconfigure.component.core.Response;
import com.zjj.autoconfigure.component.core.ResponseEnum;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月23日 15:07
 */
public class TreeResponse<T extends Parent<P>, P extends Comparable<P>> extends Response<TreeResponse.TreeNodeResponse<T, P>> {


    protected TreeResponse(Collection<T> data) {
        super(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage());
        super.setData(parse(data));
    }


    public static <T extends Parent<P>, P extends Comparable<P>> TreeResponse<T, P> apply(Collection<T> data) {
        return new TreeResponse<>(data);
    }

    public TreeNodeResponse<T, P> parse(Collection<T> data) {
        if (data == null) {
            return null;
        }

        Optional<T> first = data.stream().filter(Parent::isRoot).findFirst();
        T rootData = first.orElseThrow(() -> new IllegalArgumentException("根节点不存在"));
        TreeNodeResponse<T, P> root = new TreeNodeResponse<>(rootData);
        parse(root, data.stream().filter(p -> !p.isRoot()).toList());
        return root;
    }

    public void parse(TreeNodeResponse<T, P> parent, Collection<T> data) {

        List<T> next = data.stream().filter(p -> !p.parent().equals(parent.getCurrentId())).toList();
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        List<TreeNodeResponse<T, P>> children = new ArrayList<>();
        data.stream().filter(p -> p.parent().equals(parent.getCurrentId())).forEach(t -> {
            TreeNodeResponse<T, P> treeNodeResponse = new TreeNodeResponse<>(t);
            parse(treeNodeResponse, next);
            children.add(treeNodeResponse);
        });
        parent.children = children;
    }

    public static <T extends Parent<P>, P extends Comparable<P>> TreeResponse<T, P> apply(List<T> data) {
        return new TreeResponse<>(data);
    }


    @Getter
    public static class TreeNodeResponse<T extends Parent<P>, P extends Comparable<P>> {
        private final T current;
        private List<TreeNodeResponse<T, P>> children;

        private TreeNodeResponse(T current) {
            this.current = current;
        }

        public P getCurrentId() {
            return current.current();
        }

        public boolean hasNext() {
            return !CollectionUtils.isEmpty(children);
        }
    }

}
