package com.zjj.main.domain.user

import com.zjj.bean.componenet.ApplicationContextHelper
import com.zjj.domain.component.Aggregate

import scala.beans.BeanProperty
import java.lang.Long

/**
 * @author zengJiaJun
 * @crateTime 2024年11月18日 21:11
 * @version 1.0
 */
class UserAgg extends Aggregate[Long] with Serializable {


  @BeanProperty var id: Long = _
  /**
   * 用户账号
   */
  @BeanProperty var username: String = _

  /**
   * 用户昵称
   */
  @BeanProperty var nickName: String = _


  /**
   * 用户邮箱
   */
  @BeanProperty var email: String = _

  /**
   * 手机号码
   */
  @BeanProperty var phoneNumber: String = _

  /**
   * 用户性别
   */
  @BeanProperty var gender: String = _

  /**
   * 用户头像
   */
  @BeanProperty var avatar: String = _

  /**
   * 密码
   */
  @BeanProperty var password: String = _

  /**
   * 帐号状态（0正常 1停用）
   */
  @BeanProperty var status: String = "0"

  /**
   * 删除标志（0代表存在 2代表删除）
   */
  @BeanProperty var deleted: Integer = 0

  @BeanProperty var tenantBy: String = _

  private var roleIds: Set[String] = _


  def setRoleIds(roleIds: java.util.Set[String]): Unit = {
    import scala.jdk.CollectionConverters._
    this.roleIds = roleIds.asScala.toSet
  }


  def getRoleIds(): java.util.Set[String] = {
    import scala.jdk.CollectionConverters._
    roleIds.asJava
  }

  def assignRoles(roleIds: List[String]): UserAgg = {
    this.roleIds = this.roleIds ++ roleIds
//    publishEvent(null)
    ApplicationContextHelper.publisher().publishEvent(null);
    this
  }

}


