package com.zjj.main.domain.rule;

import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:38
 */
public interface PolicyRule {

    boolean evaluate(Map<String, Object> context);
}
