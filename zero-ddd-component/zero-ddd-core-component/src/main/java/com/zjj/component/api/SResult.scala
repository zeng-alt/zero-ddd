package com.zjj.component.api

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 20:19
 * @version 1.0
 */
@SerialVersionUID(1L)
case class SResult private(var code: Int = 0, var message: String = "", var data: Any = null)  extends Serializable {
  def withCode(newCode: Int): SResult = copy(code = newCode)
  def withMessage(newMessage: String): SResult = copy(message = newMessage)
  def withData(newData: Any): SResult = copy(data = Some(newData))
}

object SResult {

  val SUCCESS = 200
  val FAIL    = 500
  val WARN    = 601

  def success(data: Any): SResult =
    new SResult(SUCCESS, "success", Some(data))

  def success(): SResult =
    new SResult(SUCCESS, "success")

  def fail(message: String): SResult =
    new SResult(FAIL, message)

  def fail(): SResult =
    new SResult(FAIL, "fail")

  def warn(data: Any): SResult =
    new SResult(WARN, "warn", Some(data))

  def warn(message: String): SResult =
    new SResult(WARN, message)

  def warn(): SResult =
    new SResult(WARN, "warn")
}
