package org.eustrosoft.dto;

public class QRSmsParams extends QRDefaultParams {
    private String phone;

    public QRSmsParams(String basicUrl, String phone, String text) {
        super(generateSms(phone, text));
        this.phone = phone;
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
