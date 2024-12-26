package com.zjj.main.domain.user

import org.jmolecules.architecture.cqrs.QueryModel
import org.springframework.stereotype.Service

/**
 * @author zengJiaJun
 * @crateTime 2024年12月25日 21:13
 * @version 1.0
 */
@Service
@QueryModel
class UserQueryModel(userRepository: UserRepository) {



  def findByUsername(username: String): io.vavr.control.Option[UserAgg] = {
    userRepository.findByUserName(username)
  }

}
