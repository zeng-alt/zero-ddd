package com.zjj.gateway.repository;

import com.zjj.gateway.entity.Countries;
import com.zjj.graphql.component.supper.BaseRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 20:11
 */
@GraphQlRepository
public interface CountriesRepository extends BaseRepository<Countries, Long> {
}
