package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.qr.Constants.EMPTY;
import static org.eustrosoft.qr.Constants.PARAM_URL;
import static org.eustrosoft.qr.util.Util.getOrDefault;

public class QRUrlParams extends QRDefaultParams {
    private String url;

    public static QRUrlParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRUrlParams(
                getOrDefault(request, PARAM_URL, EMPTY),
                imageSettings
        );
    }

    public QRUrlParams(String url, QRImageSettings imageSettings) {
        super(url, imageSettings);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
