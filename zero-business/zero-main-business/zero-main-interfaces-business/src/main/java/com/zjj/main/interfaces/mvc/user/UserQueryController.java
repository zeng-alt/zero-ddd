package com.zjj.main.interfaces.mvc.user;

import com.zjj.autoconfigure.component.core.Response;
import com.zjj.main.interfaces.mvc.user.from.StockInUserFrom;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月31日 21:16
 */
@Tag(name = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/user")
public class UserQueryController {

    @GetMapping("/detail")
    public Response<StockInUserFrom> detail() {
        return Response.success(new StockInUserFrom());
    }
}
