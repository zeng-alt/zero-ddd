package com.zjj.auth.domain.valueobjects

import com.zjj.core.component.exception.AuthException
import org.apache.commons.lang3.StringUtils

/**
 * @author zengJiaJun
 * @crateTime 2024年06月17日 20:22
 * @version 1.0
 */
case class ClientEntity(clientId: String, clientKey: String, clientSecret: String,
               grantTypeList: List[String], grantType: String,
               deviceType: String, status: String) {

  def check(map: java.util.Map[String, String]): Unit = {
    if (!"0".equals(status)) {
      throw new AuthException("client status is not normal");
    }

    if (StringUtils.contains(grantType, map.get("grantType"))) {
      throw new AuthException("client does not support the current grant_type");
    }
  }
}
