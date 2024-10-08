package com.zjj.autoconfigure.component.security.jwt.validator;

import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.temporal.TemporalUnit;

/**
 * 校验过期的时间要大于等于20分钟
 */
public class ExpirationValidator implements ConstraintValidator<ValidExpiration, JwtProperties> {

	// @Override
	// public void initialize(ValidExpiration constraintAnnotation) {
	// }

	@Override
	public boolean isValid(JwtProperties jwtProperties, ConstraintValidatorContext context) {
		if (jwtProperties == null) {
			return true; // 如果对象为空，不进行校验
		}

		Long expiration = jwtProperties.getExpiration();
		TemporalUnit temporalUnit = jwtProperties.getTemporalUnit();
		if (temporalUnit == null || expiration == null) {
			return false; // 如果任何一个字段为空，校验失败
		}

		long minutes = temporalUnit.getDuration().toMinutes();
		long totalMinutes = expiration * minutes;

		return totalMinutes >= 20;
	}

}
