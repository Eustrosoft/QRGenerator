package org.eustrosoft.dto;

import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.Constants;

import static org.eustrosoft.Constants.PARAM_TEXT;
import static org.eustrosoft.util.Util.getOrDefault;

public class QRDefaultParams implements QRDto {
    private String text = "";
    private QRImageSettings imageSettings;

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

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public QRImageSettings getImageSettings() {
        return this.imageSettings;
    }
}
