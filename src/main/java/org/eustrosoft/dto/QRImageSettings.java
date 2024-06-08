package org.eustrosoft.dto;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.Constants;
import org.eustrosoft.FileType;
import org.eustrosoft.WebParams;

import java.awt.Color;

import static org.eustrosoft.Constants.PARAM_BACKGROUND;
import static org.eustrosoft.Constants.PARAM_COLOR;
import static org.eustrosoft.Constants.PARAM_CORRECTION_LEVEL;
import static org.eustrosoft.Constants.PARAM_FILE_TYPE;
import static org.eustrosoft.Constants.PARAM_X;
import static org.eustrosoft.util.Util.getOrDefault;

public class QRImageSettings {
    private String baseUrl;
    private Integer x;
    private Color color;
    private Color backgroundColor;
    private FileType fileType;
    private ErrorCorrectionLevel errorCorrectionLevel;

    public static QRImageSettings extractFromRequest(HttpServletRequest req) {
        QRImageSettings settings = new QRImageSettings();
        settings.setX(getOrDefault(req, PARAM_X, Constants.Default.IMAGE_SIZE));
        settings.setFileType(getOrDefault(req, PARAM_FILE_TYPE, FileType.SVG));
        settings.setErrorCorrectionLevel(getOrDefault(req, PARAM_CORRECTION_LEVEL, ErrorCorrectionLevel.L));
        settings.setColor(getOrDefault(req, PARAM_COLOR, Constants.Default.COLOR));
        settings.setBackgroundColor(getOrDefault(req, PARAM_BACKGROUND, Constants.Default.BACKGROUND));
        settings.setBaseUrl(WebParams.getString(req, WebParams.BASIC_REDIRECT_URL));
        return settings;
    }

    public QRImageSettings() {
        this.x = 165;
        this.color = Color.BLACK;
        this.backgroundColor = Color.WHITE;
        this.fileType = FileType.SVG;
        this.errorCorrectionLevel = ErrorCorrectionLevel.M;
        this.baseUrl = Constants.Default.BASIC_URL;
    }

    public QRImageSettings(
            Integer x, Color color, Color backgroundColor,
            FileType fileType, ErrorCorrectionLevel errorCorrectionLevel,
            String baseUrl
    ) {
        this.x = x;
        this.color = color;
        this.backgroundColor = backgroundColor;
        this.fileType = fileType;
        this.errorCorrectionLevel = errorCorrectionLevel;
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }
}
