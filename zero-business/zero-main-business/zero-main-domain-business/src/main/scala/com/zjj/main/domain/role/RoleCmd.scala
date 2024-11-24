package com.zjj.main.domain.role

import com.zjj.domain.component.Command

/**
 * @author zengJiaJun
 * @crateTime 2024年11月21日 21:38
 * @version 1.0
 */
case class StockInRoleCmd(id: java.lang.Long, roleName: String, roleKey: String, roleSort: Integer, status: String, deleted: Integer) extends Command
