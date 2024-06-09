package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.qr.Constants.EMPTY;
import static org.eustrosoft.qr.Constants.PARAM_PHONE;
import static org.eustrosoft.qr.Constants.PARAM_TEXT;
import static org.eustrosoft.qr.util.Util.getOrDefault;

public class QRSmsParams extends QRDefaultParams {
    private String phone;

    public static QRSmsParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRSmsParams(
                getOrDefault(request, PARAM_PHONE, EMPTY),
                getOrDefault(request, PARAM_TEXT, EMPTY),
                imageSettings
        );
    }

    public QRSmsParams(String phone, String text, QRImageSettings imageSettings) {
        super(generateSms(phone, text), imageSettings);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private static String generateSms(String phoneNumber, String text) {
        return String.format("SMSTO:%s:%s", phoneNumber, text);
    }
}
