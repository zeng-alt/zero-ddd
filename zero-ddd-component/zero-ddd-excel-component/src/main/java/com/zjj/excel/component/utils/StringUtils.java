package com.zjj.excel.component.utils;

import java.util.regex.Pattern;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月11日 17:32
 */
public class StringUtils {

    private static final Pattern pattern = Pattern.compile("^\\{.*\\}$", Pattern.DOTALL);

    public static boolean isEnclosedInCurlyBraces(String str) {
        if (str == null) {
            return false;
        }
        return pattern.matcher(str).matches();
    }
}
