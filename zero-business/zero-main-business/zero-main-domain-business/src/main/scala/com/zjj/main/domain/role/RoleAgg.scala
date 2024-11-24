package com.zjj.main.domain.role

import com.zjj.domain.component.Aggregate

import scala.beans.BeanProperty
import java.lang.Long

/**
 * @author zengJiaJun
 * @crateTime 2024年11月19日 21:26
 * @version 1.0
 */
class RoleAgg extends Aggregate[Long] {
  @BeanProperty var id: Long = _

  /**
   * 角色名称
   */
  @BeanProperty var roleName: String = _

  /**
   * 角色权限
   */
  @BeanProperty var roleKey: String = _

  /**
   * 角色排序
   */
  @BeanProperty var roleSort: Integer = _


  /**
   * 角色状态（0正常 1停用）
   */
  @BeanProperty var status: String = _

  /**
   * 删除标志（0代表存在 2代表删除）
   */
  @BeanProperty var deleted: Integer = _

}


