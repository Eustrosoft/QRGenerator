package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.qr.Constants.EMPTY;
import static org.eustrosoft.qr.Constants.PARAM_PHONE;
import static org.eustrosoft.qr.util.Util.getOrDefault;

public class QRPhoneNumberParams extends QRDefaultParams {
    private String phone;

    public static QRPhoneNumberParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRPhoneNumberParams(
                getOrDefault(request, PARAM_PHONE, EMPTY),
                imageSettings
        );
    }

    public QRPhoneNumberParams(String phone, QRImageSettings imageSettings) {
        super(generatePhoneNumber(phone), imageSettings);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    private static String generatePhoneNumber(String phoneNumber) {
        return String.format("tel:%s", phoneNumber);
    }
}
