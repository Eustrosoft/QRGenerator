package org.eustrosoft.dto;

public class QRWiFiParams extends QRPhoneNumberParams {
    private String ssid;
    private String password;
    private Encryption encryption;

    public QRWiFiParams(String basicUrl, String ssid, String password, Encryption encryption) {
        super(basicUrl, generateWiFi(ssid, password, encryption));
        this.ssid = ssid;
        this.password = password;
        this.encryption = encryption;
    }

    public static QRDto fromStrings(
            String basicUrl, String ssid, String password, Encryption encryption, String fileType,
            String x, String correctionLevel, String color, String backgroundColor
    ) throws Exception {
        QRWiFiParams params = new QRWiFiParams(basicUrl, ssid, password, encryption);
        params.setColor(color);
        params.setCorrectionLevel(correctionLevel);
        params.setX(x);
        params.setBackgroundColor(backgroundColor);
        params.setFileType(fileType);
        return params;
    }

    private static String generateWiFi(String ssid, String password, Encryption encryption) {
        if (password == null) {
            password = "";
        }
        if (encryption == null) {
            encryption = Encryption.NONE;
        }
        return String.format("WIFI:S:%s;T:%s;P:%s;;", ssid, encryption.value, password);
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public enum Encryption {
        NONE(""),
        WEP("WEP"),
        WPA("WPA");

        final String value;

        Encryption(String val) {
            this.value = val;
        }
    }
}
