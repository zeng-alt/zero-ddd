package com.zjj.autoconfigure.component.security.abac;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年07月02日 15:49
 */
@Data
public class AbacTemplateEvent {
    private String key;
    private AbacContextJsonEntity abacContextJsonEntity;
}
