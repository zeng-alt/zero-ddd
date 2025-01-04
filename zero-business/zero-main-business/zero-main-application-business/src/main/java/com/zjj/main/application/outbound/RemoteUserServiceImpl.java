package com.zjj.main.application.outbound;

import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.exchange.main.api.RemoteUserApi;
import com.zjj.main.domain.user.UserQueryModel;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 14:58
 */
@Service
@Slf4j
@ApplicationLayer
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemoteUserServiceImpl implements RemoteUserApi {


    private final UserQueryModel userQueryModel;

    @Override
    public Option<SecurityUser> findByUsername(String username) {

        return userQueryModel
                .findByUsername(username)
                .map(user ->
                     SecurityUser
                            .withUsername(user.getUsername())
                            .roles(user.getRoleIds())
                            .currentRole(CollectionUtils.isEmpty(user.getRoleIds()) ? null : user.getRoleIds().iterator().next())
                            .password(user.getPassword())
                            .tenant(user.getTenantBy())
                            .disabled(!"0".equals(user.status()))
                            .build()
                )
                .peek(securityUser -> log.info("findByUsername: {}", securityUser));
    }
}
