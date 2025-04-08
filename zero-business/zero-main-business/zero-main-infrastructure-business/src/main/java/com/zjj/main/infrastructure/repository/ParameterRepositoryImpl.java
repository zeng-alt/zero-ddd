package com.zjj.main.infrastructure.repository;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.parameter.ParameterAgg;
import com.zjj.main.domain.parameter.ParameterRepository;
import com.zjj.main.infrastructure.db.jpa.dao.ParameterEntityDao;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 14:27
 */
@Component
@RequiredArgsConstructor
public class ParameterRepositoryImpl implements ParameterRepository {

    private final ParameterEntityDao dao;

    @Override
    public Option<ParameterAgg> findByParameterKey(String parameterKey) {
        return dao
                .findByParameterKey(parameterKey)
                .map(p -> BeanHelper.copyToObject(p, ParameterAgg.class));
    }
}
