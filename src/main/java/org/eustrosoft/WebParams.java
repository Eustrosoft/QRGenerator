package org.eustrosoft;

import javax.servlet.http.HttpServletRequest;

public final class WebParams {
    public final static String BASIC_REDIRECT_URL = "BASIC_REDIRECT_URL";

    public static String getString(HttpServletRequest request, String param) {
        return request.getServletContext().getInitParameter(param);
    }

    private WebParams() {

    }
}
