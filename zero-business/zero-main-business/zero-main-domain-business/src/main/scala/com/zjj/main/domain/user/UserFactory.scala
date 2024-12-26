package com.zjj.main.domain.user

import com.google.common.collect.Lists
import com.zjj.bean.componenet.BeanHelper
import com.zjj.i18n.component.BaseI18nException
import com.zjj.main.domain.user.UserFactory.passwordEncoder
import org.springframework.beans.BeanUtils
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.{DelegatingPasswordEncoder, PasswordEncoder}
import org.springframework.stereotype.Component

import java.util.function.Consumer

/**
 * @author zengJiaJun
 * @crateTime 2024年11月20日 21:00
 * @version 1.0
 */
@Component
class UserFactory(userRepository: UserRepository) {
  def create(cmd: StockInUserCmd): Option[UserAgg] = {

    if (cmd.id != null) {
      return userRepository
                .findById(cmd.id)
                .peek(user => BeanUtils.copyProperties(cmd, user))
                .map(Option.apply(_))
                .getOrElseThrow(() => new BaseI18nException("user.not.exists", cmd.id + "的用户不存在", cmd.id))
    }


    userRepository
      .findByUserName(cmd.username)
//      .map(user => user.setPassword(passwordEncoder.encode(cmd.password)))
      .peek(_ => throw new BaseI18nException("user.name.exists", cmd.username + "的用户已存在", cmd.username))

    val result = BeanHelper.copyToObject(cmd, classOf[UserAgg])
    result.setPassword(passwordEncoder.encode(cmd.password))
    Option.apply(result)
  }
}


object UserFactory {
  val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
