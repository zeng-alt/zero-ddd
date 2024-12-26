package com.zjj.main.domain.user

import com.zjj.domain.component.Command


/**
 * @author zengJiaJun
 * @crateTime 2024年11月18日 21:07
 * @version 1.0
 */
case class StockInUserCmd(id: java.lang.Long, username: String, nickName: String, email: String, phoneNumber: String, gender: String, avatar: String, password: String, status: String, deleted: Integer) extends Command

case class AssignRoleCmd(userId: java.lang.Long, roleIds: List[String]) extends Command {
  def getRoleIds(): java.util.List[String] = {
    import scala.jdk.CollectionConverters._
    roleIds.asJava
  }
}


