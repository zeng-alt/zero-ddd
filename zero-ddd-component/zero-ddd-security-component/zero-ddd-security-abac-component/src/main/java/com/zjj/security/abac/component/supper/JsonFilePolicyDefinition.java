package com.zjj.security.abac.component.supper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zjj.autoconfigure.component.security.abac.PolicyDefinition;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:07
 */
@Slf4j
public class JsonFilePolicyDefinition implements PolicyDefinition {
    private static final String DEFAULT_POLICY_FILE_NAME = "default-policy.json";

    private Map<String, PolicyRule> rules;

    public JsonFilePolicyDefinition(String policyFilePath){
        policyFilePath = StringUtils.hasText(policyFilePath) ? policyFilePath : DEFAULT_POLICY_FILE_NAME;
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Expression.class, new SpelDeserializer());
        mapper.registerModule(module);
        try {
            PolicyRule[] rulesArray = null;
            log.debug("[init] Checking policy file at: {}", policyFilePath);
            log.info("[init] Loading policy from custom file: {}", policyFilePath);
            File file = ResourceUtils.getFile("classpath:" + policyFilePath);
            if (file.exists()) {
                rulesArray = mapper.readValue(new FileInputStream(file), PolicyRule[].class);
            }

            this.rules = (rulesArray != null? Arrays.stream(rulesArray).collect(Collectors.toConcurrentMap(PolicyRule::getName, rule -> rule)) : new ConcurrentHashMap<>());
            log.info("[init] Policy loaded successfully.");
        } catch (JsonMappingException e) {
            log.error("An error occurred while parsing the policy file.", e);
        } catch (IOException e) {
            log.error("An error occurred while reading the policy file.", e);
        }
    }

    @Override
    public List<PolicyRule> getAllPolicyRules() {
        return rules.values().stream().toList();
    }

    @Override
    public PolicyRule getPolicyRule(String tenant, String key, String typeClass, boolean isPreAuth) {
        return rules.getOrDefault(key, null);
    }

    @Override
    public PolicyRule getPolicyRule(String policyKey) {
        return rules.getOrDefault(policyKey, null);
    }
}
