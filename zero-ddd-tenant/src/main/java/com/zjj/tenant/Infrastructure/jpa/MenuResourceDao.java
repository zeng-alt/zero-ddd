package com.zjj.tenant.Infrastructure.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.tenant.Tenant;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:11
 */
@GraphQlRepository
public interface MenuResourceDao extends BaseRepository<MenuResource, Long> {
}
