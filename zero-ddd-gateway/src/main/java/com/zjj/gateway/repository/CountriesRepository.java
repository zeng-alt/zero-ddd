package com.zjj.gateway.repository;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.StringPath;
import com.zjj.gateway.entity.Countries;
import com.zjj.gateway.entity.QCountries;
import com.zjj.gateway.entity.QUsers;
import com.zjj.gateway.entity.Users;
import com.zjj.graphql.component.supper.BaseRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.graphql.data.GraphQlRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 20:11
 */
@GraphQlRepository
public interface CountriesRepository extends BaseRepository<Countries, Long> {

    default void test() {

        QCountries countries = QCountries.countries;
        DateTimePath<LocalDateTime> birthday = countries.birthday;
//        birthday.
//        StringPath name = countries.name;
//        NumberPath<Long> id = countries.id;
//        SetPath<Users, QUsers> userses = countries.userses;
//        userses.i
//        id.like()
//        name.like()
//        QCountries
    }
}
