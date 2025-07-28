package com.zjj.autoconfigure.component.security.abac;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月10日 16:19
 */
@Data
public class AbacContextJsonEntity implements Serializable, ContextEntity<Map<String, String>, String, Map<String, String>> {

    private Map<String, String> objectEntities;
    private String resultObject;
    private Map<String, String> arguments;
}
