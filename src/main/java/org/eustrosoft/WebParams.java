package org.eustrosoft;

import javax.servlet.http.HttpServletRequest;

public final class WebParams {
    public final static String BASIC_REDIRECT_URL = "BASIC_REDIRECT_URL";
    public final static String DEFAULT_MAP_LATITUDE = "DEFAULT_MAP_LATITUDE";
    public final static String DEFAULT_MAP_LONGITUDE = "DEFAULT_MAP_LONGITUDE";
    public final static String DEFAULT_MAP_DISTANCE = "DEFAULT_MAP_DISTANCE";

    public static String getString(HttpServletRequest request, String param) {
        return request.getServletContext().getInitParameter(param);
    }

    public static String getString(
            HttpServletRequest request, String param, String defaultValue
    ) {
        String paramValue = request.getServletContext().getInitParameter(param);
        if (paramValue == null) {
            return defaultValue;
        }
        return paramValue;
    }

    public static Float getFloat(HttpServletRequest request, String param, Float defaultValue) {
        String paramValue = request.getServletContext().getInitParameter(param);
        if (paramValue == null) {
            return defaultValue;
        }
        return Float.parseFloat(paramValue);
    }

    public static Integer getInteger(HttpServletRequest request, String param, Integer defaultValue) {
        String paramValue = request.getServletContext().getInitParameter(param);
        if (paramValue == null) {
            return defaultValue;
        }
        return Integer.parseInt(paramValue);
    }

    private WebParams() {

    }
}
