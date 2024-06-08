package org.eustrosoft.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

    public static <T> T getOrDefault(HttpServletRequest request, String param, T defaultValue) {
        if (request == null) {
            return null;
        }
        String parameter = request.getParameter(param);
        if (parameter == null) {
            return defaultValue;
        }
        return (T) parameter;
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
