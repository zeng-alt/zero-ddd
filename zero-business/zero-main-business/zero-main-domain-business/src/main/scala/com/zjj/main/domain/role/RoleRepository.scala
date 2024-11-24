package com.zjj.main.domain.role

import java.lang

/**
 * @author zengJiaJun
 * @crateTime 2024年11月21日 21:12
 * @version 1.0
 */
trait RoleRepository {

  def findById(id: lang.Long): io.vavr.control.Option[RoleAgg]
  def findByRoleKey(id: String): io.vavr.control.Option[RoleAgg]

  def save(roleAgg: RoleAgg): Unit
}
