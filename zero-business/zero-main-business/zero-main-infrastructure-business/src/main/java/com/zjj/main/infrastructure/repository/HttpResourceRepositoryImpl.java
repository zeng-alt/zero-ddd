package com.zjj.main.infrastructure.repository;

import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.main.domain.resource.http.HttpResourceAgg;
import com.zjj.main.domain.resource.http.HttpResourceId;
import com.zjj.main.domain.resource.http.HttpResourceRepository;
import com.zjj.main.infrastructure.db.jpa.dao.HttpResourceDao;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:23
 */
@Component
@RequiredArgsConstructor
public class HttpResourceRepositoryImpl implements HttpResourceRepository {

    private final HttpResourceDao httpResourceDao;

    @Override
    public Option<HttpResourceAgg> findById(Long id) {
        return httpResourceDao
                .findById(id)
                .map(entity -> DomainBeanHelper.copyToDomain(entity, HttpResourceAgg.class,  HttpResourceId.class));
    }
}
