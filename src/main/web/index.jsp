<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.eustrosoft.FileType" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.zxing.qrcode.decoder.ErrorCorrectionLevel" %>
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
    private static final String PARAM_BACKGROUND = "PARAM_BACKGROUND";
    private static final String PARAM_FILE_TYPE = "PARAM_FILE_TYPE";
    private static final String PARAM_CORRECTION_LEVEL = "PARAM_CORRECTION_LEVEL";

    // actions
    private static final String ACTION_GENERATE = "ACTION_GENERATE";

    // placeholders
    private static final String PLACEHOLDER_TEXT = "Place your text here...";
    private static final Integer PLACEHOLDER_WIDTH = 300;
    private static final String PLACEHOLDER_COLOR = "#000000";
    private static final String PLACEHOLDER_BACKGROUND = "#FFFFFF";

    private String text = "";
    private String fileType = "";
    private String color = "";
    private String background = "";
    private String width = "";
    private String correctionLevel = "";

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
        if (isNotEmpty(width)) {
            params.add(getPathParam("x", width));
        }
        if (isNotEmpty(fileType)) {
            params.add(getPathParam("fileType", fileType));
        }
        if (isNotEmpty(correctionLevel)) {
            params.add(getPathParam("correctionLevel", correctionLevel));
        }
        return String.join("&", params);
    }

    String getPathParam(String paramName, String param) throws UnsupportedEncodingException {
        if (param == null || param.isEmpty()) {
            return "";
        }
        return String.format("%s=%s", paramName, URLEncoder.encode(param, "UTF-8"));
    }

    String getErrorCorrectionLevelDescription(ErrorCorrectionLevel correctionLevel) {
        if (correctionLevel == null) {
            return null;
        }
        switch (correctionLevel) {
            case H:
                return "Highest quality (~30% correction)";
            case L:
                return "Low quality (~7% correction)";
            case M:
                return "Medium quality (~15% correction)";
            case Q:
                return "High quality (~25% correction)";
            default:
                return null;
        }
    }

    boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
%>
<%

    long enter_time = System.currentTimeMillis();

    String action_create = request.getParameter(ACTION_GENERATE);

    text = getIfNotNull(request, PARAM_TEXT).substring(0, 1900);
    width = getIfNotNull(request, PARAM_WIDTH);
    background = getIfNotNull(request, PARAM_BACKGROUND);
    color = getIfNotNull(request, PARAM_COLOR);
    fileType = getIfNotNull(request, PARAM_FILE_TYPE);
    correctionLevel = getIfNotNull(request, PARAM_CORRECTION_LEVEL);

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
                <textarea name="<%=PARAM_TEXT%>" placeholder="<%=PLACEHOLDER_TEXT%>" cols="50"
                          rows="10" maxlength="1900"><%=text%></textarea>
                <br>
                <label>Width:</label>
                <input name="<%=PARAM_WIDTH%>" max="2048" min="50" type="number" placeholder="<%=PLACEHOLDER_WIDTH%>" value="<%=width%>">
                <br>
                <label>Background:</label>
                <input name="<%=PARAM_BACKGROUND%>" type="color" placeholder="<%=PLACEHOLDER_BACKGROUND%>"
                       value="<%=background%>">
                <br>
                <label>Color:</label>
                <input name="<%=PARAM_COLOR%>" type="color" placeholder="<%=PLACEHOLDER_COLOR%>" value="<%=color%>">
                <br>
                <label>Correction Level:</label>
                <select name="<%=PARAM_CORRECTION_LEVEL%>">
                    <%
                        String correctionOption = "";
                        for (ErrorCorrectionLevel errorCorrectionLevel : ErrorCorrectionLevel.values()) {
                            String selected = "";
                            if (isNotEmpty(correctionLevel) && errorCorrectionLevel.toString().equalsIgnoreCase(correctionLevel)) {
                                selected = "selected=\"selected\"";
                            }
                            correctionOption = String.format("<option %s value=\"%s\">%s</option>",
                                    selected,
                                    errorCorrectionLevel,
                                    getErrorCorrectionLevelDescription(errorCorrectionLevel)
                            );

                    %>
                    <%=correctionOption%>
                    <%}%>
                </select>
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
            <img src="<%=generatedPath%>" width="<%=width%>">

            <label>Target link:</label>
            <a name="qr" href="<%=generatedPath%>">Link</a>
            <%}%>
        </div>
    </div>

    <hr>
    <i>timing : <%= ((System.currentTimeMillis() - enter_time) + " ms") %>
    </i>
    <br> Your web-server is <%= application.getServerInfo() %><br>
</div>
</body>
</html>