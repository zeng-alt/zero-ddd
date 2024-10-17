package com.zjj.graphql.component.utils;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.isIn;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 21:39
 */
public class TypeMatchUtils {

    public static String matchType(Class clasz) {
        return Match(clasz).of(
                Case($(String.class), "String"),
                Case($(BigInteger.class), "BigInteger"),
                Case($(Boolean.class), "Boolean"),
                Case($(Integer.class), "Int"),
                Case($(Long.class), "Long"),
                Case($(isIn(BigDecimal.class)), "BigDecimal"),
                Case($(isIn(Float.class, Double.class)), "Float"),
                Case($(Byte.class), "Byte"),
                Case($(UUID.class), "UUID"),
                Case($(isIn(LocalDateTime.class, Date.class, LocalDate.class, LocalTime.class)), "Date"),
                Case($(), clasz.getSimpleName())
        );
    }
}
