package com.zjj.main.domain.user

/**
 * @author zengJiaJun
 * @crateTime 2024年11月18日 21:06
 * @version 1.0
 */
trait UserRepository {

  def findById(id: Long): io.vavr.control.Option[UserAgg]
  def findByUserName(username: String): io.vavr.control.Option[UserAgg]
  def save(user: UserAgg): Unit
  def existsByRoles(roleIds: java.util.List[String]): Boolean
}
