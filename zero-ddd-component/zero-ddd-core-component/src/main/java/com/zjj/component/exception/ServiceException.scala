package com.zjj.component.exception

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 20:33
 * @version 1.0
 */
@SerialVersionUID(1L)
case class ServiceException(code: Int = 500, message: String = "") extends RuntimeException {
  def withMessage(newMessage: String): ServiceException = copy(message = newMessage)
  def withCode(newCode: Int): ServiceException = copy(code = newCode)
}
