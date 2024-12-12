package com.zjj.security.core.component.supper;

import com.google.common.collect.Maps;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 18:56
 */
public final class DefaultJwtHelper implements JwtHelper {

	private final JwtProperties jwtProperties;

	private final JsonHelper jsonHelper;

	private final SecretKey key;

	public DefaultJwtHelper(JwtProperties jwtProperties, JsonHelper jsonHelper) {
		this.jwtProperties = jwtProperties;
		this.jsonHelper = jsonHelper;
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
	}

	@NonNull
	public String generateJWT(@NonNull String uniqueIdentifier) {
		return generateJWT(Map.of(jwtProperties.getChaimKey(), uniqueIdentifier));
	}

	public String generateJWT(Map<String, Object> map) {
		Map<@Nullable String, @Nullable Object> hashMap = Maps.newHashMap();
		hashMap.putAll(map);
		LocalDateTime now = LocalDateTime.now();
		Long expiration = jwtProperties.getExpiration();
		TemporalUnit temporalUnit = jwtProperties.getTemporalUnit();
		LocalDateTime plus = now.plus(expiration, temporalUnit);
		// 当前时间加上过期时间
		hashMap.put("expire", jsonHelper.toJsonString(plus));
		return Jwts.builder()
				// .json(jacksonSerializer)
				.claims(hashMap)
				.issuedAt(new Date())
				.signWith(key)
				.compact();
	}

	public Claims getClaimsFromToken(String token) throws BadCredentialsException {
		try {
			return Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
		}
		catch (Exception e) {
			throw new BadCredentialsException("jwt校验失败: " + e.getMessage());
		}
	}

	public Object getClaimFromToken(String token) throws BadCredentialsException {
		try {
			return getClaimsFromToken(token).get(jwtProperties.getChaimKey());
		}
		catch (Exception var5) {
			throw new BadCredentialsException("jwt校验失败");
		}
	}

	@Override
	public Object getClaim(Map<String, Object> map) {
		return map.get(jwtProperties.getChaimKey());
	}

	@Override
	public LocalDateTime getExpire(Map<String, Object> map) {
		Object expireObj = map.get("expire");
		if (expireObj == null) {
			// 处理 null 值的情况，可以根据业务需求返回默认值或抛出异常
			throw  new IllegalArgumentException("expire is null");
		}

		if (!(expireObj instanceof String)) {
			// 处理非字符串类型的情况，可以根据业务需求返回默认值或抛出异常
			throw new IllegalArgumentException("expire must be a string");
		}

		return jsonHelper.parseObject(((String) expireObj).replace("\"", ""), LocalDateTime.class);
	}

	@Override
	public String tokenHeader() {
		return jwtProperties.getTokenHeader();
	}

}
