package com.zjj.auth.domain.service

import com.zjj.auth.domain.aggregates.AuthAggregates



/**
 * @author zengJiaJun
 * @crateTime 2024年06月17日 20:28
 * @version 1.0
 */
class AuthService {

  def login(body: String): Unit = {
    AuthAggregateFactor.create(body).auth(body);
  }

}

object AuthAggregateFactor {
  def create(body: String): AuthAggregates = {
    new AuthAggregates()
  }
}
