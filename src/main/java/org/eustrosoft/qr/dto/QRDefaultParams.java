package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.qr.Constants;

import static org.eustrosoft.qr.Constants.PARAM_TEXT;
import static org.eustrosoft.qr.util.Util.getOrDefault;

public class QRDefaultParams implements QRDto {
    private String text = "";
    private QRImageSettings imageSettings;
    private String fileName = "qr";

    public static QRDefaultParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRDefaultParams(
                getOrDefault(request, PARAM_TEXT, "No value given"),
                imageSettings
        );
    }

    public QRDefaultParams(String text) {
        this.text = text;
        this.imageSettings = new QRImageSettings();
    }

    public QRDefaultParams(String text, QRImageSettings imageSettings) {
        this.text = text;
        this.imageSettings = imageSettings;
    }

    protected static StringBuilder setParam(StringBuilder builder, String param, String value) {
        return builder.append(param).append(Constants.Query.EQUALS).append(value);
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public QRImageSettings getImageSettings() {
        return this.imageSettings;
    }
}
