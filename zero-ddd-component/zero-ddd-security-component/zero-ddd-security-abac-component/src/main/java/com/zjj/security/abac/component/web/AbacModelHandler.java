package com.zjj.security.abac.component.web;

import com.zjj.autoconfigure.component.core.Response;
import jakarta.servlet.ServletException;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月18日 21:31
 */
public class AbacModelHandler {

    public ServerResponse getStations(ServerRequest req) throws Exception {
        String body = req.body(String.class);
        Long entId = Long.valueOf(req.pathVariable("entId"));
        return ServerResponse.ok().body(Response.success());
    }


}
