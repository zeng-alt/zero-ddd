package com.zjj.main.domain.role

import com.zjj.domain.component.CommandHandler
import org.springframework.stereotype.Service

/**
 * @author zengJiaJun
 * @crateTime 2024年11月21日 09:39
 * @version 1.0
 */
class RoleHandler {

}

@Service
class StockInRoleCmdHandler(roleRepository: RoleRepository, roleFactory: RoleFactory) extends CommandHandler[StockInRoleCmd] {

  override def handler(cmd: StockInRoleCmd): Unit = {
    roleFactory.create(cmd).foreach(roleRepository.save)
  }
}
