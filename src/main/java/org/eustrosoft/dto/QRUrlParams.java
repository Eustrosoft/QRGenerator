package org.eustrosoft.dto;

public class QRUrlParams extends QRDefaultParams {
    private String url;

    public QRUrlParams(String basicUrl, String url) {
        super(url);
    }

    public static QRDto fromStrings(
            String basicUrl, String url, String fileType,
            String x, String correctionLevel, String color, String backgroundColor
    ) throws Exception {
        QRUrlParams params = new QRUrlParams(basicUrl, url);
        params.setColor(color);
        params.setCorrectionLevel(correctionLevel);
        params.setX(x);
        params.setBackgroundColor(backgroundColor);
        params.setFileType(fileType);
        return params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
