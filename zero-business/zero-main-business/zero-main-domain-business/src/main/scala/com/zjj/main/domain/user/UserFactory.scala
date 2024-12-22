package com.zjj.main.domain.user

import com.zjj.bean.componenet.BeanHelper
import com.zjj.i18n.component.BaseI18nException
import org.springframework.beans.BeanUtils
import org.springframework.security.crypto.password.PasswordEncoder
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
                .getOrElseThrow(() => new BaseI18nException("user.not.exists", cmd.id))
    }


    userRepository
      .findByUserName(cmd.username)
//      .map(user => user.setPassword(passwordEncoder.encode(cmd.password)))
      .peek(_ => throw new BaseI18nException("user.name.exists", cmd.username))

    val result = BeanHelper.copyToObject(cmd, classOf[UserAgg], new Consumer[UserAgg] {
      override def accept(t: UserAgg): Unit = {
//        t.setPassword(passwordEncoder.encode(cmd.password))
      }
    })
    Option.apply(result)
  }
}
