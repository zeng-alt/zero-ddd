package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.UserGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupUserDao extends BaseRepository<UserGroupUser, Long> {
}