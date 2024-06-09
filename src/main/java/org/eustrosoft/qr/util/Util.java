package org.eustrosoft.qr.util;

import javax.servlet.http.HttpServletRequest;

import java.util.function.Function;

public class Util {

    public static <T> T getOrDefault(
            HttpServletRequest request, String param, T defaultValue
    ) throws Exception {
        if (request == null) {
            return null;
        }
        String parameter = request.getParameter(param);
        if (parameter == null) {
            return defaultValue;
        }
        return (T) parameter;
    }

    public static <T> T getOrDefaultExtract(
            HttpServletRequest request, String param,
            T defaultValue, Function<String, T> extractor
    ) throws Exception {
        if (request == null) {
            return null;
        }
        String parameter = request.getParameter(param);
        if (parameter == null) {
            return defaultValue;
        }
        return extractor.apply(parameter);
    }

    public static <T extends Enum<T>> T getOrDefaultEnum(
            HttpServletRequest request, String param, T defaultValue
    ) throws Exception {
        if (request == null) {
            return null;
        }
        String parameter = request.getParameter(param);
        if (parameter == null) {
            return defaultValue;
        }
        return Enum.valueOf(defaultValue.getDeclaringClass(), parameter);
    }

    public static boolean isAllEmpty(String... strings) {
        boolean allNullOrEmpty = true;
        for (String string : strings) {
            if (string != null && !string.isEmpty()) {
                allNullOrEmpty = false;
                break;
            }
        }
        return allNullOrEmpty;
    }

    public static Integer parseInteger(String str, Integer min, Integer max) {
        try {
            int value = Integer.parseInt(str);
            if (value <= min) {
                return min;
            }
            return Math.min(value, max);
        } catch (Exception ex) {
            return min;
        }
    }

    public static String getMaxStringForGenerate(String str, Integer maxTextSize) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        int length = str.length();
        if (length > maxTextSize) {
            return str.substring(0, maxTextSize);
        }
        return str;
    }
}
