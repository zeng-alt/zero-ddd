package com.zjj.autoconfigure.component.security;

import com.zjj.autoconfigure.component.UtilException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 09:11
 */
public final class AuthenticationHelper {


    public static final String ERROR_KEY = "error:key";

    private AuthenticationHelper() {}


    public static void setErrorMsg(@NonNull HttpServletRequest request, @NonNull Exception e) {
        request.setAttribute(ERROR_KEY, e.getMessage());
    }

    @NonNull
    public static Optional<String> getErrorMsg(@NonNull HttpServletRequest request) {
        Object attribute = request.getAttribute(ERROR_KEY);
        if (attribute == null) {
            return Optional.empty();
        }
        return Optional.of(attribute.toString());
    }

    public static void renderString(@NonNull HttpServletResponse response, int status, String msg, String data) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String result =
                    """
                    {
                        "status":   "%d",
                        "msg":      "%s",
                        "data":     "%s",
                        "date":     "%s"
                    }
                    """.formatted(status, msg, data, LocalDateTime.now());
            response.getWriter().print(result);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }
}
