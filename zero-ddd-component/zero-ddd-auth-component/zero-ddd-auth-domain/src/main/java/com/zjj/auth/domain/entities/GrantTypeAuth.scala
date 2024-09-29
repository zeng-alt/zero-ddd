package com.zjj.auth.domain.entities

/**
 * @author zengJiaJun
 * @crateTime 2024年06月17日 19:51
 * @version 1.0
 */
abstract class GrantTypeAuth {
  def check(map: java.util.Map[String, String]): Boolean
}
case class PasswordTypeAuth(username: String, password: String) extends GrantTypeAuth {


  def check(map: java.util.Map[String, String]): Boolean = {
    username.nonEmpty && password.nonEmpty
  }

}

case class EmailTypeAuth(email: String, password: String) extends GrantTypeAuth {

    def check(map: java.util.Map[String, String]): Boolean = {
      email.nonEmpty && password.nonEmpty
    }
}

case class SmsTypeAuth(phone: String, code: String) extends GrantTypeAuth {
  override def check(map: java.util.Map[String, String]): Boolean = ???
}

case class SocialTypeAuth(openId: String, provider: String) extends GrantTypeAuth {
  override def check(map: java.util.Map[String, String]): Boolean = ???
}

case class XcxTypeAuth(code: String) extends GrantTypeAuth {
  override def check(map: java.util.Map[String, String]): Boolean = ???
}