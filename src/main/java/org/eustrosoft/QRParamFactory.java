package org.eustrosoft;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.eustrosoft.dto.QRDefaultParams;
import org.eustrosoft.dto.QRDto;
import org.eustrosoft.dto.QREmailParams;
import org.eustrosoft.dto.QREustrosoftParams;
import org.eustrosoft.dto.QRPersonCardParams;
import org.eustrosoft.dto.QRPhoneNumberParams;
import org.eustrosoft.dto.QRSmsParams;
import org.eustrosoft.dto.QRUrlParams;
import org.eustrosoft.dto.QRWiFiParams;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.Constants.*;

public final class QRParamFactory {

    public static QRDto getRightDto(final HttpServletRequest req) throws Exception {
        String q = req.getParameter(PARAM_Q);
        String text = req.getParameter(PARAM_TEXT);
        String firstName = req.getParameter(PARAM_FIRST_NAME);
        String url = req.getParameter(PARAM_URL);
        String phoneNumber = req.getParameter(PARAM_PHONE);
        String email = req.getParameter(PARAM_EMAIL);
        String ssid = req.getParameter(PARAM_SSID);
        String subject = req.getParameter(PARAM_SUBJECT);

        if (isAllEmpty(
                q, text, firstName,
                url, phoneNumber,
                email, ssid
        )) {
            return null;
        }

        String fileType = getOrDefault(req, PARAM_FILE_TYPE, FileType.PNG.getType());
        String x = getOrDefault(req, PARAM_X, Default.IMAGE_SIZE);
        String correctionLevel = getOrDefault(req, PARAM_CORRECTION_LEVEL, ErrorCorrectionLevel.L.name());
        String color = getOrDefault(req, PARAM_COLOR, Default.COLOR);
        String background = getOrDefault(req, PARAM_BACKGROUND, Default.BACKGROUND);
        String baseUrl = WebParams.getString(req, WebParams.BASIC_REDIRECT_URL);

        if (q != null) {
            String p = req.getParameter(PARAM_P);
            String d = req.getParameter(PARAM_D);
            String site = req.getParameter(PARAM_SITE);
            return QREustrosoftParams.fromStrings(
                    baseUrl,
                    q, p, d, site,
                    fileType, x, correctionLevel,
                    color, background
            );
        }
        if (ssid != null) {
            String password = req.getParameter(PARAM_PASSWORD);
            String enc = req.getParameter(PARAM_ENCRYPTION);
            QRWiFiParams.Encryption encryption = null;
            if (enc != null) {
                encryption = QRWiFiParams.Encryption.valueOf(enc);
            }
            return QRWiFiParams.fromStrings(
                    baseUrl, ssid, password, encryption,
                    fileType, x, correctionLevel, color, background
            );
        }
        if (firstName != null) {
            String lastName = getOrDefault(req, PARAM_LAST_NAME, "");
            String phone = getOrDefault(req, PARAM_PHONE, "");
            String mobilePhone = getOrDefault(req, PARAM_MOBILE_PHONE, "");
            String organization = getOrDefault(req, PARAM_ORGANIZATION, "");
            String title = getOrDefault(req, PARAM_TITLE, "");
            String fax = getOrDefault(req, PARAM_FAX, "");;
            String street = getOrDefault(req, PARAM_STREET, "");;
            String city = getOrDefault(req, PARAM_CITY, "");;
            String region = getOrDefault(req, PARAM_REGION, "");;
            String postcode = getOrDefault(req, PARAM_POSTCODE, "");;
            String country = getOrDefault(req, PARAM_COUNTRY, "");;
            email = getOrDefault(req, PARAM_EMAIL, "");;
            url = getOrDefault(req, PARAM_URL, "");;

            return QRPersonCardParams.fromStrings(
                    baseUrl, phone, firstName, lastName, organization,
                    title, email, mobilePhone, fax, street,
                    city, region, postcode, country, url, fileType,
                    x, correctionLevel, color, background
            );
        }
        if (phoneNumber != null && text != null) {
            return QRSmsParams.fromStrings(
                    baseUrl, phoneNumber, text,
                    fileType, x, correctionLevel, color, background
            );
        }
        if (phoneNumber != null) {
            return QRPhoneNumberParams.fromStrings(
                    baseUrl, phoneNumber, fileType,
                    x, correctionLevel, color, background
            );
        }
        if (subject != null && email != null) {
            return QREmailParams.fromStrings(
                    baseUrl, email, subject, text,
                    fileType, x, correctionLevel, color, background
            );
        }
        if (url != null) {
            return QRUrlParams.fromStrings(
                    baseUrl, url, fileType, x,
                    correctionLevel, correctionLevel, background
            );
        }

        if (text != null) {
            return QRDefaultParams.fromStrings(
                    text, color, background,
                    fileType, x, correctionLevel
            );
        }
        return null;
    }

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

    private static boolean isAllEmpty(String... strings) {
        boolean allNullOrEmpty = true;
        for (String string : strings) {
            if (string != null && !string.isEmpty()) {
                allNullOrEmpty = false;
                break;
            }
        }
        return allNullOrEmpty;
    }
}
