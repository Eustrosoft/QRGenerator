<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.io.UnsupportedEncodingException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="org.eustrosoft.qr.QRParamFactory" %>
<%@ page import="org.eustrosoft.qr.dto.QRDto" %>

<%!
    // JSP MAIN SETTINGS
    private static final Boolean WRITE_TO_QR_TEXT = true;

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

    private static final String PARAM_TEMPLATE = "template"; // todo (sic!)
    private static final String TYPE_TEXT = "text";
    private static final String TYPE_TEL = "tel";
    private static final String TYPE_URL = "url";
    private static final String TYPE_EMAIL = "email";


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

    // Email params
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_SUBJECT = "subject";
    // param text

    // Sms params
    // param phone
    // param text

    // Contact params
    // param phone
    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_ORGANIZATION = "organization";
    private static final String PARAM_TITLE = "title";
    // param email
    private static final String PARAM_MOBILE_PHONE = "mobilePhone";
    private static final String PARAM_FAX = "fax";
    private static final String PARAM_STREET = "street";
    private static final String PARAM_CITY = "city";
    private static final String PARAM_REGION = "region";
    private static final String PARAM_POSTCODE = "postcode";
    private static final String PARAM_COUNTRY = "country";
    // param url

    // Wifi params
    private static final String PARAM_SSID = "ssid";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_ENCRYPTION = "encryption";

    // Location params
    private static final String PARAM_LATITUDE = "latitude";
    private static final String PARAM_LONGITUDE = "longitude";
    private static final String PARAM_DISTANCE = "distance";

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
    private static final String PLACEHOLDER_EMAIL = "example@eustrosoft.org";
    private static final String PLACEHOLDER_URL = "http://eustrosoft.org";
    private static final String PLACEHOLDER_TEL = "+7-999-999-99-99";
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
    private String email = "";
    private String subject = "";
    private String ssid = "";
    private String password = "";
    private String encryption = "";
    private String longitude = "";
    private String latitude = "";
    private String distance = "";
    private String firstName = "";
    private String lastName = "";
    private String organization = "";
    private String title = "";
    private String mobilePhone = "";
    private String fax = "";
    private String street = "";
    private String city = "";
    private String region = "";
    private String postcode = "";
    private String country = "";

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
        setIfNotEmpty(params, PARAM_TEXT, text);
        setIfNotEmpty(params, PARAM_Q, q);
        setIfNotEmpty(params, PARAM_P, p);
        setIfNotEmpty(params, PARAM_D, d);
        setIfNotEmpty(params, PARAM_URL, url);
        setIfNotEmpty(params, PARAM_PHONE, phone);
        setIfNotEmpty(params, PARAM_ONLY_TEXT, onlyText);
        setIfNotEmpty(params, PARAM_EMAIL, email);
        setIfNotEmpty(params, PARAM_SUBJECT, subject);
        setIfNotEmpty(params, PARAM_SSID, ssid);
        setIfNotEmpty(params, PARAM_PASSWORD, password);
        setIfNotEmpty(params, PARAM_ENCRYPTION, encryption);
        setIfNotEmpty(params, PARAM_LONGITUDE, longitude);
        setIfNotEmpty(params, PARAM_LATITUDE, latitude);
        setIfNotEmpty(params, PARAM_DISTANCE, distance);
        setIfNotEmpty(params, PARAM_FIRST_NAME, firstName);
        setIfNotEmpty(params, PARAM_LAST_NAME, lastName);
        setIfNotEmpty(params, PARAM_ORGANIZATION, organization);
        setIfNotEmpty(params, PARAM_TITLE, title);
        setIfNotEmpty(params, PARAM_MOBILE_PHONE, mobilePhone);
        setIfNotEmpty(params, PARAM_FAX, fax);
        setIfNotEmpty(params, PARAM_STREET, street);
        setIfNotEmpty(params, PARAM_CITY, city);
        setIfNotEmpty(params, PARAM_REGION, region);
        setIfNotEmpty(params, PARAM_POSTCODE, postcode);
        setIfNotEmpty(params, PARAM_COUNTRY, country);

        if (includeSettings) {
            // TODO: maybe remove from here (made for not include in print form link)
            setIfNotEmpty(params, "site", site);
            setIfNotEmpty(params, "type", formType);
            setIfNotEmpty(params, "color", color);
            setIfNotEmpty(params, "background", background);
            setIfNotEmpty(params, "x", width);
            setIfNotEmpty(params, "fileType", fileType);
            setIfNotEmpty(params, "correctionLevel", correctionLevel);
        }
        return String.join("&", params);
    }

    void setIfNotEmpty(
            final List<String> params,
            final String paramName, final String paramValue
    ) throws UnsupportedEncodingException {
        if (isNotEmpty(paramValue)) {
            params.add(getPathParam(paramName, paramValue));
        }
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
            case "EMAIL":
                printEmailForm();
                break;
            case "SMS":
                printSmsForm();
                break;
            case "CONTACT":
                printContactForm();
                break;
            case "WIFI":
                printWIFIForm();
                break;
            case "LOCATION":
                printLocationForm();
                break;
            default:
                printQRQXYZForm();
                break;
        }
    }

    private void printQRQXYZForm() {
        label(PARAM_Q);
        input(PARAM_Q, TYPE_TEXT, "0001AF", q);
        label(PARAM_P);
        input(PARAM_P, TYPE_TEXT, "", p);
        label(PARAM_D);
        input(PARAM_D, TYPE_TEXT, "", d);
        label(PARAM_SITE);
        input(PARAM_SITE, TYPE_TEXT, "", site);
    }

    private void printTextForm() {
        label(PARAM_TEXT);
        input(PARAM_TEXT, TYPE_TEXT, PLACEHOLDER_TEXT, text);
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "TEXT");
    }

    private void printURLForm() {
        label(PARAM_URL);
        input(PARAM_URL, TYPE_URL, PLACEHOLDER_URL, url);
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "URL");
    }

    private void printPhoneForm() {
        label(PARAM_PHONE);
        input(PARAM_PHONE, TYPE_TEL, PLACEHOLDER_TEL, phone);
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "PHONE");
    }

    private void printEmailForm() {
        label(PARAM_EMAIL);
        input(PARAM_EMAIL, TYPE_EMAIL, PLACEHOLDER_EMAIL, email);
        label(PARAM_SUBJECT);
        input(PARAM_SUBJECT, TYPE_TEXT, "Subject", subject);
        label(PARAM_TEXT);
        input(PARAM_TEXT, TYPE_TEXT, PLACEHOLDER_TEXT, text);
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "EMAIL");
    }

    private void printSmsForm() {
        label("Sms");
        input(PARAM_PHONE, TYPE_TEL, PLACEHOLDER_TEL, phone);
        label(PARAM_TEXT);
        input(PARAM_TEXT, TYPE_TEXT, "Text", text);
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "SMS");
    }

    private void printContactForm() {
        label(PARAM_PHONE);
        input(PARAM_PHONE, TYPE_TEL, PLACEHOLDER_TEL, phone);
        label(PARAM_FIRST_NAME);
        input(PARAM_FIRST_NAME, TYPE_TEXT, PLACEHOLDER_TEXT, firstName);
        label(PARAM_LAST_NAME);
        input(PARAM_LAST_NAME, TYPE_TEXT, PLACEHOLDER_TEXT, lastName);
        label(PARAM_ORGANIZATION);
        input(PARAM_ORGANIZATION, TYPE_TEXT, PLACEHOLDER_TEXT, organization);
        label(PARAM_TITLE);
        input(PARAM_TITLE, TYPE_TEXT, PLACEHOLDER_TEXT, title);
        label(PARAM_EMAIL);
        input(PARAM_EMAIL, TYPE_EMAIL, PLACEHOLDER_EMAIL, email);
        label(PARAM_MOBILE_PHONE);
        input(PARAM_MOBILE_PHONE, TYPE_TEL, PLACEHOLDER_TEL, mobilePhone);
        label(PARAM_FAX);
        input(PARAM_FAX, TYPE_TEXT, PLACEHOLDER_TEXT, fax);
        label(PARAM_STREET);
        input(PARAM_STREET, TYPE_TEXT, PLACEHOLDER_TEXT, street);
        label(PARAM_CITY);
        input(PARAM_CITY, TYPE_TEXT, PLACEHOLDER_TEXT, city);
        label(PARAM_REGION);
        input(PARAM_REGION, TYPE_TEXT, PLACEHOLDER_TEXT, region);
        label(PARAM_POSTCODE);
        input(PARAM_POSTCODE, TYPE_TEXT, PLACEHOLDER_TEXT, postcode);
        label(PARAM_COUNTRY);
        input(PARAM_COUNTRY, TYPE_TEXT, PLACEHOLDER_TEXT, country);
        label(PARAM_URL);
        input(PARAM_URL, TYPE_URL, PLACEHOLDER_URL, url);
        br();
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "CONTACT");
    }

    private void printWIFIForm() {
        label(PARAM_SSID);
        input(PARAM_SSID, TYPE_TEXT, PLACEHOLDER_TEXT, ssid);
        label(PARAM_PASSWORD);
        input(PARAM_PASSWORD, TYPE_TEXT, PLACEHOLDER_TEXT, password);
        label(PARAM_ENCRYPTION);
        input(PARAM_ENCRYPTION, TYPE_TEXT, PLACEHOLDER_TEXT, encryption);
        br();
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "WIFI");
    }

    private void printLocationForm() {
        label(PARAM_LATITUDE);
        input(PARAM_LATITUDE, TYPE_TEXT, PLACEHOLDER_TEXT, latitude);
        label(PARAM_LONGITUDE);
        input(PARAM_LONGITUDE, TYPE_TEXT, PLACEHOLDER_TEXT, longitude);
        label(PARAM_DISTANCE);
        input(PARAM_DISTANCE, TYPE_TEXT, PLACEHOLDER_TEXT, distance);
        br();
        hiddenInput(PARAM_TYPE, TYPE_TEXT, "LOCATION");
    }

    private void printFormTypes() {
        a("QXYZ", getBaseForm());
        a("Phone", getBaseForm() + "&" + PARAM_TYPE + "=PHONE");
        a("Url", getBaseForm() + "&" + PARAM_TYPE + "=URL");
        a("Text", getBaseForm() + "&" + PARAM_TYPE + "=TEXT");
        a("Email", getBaseForm() + "&" + PARAM_TYPE + "=EMAIL");
        a("Sms", getBaseForm() + "&" + PARAM_TYPE + "=SMS");
        a("Contact", getBaseForm() + "&" + PARAM_TYPE + "=CONTACT");
        a("Wifi", getBaseForm() + "&" + PARAM_TYPE + "=WIFI");
        a("Location", getBaseForm() + "&" + PARAM_TYPE + "=LOCATION");
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
        writer.print("<input name=\"" + name + "\" type=\"" + type + "\" placeholder=\"" + placeholder + "\" value=\"" + value + "\"/>");
        writer.println("<br/>");
    }

    private void hiddenInput(String name, String type, String value) {
        writer.println("<input name=\"" + name + "\" hidden=\"true\" type=\"" + type + "\" value=\"" + value + "\"/>");
        writer.println("<br/>");
    }

    public String getBaseForm() {
        return CGI_MAIN + "?" + ACTION_SHOW_FORM + "=TRUE";
    }

    public void w(String msg) {
        try {
            writer.print(msg);
        } catch (Exception ex) {
            // ignored
        }
    }

    public void wln(String msg) {
        try {
            writer.println(msg);
        } catch (Exception ioe) {
            // ignored
        }
    }

    public void wlf(String msg, Object... params) {
        try {
            writer.println(String.format(msg, params));
        } catch (Exception ioe) {
            // ignored
        }
    }

    public void wln() {
        try {
            writer.println();
        } catch (Exception ioe) {
            // ignored
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
    email = getMaxStringForGenerate(request.getParameter(PARAM_EMAIL));
    subject = getMaxStringForGenerate(request.getParameter(PARAM_SUBJECT));
    firstName = getMaxStringForGenerate(request.getParameter(PARAM_FIRST_NAME));
    lastName = getMaxStringForGenerate(request.getParameter(PARAM_LAST_NAME));
    organization = getMaxStringForGenerate(request.getParameter(PARAM_ORGANIZATION));
    title = getMaxStringForGenerate(request.getParameter(PARAM_TITLE));
    mobilePhone = getMaxStringForGenerate(request.getParameter(PARAM_MOBILE_PHONE));
    fax = getMaxStringForGenerate(request.getParameter(PARAM_FAX));
    street = getMaxStringForGenerate(request.getParameter(PARAM_STREET));
    city = getMaxStringForGenerate(request.getParameter(PARAM_CITY));
    region = getMaxStringForGenerate(request.getParameter(PARAM_REGION));
    postcode = getMaxStringForGenerate(request.getParameter(PARAM_POSTCODE));
    country = getMaxStringForGenerate(request.getParameter(PARAM_COUNTRY));
    ssid = getMaxStringForGenerate(request.getParameter(PARAM_SSID));
    password = getMaxStringForGenerate(request.getParameter(PARAM_PASSWORD));
    encryption = getMaxStringForGenerate(request.getParameter(PARAM_ENCRYPTION));
    latitude = getMaxStringForGenerate(request.getParameter(PARAM_LATITUDE));
    longitude = getMaxStringForGenerate(request.getParameter(PARAM_LONGITUDE));
    distance = getMaxStringForGenerate(request.getParameter(PARAM_DISTANCE));

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
    boolean needToShowForm = actionShowForm  == null || actionShowForm.equalsIgnoreCase("true");
    if ((needToShowForm || actionGenerate != null) && actionPrintForm == null) {
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
        input(
                PARAM_BACKGROUND,
                "color",
                PLACEHOLDER_BACKGROUND,
                background
        );
        br();
        wln("<label>Color:</label>\n");
        input(
                PARAM_COLOR,
                "color",
                PLACEHOLDER_COLOR,
                color
        );
        br();
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
        br();
        wln("<label>Target link:</label>");
        wlf("<a name=\"qr\" href=\"%s\">Link</a>", generatedPath);
        // Print to direct QR text
        if (WRITE_TO_QR_TEXT) {
            try {
                br();
                QRDto rightDto = QRParamFactory.getRightDto(request);
                if (rightDto != null) {
                    wln("Text to encode: <b>" + rightDto.getText() + " </b>");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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
        try {
            printTemplate(null, generatedPath, link, linkBack); // TODO: usage for several templates
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // FOOTER
    request.getRequestDispatcher("/footer.jsp").include(request, response);
%>
