package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.qr.Constants.EMPTY;
import static org.eustrosoft.qr.Constants.PARAM_ENCRYPTION;
import static org.eustrosoft.qr.Constants.PARAM_PASSWORD;
import static org.eustrosoft.qr.Constants.PARAM_SSID;
import static org.eustrosoft.qr.util.Util.getOrDefault;
import static org.eustrosoft.qr.util.Util.getOrDefaultEnum;

public class QRWiFiParams extends QRDefaultParams {
    private String ssid;
    private String password;
    private Encryption encryption;

    public static QRWiFiParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRWiFiParams(
                getOrDefault(request, PARAM_SSID, EMPTY),
                getOrDefault(request, PARAM_PASSWORD, EMPTY),
                getOrDefaultEnum(request, PARAM_ENCRYPTION, Encryption.class, Encryption.WPA),
                imageSettings
        );
    }

    public QRWiFiParams(
            String ssid, String password,
            Encryption encryption, QRImageSettings imageSettings
    ) {
        super(generateWiFi(ssid, password, encryption), imageSettings);
        this.ssid = ssid;
        this.password = password;
        this.encryption = encryption;
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
