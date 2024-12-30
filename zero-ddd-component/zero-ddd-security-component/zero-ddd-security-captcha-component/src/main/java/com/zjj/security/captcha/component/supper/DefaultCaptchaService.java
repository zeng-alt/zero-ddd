package com.zjj.security.captcha.component.supper;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.security.captcha.component.configuration.CaptchaProperties;
import com.zjj.security.captcha.component.spi.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月30日 21:41
 */
@RequiredArgsConstructor
public class DefaultCaptchaService implements CaptchaService {

    private static final String CAPTCHA_KEY = "captcha:";
    private final RedisStringRepository redisStringRepository;
    private final CaptchaProperties captchaProperties;

    private String getCaptchaKey(String captchaKey) {
        String tenantId = TenantContextHolder.getTenantId();
        if (StringUtils.hasText(tenantId)) {
            return CAPTCHA_KEY + tenantId + ":" + captchaKey;
        }

        return CAPTCHA_KEY + captchaKey;
    }

    @Override
    public String getCaptcha(String captchaKey) {
        String captcha = redisStringRepository.get(getCaptchaKey(captchaKey));
        if (!StringUtils.hasText(captcha)) {
            throw new CaptchaNotFoundException(captchaKey + " 验证码不存在");
        }

        return captcha;
    }

    @Override
    public void putCaptcha(String captchaKey, String captcha) {
        redisStringRepository.put(getCaptchaKey(captchaKey), captcha, captchaProperties.getExpireTime());
    }

    @Override
    public void removateCaptcha(String captchaKey) {
        redisStringRepository.remove(getCaptchaKey(captchaKey));
    }
}
