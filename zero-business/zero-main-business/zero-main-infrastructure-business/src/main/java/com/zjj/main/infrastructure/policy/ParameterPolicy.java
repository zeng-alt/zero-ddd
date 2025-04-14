package com.zjj.main.infrastructure.policy;

import com.zjj.core.component.parameter.Parameter;
import com.zjj.core.component.parameter.ParameterService;
import com.zjj.main.domain.parameter.event.InitParameterEvent;
import com.zjj.main.domain.role.event.InitRbacEvent;
import com.zjj.main.infrastructure.db.jpa.dao.ParameterEntityDao;
import com.zjj.main.infrastructure.db.jpa.entity.ParameterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:51
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParameterPolicy {

    private final ParameterEntityDao parameterDao;
    private final ParameterService parameterService;

    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void on(InitParameterEvent event) {
        List<ParameterEntity> list = parameterDao.findAll();
        List<Parameter> result = list.stream().map(p -> {
            Parameter parameter = new Parameter();
            BeanUtils.copyProperties(p, parameter);
            return parameter;
        }).toList();
        parameterService.batchPutParameter(result);
        log.info("初始化参数完成");
    }
}
