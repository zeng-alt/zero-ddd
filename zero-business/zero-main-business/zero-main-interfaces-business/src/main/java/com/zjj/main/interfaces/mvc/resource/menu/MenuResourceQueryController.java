package com.zjj.main.interfaces.mvc.resource.menu;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.UserContextHolder;
import com.zjj.main.application.dto.MenuResourceDTO;
import com.zjj.main.application.service.MenuResourceService;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;
import com.zjj.main.interfaces.mvc.resource.menu.transformation.MenuResourceVOTransformation;
import com.zjj.main.interfaces.mvc.resource.menu.vo.MenuResourceVO;
import com.zjj.security.abac.component.annotation.AbacPostAuthorize;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "获取当前用户资源树")
    @GetMapping("/tree")
//    @AbacPreAuthorize("GetMenuTree")
    public ResponseEntity<List<MenuResourceDTO>> tree() {
        List<MenuResourceVO> result = new LinkedList<>();
        SecurityUser securityUser = UserContextHolder.getSecurityUser();
        String username = securityUser.getUsername();
        String roleCode = securityUser.getCurrentRole() == null ? null : securityUser.getCurrentRole().getAuthority();
//        for (MenuResource menuResource : this.menuResourceService.tree(username, roleCode)) {
//            result.add(this.transformation.to(menuResource));
//        }
        return ResponseEntity.ok(this.menuResourceService.tree(username, roleCode));
    }

    @Operation(summary = "获取所有菜单资源")
    @GetMapping("/tree/menu")
    public ResponseEntity<List<MenuResourceDTO>> treeMenu() {
//        List<MenuResourceVO> result = new LinkedList<>();
//        for (MenuResource menuResource : this.menuResourceService.tree()) {
//            result.add(this.transformation.toFilterButton(menuResource));
//        }
//        Iterable<MenuResourceDTO> tree = this.menuResourceService.tree();
        return ResponseEntity.ok(this.menuResourceService.treeMenu());
    }

    /**
     * 获取所有菜单资源及菜单下的按钮
     * @return 菜单资源及菜单下的按钮
     */
    @Operation(summary = "获取所有资源树")
    @GetMapping("/tree/all")
    public ResponseEntity<List<MenuResourceDTO>> treeAll() {
//        List<MenuResourceVO> result = new LinkedList<>();
//        for (MenuResource menuResource : this.menuResourceService.treeAll()) {
//            result.add(this.transformation.to(menuResource));
//        }
        return ResponseEntity.ok(this.menuResourceService.treeAll());
    }

    @Operation(summary = "获取菜单下的按钮")
    @GetMapping("/button/{id}")
    public ResponseEntity<List<MenuResourceVO>> button(@PathVariable Long id) {
        List<MenuResourceVO> result = new LinkedList<>();
        for (MenuResource menuResource : this.menuResourceService.button(id)) {
            result.add(this.transformation.to(menuResource));
        }
        return ResponseEntity.ok(result);

    }
}
