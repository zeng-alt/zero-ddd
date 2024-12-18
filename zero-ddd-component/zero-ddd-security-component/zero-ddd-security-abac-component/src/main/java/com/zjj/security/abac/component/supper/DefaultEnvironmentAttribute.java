package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.EnvironmentAttribute;
import io.vavr.control.Option;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:17
 */
public class DefaultEnvironmentAttribute implements EnvironmentAttribute {
    @Override
    public Map<String, Object> getEnvironment(Authentication authentication) {
        HashMap<String, Object> result = new HashMap<>();

        Option.of(authentication)
                .map(Authentication::getDetails)
                .filter(WebAuthenticationDetails.class::isInstance)
                .map(a -> (WebAuthenticationDetails) a)
                .forEach(a -> {
                    result.put("remoteAddress", a.getRemoteAddress());
                    result.put("sessionId", a.getSessionId());
                });

        LocalDateTime now = LocalDateTime.now();

        result.put("now", now);
        result.put("year", now.getYear());
        result.put("month", now.getMonthValue());
        result.put("day", now.getDayOfMonth());
        result.put("hour", now.getHour());
        result.put("list", List.of("uu", "uu", "uu1"));

        return result;
    }

    @Override
    public Map<String, Class<?>> getEnvironmentType() {
//        return Map.of(
//                "now", LocalDateTime.class,
//                "year", Integer.class,
//                "month", Integer.class,
//                "day", Integer.class,
//                "hour", Integer.class,
//                "list", List<String>.class
//        );

        return Map.of(
            "now", LocalDateTime.class,
            "year", Integer.class,
            "month", Integer.class,
            "day", Integer.class,
            "hour", Integer.class
        );
    }
}
