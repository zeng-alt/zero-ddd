package com.zjj.gateway.repository;

import com.zjj.gateway.entity.Countries;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 11:11
 */
@GraphQlRepository
public interface CountrieRepository extends Repository<Countries, Long>, QuerydslPredicateExecutor<Long> {

    Countries findCountriesById(Long id);
}
