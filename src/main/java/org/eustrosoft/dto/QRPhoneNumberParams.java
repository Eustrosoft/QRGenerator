package org.eustrosoft.dto;

public class QRPhoneNumberParams extends QRDefaultParams {
    private String phone;

    public QRPhoneNumberParams(String basicUrl, String phone) {
        super(generatePhoneNumber(phone));
        this.phone = phone;
    }

    public static QRDto fromStrings(
            String basicUrl, String phone, String fileType,
            String x, String correctionLevel, String color, String backgroundColor
    ) throws Exception {
        QRPhoneNumberParams params = new QRPhoneNumberParams(basicUrl, phone);
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

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    private static String generatePhoneNumber(String phoneNumber) {
        return String.format("tel:%s", phoneNumber);
    }
}
