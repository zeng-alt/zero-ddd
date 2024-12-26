package com.zjj.main.domain.role

import com.zjj.bean.componenet.BeanHelper
import com.zjj.i18n.component.BaseI18nException
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

/**
 * @author zengJiaJun
 * @crateTime 2024年11月21日 21:12
 * @version 1.0
 */
@Component
class RoleFactory(roleRepository: RoleRepository) {

  def create(cmd: StockInRoleCmd): Option[RoleAgg] = {
    if (cmd.id != null) {
      return roleRepository
        .findById(cmd.id)
        .peek(role => Option.when(StringUtils.hasText(cmd.roleKey) && !role.roleKey.equals(cmd.roleKey))(throw new BaseI18nException("roleKey.cannot.modified")))
        .peek(role => BeanUtils.copyProperties(cmd, role))
        .map(Option.apply(_))
        .getOrElseThrow(() => new BaseI18nException("role.not.exists", "role.not.exists", cmd.id))
    }
    roleRepository
      .findByRoleKey(cmd.roleKey)
      .peek(_ => throw new BaseI18nException("roleKey.exists", "roleKey.exists", cmd.roleKey))

    Option.apply(BeanHelper.copyToObject(cmd, classOf[RoleAgg]))
  }

}
