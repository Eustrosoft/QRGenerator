package org.eustrosoft.qr;

import javax.servlet.http.HttpServletRequest;

public final class WebParams {
    // QR subsystem redirect base url
    public final static String BASIC_REDIRECT_URL = "BASIC_REDIRECT_URL";

    // Map generating default parameters
    public final static String DEFAULT_MAP_API = "DEFAULT_MAP_API";
    public final static String DEFAULT_MAP_LATITUDE = "DEFAULT_MAP_LATITUDE";
    public final static String DEFAULT_MAP_LONGITUDE = "DEFAULT_MAP_LONGITUDE";
    public final static String DEFAULT_MAP_DISTANCE = "DEFAULT_MAP_DISTANCE";

    // QR image default settings
    public final static String DEFAULT_QR_SIZE = "DEFAULT_QR_SIZE";
    public final static String DEFAULT_QR_MAX_SIZE = "DEFAULT_QR_MAX_SIZE";
    public final static String DEFAULT_QR_FILE_TYPE = "DEFAULT_QR_FILE_TYPE";
    public final static String DEFAULT_QR_CORRECTION_LEVEL = "DEFAULT_QR_CORRECTION_LEVEL";
    public final static String DEFAULT_QR_COLOR = "DEFAULT_QR_COLOR";
    public final static String DEFAULT_QR_BACKGROUND = "DEFAULT_QR_BACKGROUND";

    public static <T extends Enum<T>> T getEnum(
            HttpServletRequest request, String param, T defaultValue
    ) {
        String paramValue = request.getServletContext().getInitParameter(param);
        if (paramValue == null) {
            return defaultValue;
        }
        return Enum.valueOf(defaultValue.getDeclaringClass(), paramValue);
    }

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
