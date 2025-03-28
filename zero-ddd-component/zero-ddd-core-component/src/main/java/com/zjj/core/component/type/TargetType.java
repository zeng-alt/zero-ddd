package com.zjj.core.component.type;

import lombok.Getter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 21:55
 */
@Getter
public enum TargetType {
    STRING("String"),
    INTEGER("Integer"),
    LIST("List"),
    BOOLEAN("Boolean"),
    LOCAL_DATE_TIME("LocalDateTime"),
    LOCALTIME("LocalTime"),
    LOCAL_DATE("LocalDate"),
    DOUBLE("Double"),
    LONG("Long"),
    BIG_DECIMAL("BigDecimal"),
    ;

    private final String type;

    TargetType(String type) {
        this.type = type;
    }
}
