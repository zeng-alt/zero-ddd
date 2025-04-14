package com.zjj.main.interfaces.mvc.resource.menu;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.UserContextHolder;
import com.zjj.main.application.service.MenuResourceService;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;
import com.zjj.main.interfaces.mvc.resource.menu.transformation.MenuResourceVOTransformation;
import com.zjj.main.interfaces.mvc.resource.menu.vo.MenuResourceVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<MenuResourceVO>> tree() {
        List<MenuResourceVO> result = new LinkedList<>();
        SecurityUser securityUser = UserContextHolder.getSecurityUser();
        String username = securityUser.getUsername();
        String roleCode = securityUser.getCurrentRole() == null ? null : securityUser.getCurrentRole().getAuthority();
        for (MenuResource menuResource : this.menuResourceService.tree(username, roleCode)) {
            result.add(this.transformation.to(menuResource));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tree/all")
    public ResponseEntity<List<MenuResourceVO>> treeAll() {
        List<MenuResourceVO> result = new LinkedList<>();
        for (MenuResource menuResource : this.menuResourceService.tree()) {
            result.add(this.transformation.toFilterButton(menuResource));
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
