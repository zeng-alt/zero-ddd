package com.zjj.graphql.component.query.condition;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月20日 21:32
 * @version 1.0
 */
public enum Option {
    EQ("=="),
    NEQ("!="),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<="),
    IN("in"),
    NOTIN("not in"),
    LIKE("%%"),
    NOT_LIKE("!%%"),
    LIFT_LIKE("^%"),
    RIGHT_LIKE("%$"),
    ;

    String code;

    Option(String s) {
    }
}
