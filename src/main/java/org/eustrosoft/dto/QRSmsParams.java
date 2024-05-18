package org.eustrosoft.dto;

public class QRSmsParams extends QRPhoneNumberParams {

    public QRSmsParams(String basicUrl, String phone, String text) {
        super(basicUrl, generateSms(phone, text));
        this.setPhone(phone);
        this.setText(text);
    }

    public static QRDto fromStrings(
            String basicUrl, String phone, String text, String fileType,
            String x, String correctionLevel, String color, String backgroundColor
    ) throws Exception {
        QRSmsParams params = new QRSmsParams(basicUrl, phone, text);
        params.setColor(color);
        params.setCorrectionLevel(correctionLevel);
        params.setX(x);
        params.setBackgroundColor(backgroundColor);
        params.setFileType(fileType);
        return params;
    }

    private static String generateSms(String phoneNumber, String text) {
        return String.format("SMSTO:%s:%s", phoneNumber, text);
    }
}
