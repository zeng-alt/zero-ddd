package com.zjj.main.interfaces.mvc.user;


import com.zjj.autoconfigure.component.core.ResponseEntity;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.main.application.service.UserService;
import com.zjj.main.interfaces.mvc.user.transformation.UserDetailVOTransformation;
import com.zjj.main.interfaces.mvc.user.vo.UserDetailVO;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.annotation.AbacPreHttpAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final JwtCacheManage jwtCacheManage;
    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final UserDetailVOTransformation transformation;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/detail")
    public ResponseEntity<UserDetailVO> detail(HttpServletRequest request) {
        String soleId = request.getHeader(jwtProperties.getFastToken());
        SecurityUser securityUser = jwtCacheManage.get(soleId, SecurityUser.class);
        UserProfile userProfile = userService.findProfileByUsername(securityUser.getUsername()).getOrElse((UserProfile) null);
        return ResponseEntity.ok(transformation.to(securityUser, userProfile));
    }

    @Operation(summary = "根据id获取用户信息")
    @GetMapping("/detail/{id}")
    public ResponseEntity<UserDetailVO> detailById(@PathVariable Long id) {
        return userService
                .findById(id)
                .map(transformation::to)
                .map(ResponseEntity::ok)
                .getOrElse(() -> ResponseEntity.noContent().build());
    }
}
