package com.zjj.camunda.infrastructure.identity;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年08月28日 21:28
 */
public interface GroupRepository extends CrudRepository<MainGroup, Long> {

    MainGroup findById(String groupId);

    List<MainGroup> queryById(String groupId);
}
