package com.zjj.security.jwt.component.supper;

import com.google.common.collect.Maps;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.security.jwt.component.configuration.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 18:56
 */
public final class DefaultJwtHelper implements JwtHelper {

    private final JwtProperties jwtProperties;

    public DefaultJwtHelper(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
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
        // 当前时间加上过期时间
        hashMap.put("create", now.plus(expiration, temporalUnit));
        hashMap.put("expire", LocalDateTime.now());
        return Jwts
                .builder()
                .setClaims(hashMap)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    public Map<String, Object> getClaimsFromToken(String token) throws BadCredentialsException {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception var5) {
            throw new BadCredentialsException("jwt校验失败");
        }
    }

    public Object getClaimFromToken(String token) throws BadCredentialsException {
        try {
            return getClaimsFromToken(token).get(jwtProperties.getChaimKey());
        } catch (Exception var5) {
            throw new BadCredentialsException("jwt校验失败");
        }
    }

    @Override
    public Object getClaim(Map<String, Object> map) {
        return map.get(jwtProperties.getChaimKey());
    }

    @Override
    public LocalDateTime getExpire(Map<String, Object> map) {
        return (LocalDateTime) map.get("expire");
    }

    @Override
    public String tokenHeader() {
        return jwtProperties.getTokenHeader();
    }


}
