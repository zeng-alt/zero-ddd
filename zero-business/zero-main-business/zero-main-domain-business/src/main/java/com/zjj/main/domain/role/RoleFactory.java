package com.zjj.main.domain.role;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.i18n.component.BaseI18nException;
import com.zjj.main.domain.role.cmd.StockInRoleCmd;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:11
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleFactory {

    private final RoleRepository roleRepository;

    public Option<RoleAgg> create(StockInRoleCmd cmd) {

        RoleAgg roleAgg = null;

        if (cmd.id() != null) {
            roleAgg = roleRepository
                    .findById(cmd.id())
                    .peek(role -> {
                        if (StringUtils.hasText(cmd.code()) && !role.getCode().equals(cmd.code())) {
                            throw new BaseI18nException("roleKey.cannot.modified");
                        }
                    })
                    .map(role -> BeanHelper.copyToObject(role, RoleAgg.class))
                    .getOrElseThrow(() -> new BaseI18nException("role.not.exists", "role.not.exists", cmd.id()));
        }

        if (roleAgg == null && roleRepository.existsByCode(cmd.code())) {
            throw new BaseI18nException("roleKey.exists", "roleKey.exists", cmd.code());
        }

        return Option.of(roleAgg).orElse(Option.of(BeanHelper.copyToObject(cmd, RoleAgg.class)));
    }
}
