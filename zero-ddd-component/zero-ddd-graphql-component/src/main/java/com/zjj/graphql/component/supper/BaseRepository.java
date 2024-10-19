package com.zjj.graphql.component.supper;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年08月26日 20:16
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID>, QuerydslPredicateExecutor<T>, QueryByExampleExecutor<T> {

    T save(T entity);

    T getById(ID id);

    Optional<T> findById(ID id);

    void deleteById(ID id);

    List<T> findAll();

    void deleteAllById(Iterable<ID> ids);

    void saveAll(Iterable<T> objects);
//    void saveAll(List<T> objects);
}
