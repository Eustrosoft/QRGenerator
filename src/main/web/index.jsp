<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.eustrosoft.FileType" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>QR generator</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%!
    private static final String WEB_PARAM_GENERATOR_PATH = "GENERATOR_PATH";

    private static final String PARAM_TEXT = "PARAM_TEXT";

    // qr code settings
    private static final String PARAM_COLOR = "PARAM_COLOR";
    private static final String PARAM_WIDTH = "PARAM_WIDTH";
    private static final String PARAM_HEIGHT = "PARAM_HEIGHT";
    private static final String PARAM_BACKGROUND = "PARAM_BACKGROUND";
    private static final String PARAM_FILE_TYPE = "PARAM_FILE_TYPE";

    // actions
    private static final String ACTION_GENERATE = "ACTION_GENERATE";

    // placeholders
    private static final String PLACEHOLDER_TEXT = "Place your text here...";
    private static final Integer PLACEHOLDER_WIDTH = 300;
    private static final Integer PLACEHOLDER_HEIGHT = 300;
    private static final String PLACEHOLDER_COLOR = "#000000";
    private static final String PLACEHOLDER_BACKGROUND = "#FFFFFF";

    private String text = "";
    private String fileType = "";
    private String color = "";
    private String background = "";
    private String height = "";
    private String width = "";

    String getIfNotNull(HttpServletRequest request, String paramName) {
        String parameter = request.getParameter(paramName);
        return parameter == null ? "" : parameter;
    }

    String getProcessedParameters() throws UnsupportedEncodingException {
        List<String> params = new ArrayList<>();
        if (isNotEmpty(text)) {
            params.add(getPathParam("text", text));
        }
        if (isNotEmpty(color)) {
            params.add(getPathParam("color", color));
        }
        if (isNotEmpty(background)) {
            params.add(getPathParam("background", background));
        }
        if (isNotEmpty(height)) {
            params.add(getPathParam("height", height));
        }
        if (isNotEmpty(width)) {
            params.add(getPathParam("WIDTH", width));
        }
        if (isNotEmpty(fileType)) {
            params.add(getPathParam("fileType", fileType));
        }
        return String.join("&", params);
    }

    String getPathParam(String paramName, String param) throws UnsupportedEncodingException {
        if (param == null || param.isEmpty()) {
            return "";
        }
        return String.format("%s=%s", paramName, URLEncoder.encode(param, "UTF-8"));
    }

    boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
%>
<%

    long enter_time = System.currentTimeMillis();

    String action_create = request.getParameter(ACTION_GENERATE);

    text = getIfNotNull(request, PARAM_TEXT);
    width = getIfNotNull(request, PARAM_WIDTH);
    height = getIfNotNull(request, PARAM_HEIGHT);
    background = getIfNotNull(request, PARAM_BACKGROUND);
    color = getIfNotNull(request, PARAM_COLOR);
    fileType = getIfNotNull(request, PARAM_FILE_TYPE);

    if (!isNotEmpty(background)) {
        background = PLACEHOLDER_BACKGROUND;
    }

    String qrPath = config.getServletContext().getInitParameter(WEB_PARAM_GENERATOR_PATH);
    String generatedPath = "";
    if (action_create != null) {
        generatedPath = request.getContextPath() + qrPath + "?" + getProcessedParameters();
    }
%>
<div class="mainContainer">
    <div class="containerHeader">
        <h1>QR code generator</h1>
    </div>
    <div class="containerBody">
        <div>
            <form>
                <label>Text:</label>
                <textarea name="<%=PARAM_TEXT%>" placeholder="<%=PLACEHOLDER_TEXT%>" cols="50" rows="10"><%=text%></textarea>
                <br>
                <label>Width:</label>
                <input name="<%=PARAM_WIDTH%>" type="number" placeholder="<%=PLACEHOLDER_WIDTH%>" value="<%=width%>">
                <br>
                <label>Height:</label>
                <input name="<%=PARAM_HEIGHT%>" type="number" placeholder="<%=PLACEHOLDER_HEIGHT%>" value="<%=height%>">
                <br>
                <label>Background:</label>
                <input name="<%=PARAM_BACKGROUND%>" type="color" placeholder="<%=PLACEHOLDER_BACKGROUND%>" value="<%=background%>">
                <br>
                <label>Color:</label>
                <input name="<%=PARAM_COLOR%>" type="color" placeholder="<%=PLACEHOLDER_COLOR%>" value="<%=color%>">
                <br>
                <label>File type:</label>
                <select name="<%=PARAM_FILE_TYPE%>">
                    <%
                        String option = "";
                        for (FileType type : FileType.values()) {
                            String selected = "";
                            if (isNotEmpty(fileType) && type.getType().equalsIgnoreCase(fileType)) {
                                selected = "selected=\"selected\"";
                            }
                            option = String.format("<option %s value=\"%s\">%s</option>", selected, type.getType(), type.getType());

                    %>
                    <%=option%>
                    <%}%>
                </select>
                <br>
                <input name="<%=ACTION_GENERATE%>" type="submit">
            </form>
        </div>
        <div>
            <%if (isNotEmpty(text)) {%>
                <img src="<%=generatedPath%>" width="<%=width%>" height="<%=height%>">

                <label>Target link:</label>
                <a name="qr" href="<%=generatedPath%>">Link</a>
            <%}%>
        </div>
    </div>

    <hr> <i>timing : <%= ((System.currentTimeMillis() - enter_time) + " ms") %></i>
    <br> Your web-server is <%= application.getServerInfo() %><br>
</div>
</body>
</html>