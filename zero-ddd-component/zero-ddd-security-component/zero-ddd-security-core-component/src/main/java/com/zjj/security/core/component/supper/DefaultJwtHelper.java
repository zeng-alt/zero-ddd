package com.zjj.security.core.component.supper;

import com.google.common.collect.Maps;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

	private final SecretKey key = Jwts.SIG.HS256.key().build();

	public DefaultJwtHelper(JwtProperties jwtProperties, JsonHelper jsonHelper) {
		this.jwtProperties = jwtProperties;
		this.jsonHelper = jsonHelper;
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
				.issuedAt(new Date()).claims(hashMap).signWith(key).compact();
	}

	public Claims getClaimsFromToken(String token) throws BadCredentialsException {
		try {
			return Jwts.parser()
					// .json(jacksonDeserializer)
					.verifyWith(key).build().parseSignedClaims(token).getPayload();
		}
		catch (Exception var5) {
			throw new BadCredentialsException("jwt校验失败");
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
		return jsonHelper.parseObject(map.get("expire").toString(), LocalDateTime.class);
	}

	@Override
	public String tokenHeader() {
		return jwtProperties.getTokenHeader();
	}

}
