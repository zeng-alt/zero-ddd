package com.zjj.main.domain.user

import com.zjj.domain.component.CommandHandler
import com.zjj.i18n.component.BaseI18nException
import org.springframework.stereotype.Service

/**
 * @author zengJiaJun
 * @crateTime 2024年11月18日 21:56
 * @version 1.0
 */
@Service
class StockInUserCmdHandler(userRepository: UserRepository, userFactory: UserFactory) extends CommandHandler[StockInUserCmd] {

  override def handler(cmd: StockInUserCmd): Unit = {
    userFactory.create(cmd).foreach(userRepository.save)

//    val user = BeanHelper.copyToObject(cmd, UserAgg.getClass)

//    val agg = UserAgg.apply(null, cmd.username, cmd.nickName, cmd.email, cmd.phoneNumber, cmd.gender, cmd.avatar, cmd.password, cmd.status, cmd.deleted)
  }
}

@Service
class AssignRoleCmdHandler(userRepository: UserRepository) extends CommandHandler[AssignRoleCmd] {

  override def handler(cmd: AssignRoleCmd): Unit = {

    if (!userRepository.existsByRoles(cmd.getRoleIds())) {
      throw new BaseI18nException("user.role.not.exists")
    }
    userRepository
      .findById(cmd.userId)
      .map(u => u.assignRoles(cmd.roleIds));

  }
}
