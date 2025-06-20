package com.zjj.main.interfaces.mvc.rule;

import com.zjj.autoconfigure.component.core.ResponseEntity;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.main.application.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:25
 */
@Tag(name = "安全规则管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/policy/rule")
public class PolicyRuleQueryController {

    private final PolicyRuleService policyRuleService;

    @Operation(summary = "根据编码查询安全规则")
    @GetMapping("/findRuleByCode/{code}/{isPreAuth}")
    public ResponseEntity<PolicyRule> findRuleByCode(@PathVariable String code, @PathVariable boolean isPreAuth) {
        PolicyRule ruleByCode = policyRuleService.findRuleByCode(code, isPreAuth);
        return ResponseEntity.ok(ruleByCode);
    }
}
