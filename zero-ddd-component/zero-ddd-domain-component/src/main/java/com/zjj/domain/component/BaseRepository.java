package com.zjj.domain.component;

import io.vavr.control.Option;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年08月26日 20:16
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID>, QuerydslPredicateExecutor<T>, QueryByExampleExecutor<T> {

    <S extends T> S save(S entity);

    Option<T> findById(ID id);

    void deleteById(ID id);

    void delete(T entities);

    void deleteAllById(Iterable<? extends ID> ids);
}
