package com.zjj.security.core.component.supper;

import com.google.common.collect.Maps;
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
import java.util.Date;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 18:56
 */
public final class DefaultJwtHelper implements JwtHelper {

	private final JwtProperties jwtProperties;

	private final SecretKey key;

	public DefaultJwtHelper(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
	}

	@NonNull
	public String generateJWT(@NonNull String uniqueIdentifier) {
		return generateJWT(Map.of(jwtProperties.getChaimKey(), uniqueIdentifier));
	}

	public String generateJWT(Map<String, Object> map) {
		Map<@Nullable String, @Nullable Object> hashMap = Maps.newHashMap();
		hashMap.putAll(map);

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
	public String tokenHeader() {
		return jwtProperties.getTokenHeader();
	}

}
