<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%!
    // JSP MAIN SETTINGS
    private static final String CGI_MAIN = "index2.jsp";
    private static final String WEB_PARAM_GENERATOR_PATH = "GENERATOR_PATH";

    private static final String DEFAULT_TEMPLATE = "DEFAULT";

    private static final String TEMPLATE_LINK_BACK = "${LINK_BACK}";
    private static final String TEMPLATE_IMAGE = "${IMAGE}";
    private static final String TEMPLATE_LINK = "${LINK}";

    private static final String[] TEMPLATE_NAMES = new String[]{"DEFAULT"};
    private static final String[] TEMPLATE_HTML = new String[]{
            "<style>" +
                    ".content { display: flex; flex-direction: column; justify-content:center; align-items: center; }" +
                    "a { text-decoration: none; }" +
                    ".up { height:10px; }" +
                    "</style>" +
                    "<body><div class=\"content\"><div class=\"up\"></div><div>" +
                    "<span><center> Сканируйте <br>для обслуживания и ремонта <br></center></span>" +
                    "</div><div id='mainBlock'><a href=\"${LINK_BACK}\"><img src='${IMAGE}'/></a></div>" +
                    "<div><span><center>или перейдите по ссылке:<br>" +
                    "<a href=\"${LINK}\">${LINK}</a></center></span></div></div>"
    };

    // QR code parameters

    private static final String PARAM_TEMPLATE = "template";
    // QR type
    private static final String PARAM_TYPE = "type";

    // QXYZ params
    private static final String PARAM_Q = "q";
    private static final String PARAM_P = "p";
    private static final String PARAM_D = "d";
    private static final String PARAM_SITE = "site";

    // Text params
    private static final String PARAM_TEXT = "text";

    // URL params
    private static final String PARAM_URL = "url";

    // Phone params
    private static final String PARAM_PHONE = "phone";

    // QR code settings
    private static final String PARAM_COLOR = "PARAM_COLOR";
    private static final String PARAM_WIDTH = "PARAM_WIDTH";
    private static final String PARAM_BACKGROUND = "PARAM_BACKGROUND";
    private static final String PARAM_FILE_TYPE = "PARAM_FILE_TYPE";
    private static final String PARAM_CORRECTION_LEVEL = "PARAM_CORRECTION_LEVEL";
    private static final String PARAM_ONLY_TEXT = "onlyText";

    // ACTIONS
    private static final String ACTION_GENERATE = "ACTION_GENERATE";
    private static final String ACTION_PRINT_FORM = "PRINT_FORM";
    private static final String ACTION_SHOW_FORM = "SHOW_FORM";

    // PLACEHOLDERS
    private static final String PLACEHOLDER_TEXT = "Place your text here...";
    private static final Integer PLACEHOLDER_WIDTH = 300;
    private static final String PLACEHOLDER_COLOR = "#000000";
    private static final String PLACEHOLDER_BACKGROUND = "#FFFFFF";

    private String formType = "";

    private String text = "";
    private String q = "";
    private String p = "";
    private String d = "";
    private String site = "";
    private String url = "";
    private String phone = "";

    private String fileType = "";
    private String color = "";
    private String background = "";
    private String width = "";
    private String correctionLevel = "";
    private String onlyText = "";

    private PrintWriter writer;

    String getIfNotNull(HttpServletRequest request, String paramName, String defaultValue) {
        String parameter = request.getParameter(paramName);
        return parameter == null ? defaultValue : parameter;
    }

    String getProcessedParameters() throws UnsupportedEncodingException {
        return getProcessedParameters(true);
    }

    String getProcessedParameters(boolean includeSettings) throws UnsupportedEncodingException {
        List<String> params = new ArrayList<>();
        if (isNotEmpty(text)) {
            params.add(getPathParam("text", text));
        }
        if (isNotEmpty(q)) {
            params.add(getPathParam("q", q));
        }
        if (isNotEmpty(p)) {
            params.add(getPathParam("p", p));
        }
        if (isNotEmpty(d)) {
            params.add(getPathParam("d", d));
        }
        if (isNotEmpty(url)) {
            params.add(getPathParam("url", url));
        }
        if (isNotEmpty(phone)) {
            params.add(getPathParam("phone", phone));
        }
        if (isNotEmpty(onlyText)) {
            params.add(getPathParam("onlyText", onlyText));
        }
        if (includeSettings) {
            // TODO: maybe remove from here (made for not include in print form link)
            if (isNotEmpty(site)) {
                params.add(getPathParam("site", site));
            }
            if (isNotEmpty(formType)) {
                params.add(getPathParam("type", formType));
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

    private static String getMaxStringForGenerate(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        int length = str.length();
        if (length > 1900) {
            return str.substring(0, 1900);
        }
        return str;
    }

    private static void printTimingAndServer(PrintWriter writer, ServletContext servletContext, long enterTime) {
        writer.println("<hr/>");
        writer.println("<i>timing: " + (System.currentTimeMillis() - enterTime) + " ms.");
        writer.println("<br/>");
        writer.println("Your web-server is " + servletContext.getServerInfo());
    }

    private void printForm(String formType) {
        if (formType == null) {
            printQRQXYZForm();
            return;
        }
        switch (formType) {
            case "TEXT":
                printTextForm();
                break;
            case "URL":
                printURLForm();
                break;
            case "PHONE":
                printPhoneForm();
                break;
            default:
                printQRQXYZForm();
                break;
        }
    }

    private void printQRQXYZForm() {
        label(PARAM_Q);
        input(PARAM_Q, "text", "0001AF", q);
        br();
        label(PARAM_P);
        input(PARAM_P, "text", "", p);
        br();
        label(PARAM_D);
        input(PARAM_D, "text", "", d);
        br();
        label(PARAM_SITE);
        input(PARAM_SITE, "text", "", site);
        br();
    }

    private void printTextForm() {
        label(PARAM_TEXT);
        input(PARAM_TEXT, "text", "", text);
        br();
        hiddenInput(PARAM_TYPE, "text", "TEXT");
    }

    private void printURLForm() {
        label(PARAM_URL);
        input(PARAM_URL, "url", "", url);
        br();
        hiddenInput(PARAM_TYPE, "text", "URL");
    }

    private void printPhoneForm() {
        label(PARAM_PHONE);
        input(PARAM_PHONE, "tel", "", phone);
        br();
        hiddenInput(PARAM_TYPE, "text", "PHONE");
    }

    private void printFormTypes() {
        a("QXYZ", getBaseForm());
        a("Phone", getBaseForm() + "&" + PARAM_TYPE + "=PHONE");
        a("Url", getBaseForm() + "&" + PARAM_TYPE + "=URL");
        a("Text", getBaseForm() + "&" + PARAM_TYPE + "=TEXT");
    }

    private void label(String text) {
        writer.println("<label>" + text + "</label>");
    }

    private void br() {
        writer.println("<br/>");
    }

    private void a(String name, String href) {
        writer.println("<a href=\"" + href + "\"/>" + name + "</a>");
    }

    private void input(String name, String type, Object placeholder, String value) {
        writer.println("<input name=\"" + name + "\" type=\"" + type + "\" placeholder=\"" + placeholder + "\" value=\"" + value + "\"/>");
    }

    private void hiddenInput(String name, String type, String value) {
        writer.println("<input name=\"" + name + "\" hidden=\"true\" type=\"" + type + "\" value=\"" + value + "\"/>");
    }

    public String getBaseForm() {
        return CGI_MAIN + "?" + ACTION_SHOW_FORM + "=TRUE";
    }

    public String getBaseLinkTyped(String type) {
        return CGI_MAIN + "?" + ACTION_SHOW_FORM + "=" + Boolean.TRUE + "&" + PARAM_TYPE + "=" + type;
    }

    public void w(String msg) {
        try {
            writer.print(msg);
        } catch (Exception ex) {
        }
    }

    public void wln(String msg) {
        try {
            writer.println(msg);
        } catch (Exception ioe) {
        }
    }

    public void wlf(String msg, Object... params) {
        try {
            writer.println(String.format(msg, params));
        } catch (Exception ioe) {
        }
    }

    public void wln() {
        try {
            writer.println();
        } catch (Exception ioe) {
        }
    }

    public void printTemplate(String template, String image, String link, String linkBack) throws Exception {
        if (template == null) {
            template = DEFAULT_TEMPLATE;
        }
        int templateIndex = -1;
        for (int i = 0; i < TEMPLATE_NAMES.length; i++) {
            if (template.equals(TEMPLATE_NAMES[i])) {
                templateIndex = i;
                break;
            }
        }
        if (templateIndex == -1) {
            templateIndex = 0;
        }

        String html = TEMPLATE_HTML[templateIndex];
        html = html.replace(TEMPLATE_IMAGE, image);
        html = html.replace(TEMPLATE_LINK, link);
        html = html.replace(TEMPLATE_LINK_BACK, linkBack);
        wln(html);
    }

    // File type enum for separate using
    public enum FileType {
        SVG("SVG"),
        PNG("PNG"),
        JPG("JPG"),
        JPEG("JPEG");

        public final static String[] CONTENT_TYPES = {"image/svg+xml", "image/png", "image/jpg", "image/jpeg"};

        private String type;

        public static FileType of(String value) {
            if (value == null || value.isEmpty()) {
                return null;
            }
            FileType[] values = values();
            for (FileType fileType : values) {
                if (value.equalsIgnoreCase(fileType.type)) {
                    return fileType;
                }
            }
            return null;
        }

        public static String getContentType(org.eustrosoft.qr.FileType value) {
            if (value == null) {
                return null;
            }
            return CONTENT_TYPES[value.ordinal()];
        }

        public String getType() {
            return type;
        }

        FileType(String type) {
            this.type = type;
        }
    }

    // ErrorCorrectionLevel enum from zxing for separate using
    public enum ErrorCorrectionLevel {
        L(1),
        M(0),
        Q(3),
        H(2);

        private static final ErrorCorrectionLevel[] FOR_BITS = new ErrorCorrectionLevel[]{M, L, H, Q};
        private final int bits;

        private ErrorCorrectionLevel(int bits) {
            this.bits = bits;
        }

        public int getBits() {
            return this.bits;
        }

        public static ErrorCorrectionLevel forBits(int bits) {
            if (bits >= 0 && bits < FOR_BITS.length) {
                return FOR_BITS[bits];
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
%>

<%
    long enterTime = System.currentTimeMillis();
    writer = response.getWriter();

    // ACTIONS
    String actionGenerate = request.getParameter(ACTION_GENERATE);
    String actionPrintForm = request.getParameter(ACTION_PRINT_FORM);
    String actionShowForm = request.getParameter(ACTION_SHOW_FORM);

    // TODO: get default values from web.xml

    formType = request.getParameter(PARAM_TYPE);
    // QR Params
    text = getMaxStringForGenerate(request.getParameter(PARAM_TEXT));
    q = getMaxStringForGenerate(request.getParameter(PARAM_Q));
    p = getMaxStringForGenerate(request.getParameter(PARAM_P));
    d = getMaxStringForGenerate(request.getParameter(PARAM_D));
    site = getMaxStringForGenerate(request.getParameter(PARAM_SITE));
    url = getMaxStringForGenerate(request.getParameter(PARAM_URL));
    phone = getMaxStringForGenerate(request.getParameter(PARAM_PHONE));

    // QR Settings
    width = getIfNotNull(request, PARAM_WIDTH, PLACEHOLDER_WIDTH.toString());
    background = getIfNotNull(request, PARAM_BACKGROUND, PLACEHOLDER_BACKGROUND);
    color = getIfNotNull(request, PARAM_COLOR, PLACEHOLDER_COLOR);
    fileType = getIfNotNull(request, PARAM_FILE_TYPE, FileType.SVG.name());
    correctionLevel = getIfNotNull(request, PARAM_CORRECTION_LEVEL, ErrorCorrectionLevel.M.name());
    onlyText = getMaxStringForGenerate(request.getParameter(PARAM_ONLY_TEXT));

    if (!isNotEmpty(background)) {
        background = PLACEHOLDER_BACKGROUND;
    }

    String servletPath = config.getServletContext().getInitParameter(WEB_PARAM_GENERATOR_PATH);
    String generatedPath =  request.getContextPath() + servletPath + "?" + getProcessedParameters();

    // HEADER
    request.getRequestDispatcher("/header.jsp").include(request, response);

    if (actionPrintForm == null) {
        wln("<body>");
        wln("<div class=\"mainContainer\">");
        wln("<div class=\"containerHeader\">");
        wln("<h1>QR code generator</h1>");
        wln("</div>");
        wln("<div class=\"containerBody\">");
    }

    // FORM print section
    if ((actionShowForm != null || actionGenerate != null) && actionPrintForm == null) {
        wln("<div>");
        wln("<form>");
        // Choose form type
        printForm(formType);

        // Print settings always same
        wln("<label>Width:</label>\n");
        wlf("<input name=\"%s\" max=\"2048\" min=\"50\" type=\"number\" placeholder=\"%s\" value=\"%s\"> <br>",
                PARAM_WIDTH,
                PLACEHOLDER_WIDTH,
                width
        );
        wln("<label>Background:</label>\n");
        wlf("<input name=\"%s\" type=\"color\" placeholder=\"%s\" value=\"%s\"> <br>",
                PARAM_BACKGROUND,
                PLACEHOLDER_BACKGROUND,
                background
        );
        wln("<label>Color:</label>\n");
        wlf("<input name=\"%s\" type=\"color\" placeholder=\"%s\" value=\"%s\"><br>",
                PARAM_COLOR,
                PLACEHOLDER_COLOR,
                color
        );
        wln("<label>Correction level:</label>\n");

        wlf("<select name=\"%s\">", PARAM_CORRECTION_LEVEL);

        for (ErrorCorrectionLevel errorCorrectionLevel : ErrorCorrectionLevel.values()) {
            String selected = "";
            if (isNotEmpty(correctionLevel) && errorCorrectionLevel.toString().equalsIgnoreCase(correctionLevel)) {
                selected = "selected=\"selected\"";
            }
            wlf("<option %s value=\"%s\">%s</option>",
                    selected,
                    errorCorrectionLevel,
                    getErrorCorrectionLevelDescription(errorCorrectionLevel)
            );
        }

        wln("</select><br/>");


        wln("<label>File type:</label>\n");
        wlf("<select name=\"%s\">", PARAM_FILE_TYPE);

        for (FileType type : FileType.values()) {
            String selected = "";
            if (isNotEmpty(fileType) && type.getType().equalsIgnoreCase(fileType)) {
                selected = "selected=\"selected\"";
            }
            wlf(
                    "<option %s value=\"%s\">%s</option>",
                    selected,
                    type.getType(),
                    type.getType()
            );
        }

        wln("</select><br/>");

        wlf("<input name=\"%s\" type=\"submit\">", ACTION_GENERATE);

        // Links for form types
        printFormTypes();

        wln("</form>");
        wln("</div>");
    }

    // Image generating
    if (actionGenerate != null && actionPrintForm == null && onlyText.isEmpty()) {
        wln("<div>");
        // Generate print form instead of generate action
        String queryString = request.getQueryString().replace(ACTION_GENERATE, ACTION_PRINT_FORM);
        wln("<a href=\"" + CGI_MAIN + "?" + queryString + "\">"); // Make link from image to go back
        wlf("<img src=\"%s\" width=\"%s\">", generatedPath, width);
        wln("</a>");
        wln("<label>Target link:</label>");
        wlf("<a name=\"qr\" href=\"%s\">Link</a>", generatedPath);
        wln("</div>");
    }
    // Text generating
    if (!onlyText.isEmpty()) {
        wln(String.format("<label>Text to generate: %s</label>", generatedPath));
    }

    // Server info
    if ((actionShowForm != null || actionGenerate != null) && actionPrintForm == null) {
        printTimingAndServer(writer, request.getServletContext(), enterTime);
    }

    if (actionPrintForm == null) {
        wln("</div>");
        wln("</body>");
    }

    // Generate print form
    if (actionPrintForm != null) {
        String link = isNotEmpty(site) ? site + "?" : "https://qr.qxyz.ru?";
        link = link + getProcessedParameters(false);
        // Generating back link: replace print form action to generate qr action in form
        String linkBack = CGI_MAIN + "?" + request.getQueryString().replace(ACTION_PRINT_FORM, ACTION_GENERATE);
        printTemplate(null, generatedPath, link, linkBack); // TODO: usage for several templates
    }

    // FOOTER
    request.getRequestDispatcher("/footer.jsp").include(request, response);
%>
