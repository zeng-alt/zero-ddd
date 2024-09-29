package com.zjj.autoconfigure.component.security.jwt;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:41
 */
public interface JwtHelper {

    @NonNull
    public String generateJWT(@NonNull String uniqueIdentifier);

    public String generateJWT(Map<String, Object> map);

    public Map<String, Object> getClaimsFromToken(String token) throws BadCredentialsException;

    public Object getClaimFromToken(String token) throws BadCredentialsException;


    public Object getClaim(Map<String, Object> map);

    public LocalDateTime getExpire(Map<String, Object> map);

    public String tokenHeader();

}
