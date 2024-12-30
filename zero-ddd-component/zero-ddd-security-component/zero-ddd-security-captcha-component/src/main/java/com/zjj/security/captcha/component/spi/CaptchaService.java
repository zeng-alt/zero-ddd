package com.zjj.security.captcha.component.spi;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 16:05
 */
public interface CaptchaService {

    String getCaptcha(String captchaKey);

    public void putCaptcha(String captchaKey, String captcha);

    void removeCaptcha(String captchaKey);
}
