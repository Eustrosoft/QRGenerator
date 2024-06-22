package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.qr.Constants;

import static org.eustrosoft.qr.Constants.EMPTY;
import static org.eustrosoft.qr.Constants.PARAM_D;
import static org.eustrosoft.qr.Constants.PARAM_P;
import static org.eustrosoft.qr.Constants.PARAM_Q;
import static org.eustrosoft.qr.Constants.PARAM_SITE;
import static org.eustrosoft.qr.util.Util.getOrDefault;

public class QREustrosoftParams extends QRDefaultParams {
    private String q;
    private String p;
    private String d;
    private String site;

    public static QREustrosoftParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QREustrosoftParams(
                getOrDefault(request, PARAM_Q, EMPTY),
                getOrDefault(request, PARAM_P, EMPTY),
                getOrDefault(request, PARAM_D, EMPTY),
                getOrDefault(request, PARAM_SITE, EMPTY),
                imageSettings
        );
    }

    public QREustrosoftParams(
            String q, String p, String d, String site,
            QRImageSettings imageSettings
    ) throws Exception {
        super(generateURL(Constants.Default.BASIC_URL, q, p, d, site), imageSettings);
        this.q = q;
        this.p = p;
        this.d = d;
        this.site = site;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public static String generateURL(String basicUrl, String q, String p, String d, String site) throws Exception {
        StringBuilder urlBuilder = new StringBuilder();
        if (site != null && !site.isEmpty()) {
            basicUrl = site;
        }
        if (basicUrl == null || basicUrl.isEmpty()) {
            basicUrl = Constants.Default.BASIC_URL;
        }
        urlBuilder.append(basicUrl).append(Constants.Query.QUESTION);

        if (q != null)
            if (isValidQR(q)) {
                setParam(urlBuilder, PARAM_Q, q);
            } else {
                throw new Exception("Illegal argument Q");
            }
        if (p != null) {
            urlBuilder.append(Constants.Query.AND);
            setParam(urlBuilder, PARAM_P, p);
        }
        if (d != null) {
            urlBuilder.append(Constants.Query.AND);
            setParam(urlBuilder, PARAM_D, d);
        }
        return urlBuilder.toString();
    }

    private static boolean isValidQR(String q) {
        try {
            Long.parseLong(q, 16);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
