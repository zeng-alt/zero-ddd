package com.zjj.main.interfaces.mvc.permission;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:31
 */
@Tag(name = "权限管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/permission")
public class PermissionQueryController {

}
