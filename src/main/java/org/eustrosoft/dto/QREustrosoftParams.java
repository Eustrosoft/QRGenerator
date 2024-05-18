package org.eustrosoft.dto;

import org.eustrosoft.Constants;
import org.eustrosoft.FileType;

import java.awt.Color;

import static org.eustrosoft.Constants.PARAM_D;
import static org.eustrosoft.Constants.PARAM_P;
import static org.eustrosoft.Constants.PARAM_Q;
import static org.eustrosoft.Constants.PARAM_SITE;

public class QREustrosoftParams extends QRDefaultParams {
    private String q;
    private String p;
    private String d;
    private String site;

    public QREustrosoftParams(String basicUrl, String q, String p, String d, String site) throws Exception {
        super(generateURL(basicUrl, q, p, d, site));
        this.q = q;
        this.p = p;
        this.d = d;
        this.site = site;
    }

    public QREustrosoftParams(String q, String p, String d, String site) throws Exception {
        this(Constants.Default.BASIC_URL, q, p, d, site);
    }

    public QREustrosoftParams(String q, String p, String d, String site,
                              Color color, Color backgroundColor, FileType fileType, Integer x) throws Exception {
        super(generateURL(Constants.Default.BASIC_URL, q, p, d, site), color, fileType, x);
        this.setBackgroundColor(backgroundColor);
        this.q = q;
        this.p = p;
        this.d = d;
        this.site = site;
    }

    public static QRDto fromStrings(
            String basicUrl, String q, String p, String d, String site, String fileType,
            String x, String correctionLevel, String color, String backgroundColor
    ) throws Exception {
        QREustrosoftParams params = new QREustrosoftParams(basicUrl, q, p, d, site);
        params.setColor(color);
        params.setCorrectionLevel(correctionLevel);
        params.setX(x);
        params.setBackgroundColor(backgroundColor);
        params.setFileType(fileType);
        return params;
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
        if (site != null) {
            urlBuilder.append(Constants.Query.AND);
            setParam(urlBuilder, PARAM_SITE, site);
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
