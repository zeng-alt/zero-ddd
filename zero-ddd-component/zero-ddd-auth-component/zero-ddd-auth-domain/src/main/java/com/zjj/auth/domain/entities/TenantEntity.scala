package com.zjj.auth.domain.entities

import com.zjj.component.exception.AuthException

import java.time.LocalDate

/**
 * @author zengJiaJun
 * @crateTime 2024年06月17日 21:53
 * @version 1.0
 */
case class TenantEntity(tenantId: String, status: String, expireTime: LocalDate) {

  def check(): Unit = {
    if ("0000".equals(tenantId)) {
      return
    }
    if ("1".equals(status)) {
      throw new AuthException("tenant status is not normal")
    }

    if (expireTime.isBefore(LocalDate.now())) {
      throw new AuthException("tenant has expired")
    }

  }
}
