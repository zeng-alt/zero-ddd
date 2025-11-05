package com.zjj.camunda.infrastructure.identity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年08月28日 21:28
 */
@Repository
public interface UserRepository extends CrudRepository<MainUser, Long> {

    MainUser findById(String userId);

    List<MainUser> queryById(String userId);
}
