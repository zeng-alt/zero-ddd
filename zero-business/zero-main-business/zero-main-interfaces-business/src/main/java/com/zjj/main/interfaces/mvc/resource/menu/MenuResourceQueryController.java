package com.zjj.main.interfaces.mvc.resource.menu;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.UserContextHolder;
import com.zjj.main.application.service.MenuResourceService;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;
import com.zjj.main.interfaces.mvc.resource.menu.transformation.MenuResourceVOTransformation;
import com.zjj.main.interfaces.mvc.resource.menu.vo.MenuResourceVO;
import com.zjj.security.abac.component.annotation.AbacPostAuthorize;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:35
 */
@Tag(name = "菜单资源管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/menu/resource")
public class MenuResourceQueryController {

    private final MenuResourceService menuResourceService;
    private final MenuResourceVOTransformation transformation;

    @GetMapping("/tree")
//    @AbacPreAuthorize("GetMenuTree")
    public ResponseEntity<List<MenuResourceVO>> tree(TestVo testVo) {
        List<MenuResourceVO> result = new LinkedList<>();
        SecurityUser securityUser = UserContextHolder.getSecurityUser();
        String username = securityUser.getUsername();
        String roleCode = securityUser.getCurrentRole() == null ? null : securityUser.getCurrentRole().getAuthority();
        for (MenuResource menuResource : this.menuResourceService.tree(username, roleCode)) {
            result.add(this.transformation.to(menuResource));
        }
        return ResponseEntity.ok(result);
    }

    @Data
    public static class TestVo {
        private String username = "admin1234";
        private List<String> roles;
        private UserVo userVo;
    }

    @Data
    public static class UserVo {
        private Integer age;
        private Double weight;
    }

    @GetMapping("/tree/menu")
    public ResponseEntity<List<MenuResourceVO>> treeMenu() {
        List<MenuResourceVO> result = new LinkedList<>();
        for (MenuResource menuResource : this.menuResourceService.tree()) {
            result.add(this.transformation.toFilterButton(menuResource));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tree/all")
    public ResponseEntity<List<MenuResourceVO>> treeAll() {
        List<MenuResourceVO> result = new LinkedList<>();
        for (MenuResource menuResource : this.menuResourceService.treeAll()) {
            result.add(this.transformation.to(menuResource));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/button/{id}")
    public ResponseEntity<List<MenuResourceVO>> button(@PathVariable Long id) {
        List<MenuResourceVO> result = new LinkedList<>();
        for (MenuResource menuResource : this.menuResourceService.button(id)) {
            result.add(this.transformation.to(menuResource));
        }
        return ResponseEntity.ok(result);

    }
}
