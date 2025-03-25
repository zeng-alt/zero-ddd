package com.zjj.main.domain.user;

import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.i18n.component.BaseI18nException;
import com.zjj.main.domain.user.cmd.StockInUserCmd;
import com.zjj.main.domain.user.event.StockInUserEvent;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:30
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserFactory {

    private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserRepository userRepository;

    public Option<UserAgg> create(StockInUserCmd cmd) {
        if (cmd.id() != null) {
            return userRepository
                    .findById(cmd.id())
                    .peek(user -> BeanUtils.copyProperties(cmd, user))
                    .peek(user -> ApplicationContextHelper.publisher().publishEvent(BeanHelper.copyToObject(user, StockInUserEvent.class)))
                    .map(Option::of)
                    .getOrElseThrow(() -> new BaseI18nException("user.not.exists", cmd.id() + "的用户不存在", cmd.id()));
        }

        userRepository
                .findByUsername(cmd.username())
                .forEach(user -> {throw new BaseI18nException("user.name.exists", cmd.username() + "的用户已存在", cmd.username());});

        Option<UserAgg> peek = Option
                .of(BeanHelper.copyToObject(cmd, UserAgg.class))
                .peek(u -> u.setPassword(PASSWORD_ENCODER.encode(cmd.password())))
                .peek(u -> ApplicationContextHelper.publisher().publishEvent(BeanHelper.copyToObject(u, StockInUserEvent.class)));


        System.out.println();
        return peek;
    }
}
