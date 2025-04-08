package com.zjj.main.interfaces.mvc.permission;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "权限管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/permission")
public class PermissionController {


}
