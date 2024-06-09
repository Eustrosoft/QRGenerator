package org.eustrosoft.qr.dto;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.qr.Constants;
import org.eustrosoft.qr.FileType;
import org.eustrosoft.qr.WebParams;

import java.awt.Color;

import static org.eustrosoft.qr.Constants.PARAM_BACKGROUND;
import static org.eustrosoft.qr.Constants.PARAM_COLOR;
import static org.eustrosoft.qr.Constants.PARAM_CORRECTION_LEVEL;
import static org.eustrosoft.qr.Constants.PARAM_FILE_TYPE;
import static org.eustrosoft.qr.Constants.PARAM_X;
import static org.eustrosoft.qr.util.Util.getOrDefaultEnum;
import static org.eustrosoft.qr.util.Util.getOrDefaultExtract;

public class QRImageSettings {
    private String baseUrl;
    private Integer x;
    private Integer maxQrSize;
    private Color color;
    private Color backgroundColor;
    private FileType fileType;
    private ErrorCorrectionLevel errorCorrectionLevel;

    public static QRImageSettings extractFromRequest(HttpServletRequest req) throws IllegalArgumentException {
        QRImageSettings settings = new QRImageSettings();
        try {
            settings.setMaxQrSize(
                    WebParams.getInteger(req, WebParams.DEFAULT_QR_MAX_SIZE, Constants.Default.IMAGE_MAX_SIZE)
            );
            settings.setX(
                    getOrDefaultExtract(
                            req, PARAM_X,
                            WebParams.getInteger(req, WebParams.DEFAULT_QR_SIZE, Constants.Default.IMAGE_SIZE),
                            Integer::parseInt
                    )
            );
            settings.setFileType(
                    getOrDefaultEnum(
                            req, PARAM_FILE_TYPE,
                            WebParams.getEnum(req, WebParams.DEFAULT_QR_FILE_TYPE, Constants.Default.FILE_TYPE)
                    )
            );
            settings.setErrorCorrectionLevel(
                    getOrDefaultEnum(
                            req, PARAM_CORRECTION_LEVEL,
                            WebParams.getEnum(req, WebParams.DEFAULT_QR_CORRECTION_LEVEL, Constants.Default.ERROR_CORRECTION)
                    )
            );
            settings.setColor(
                    getOrDefaultExtract(
                            req, PARAM_COLOR,
                            Color.decode(WebParams.getString(req, WebParams.DEFAULT_QR_COLOR, Constants.Default.COLOR.toString())),
                            Color::decode
                    )
            );
            settings.setBackgroundColor(
                    getOrDefaultExtract(
                            req, PARAM_BACKGROUND,
                            Color.decode(WebParams.getString(req, WebParams.DEFAULT_QR_BACKGROUND, Constants.Default.BACKGROUND.toString())),
                            Color::decode
                    )
            );
            settings.setBaseUrl(WebParams.getString(req, WebParams.BASIC_REDIRECT_URL, Constants.Default.BASIC_URL));
            return settings;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Passed illegal qr image settings argument");
        }
    }

    public QRImageSettings() {
        this.x = 165;
        this.maxQrSize = Constants.Default.IMAGE_MAX_SIZE;
        this.color = Constants.Default.COLOR;
        this.backgroundColor = Constants.Default.BACKGROUND;
        this.fileType = Constants.Default.FILE_TYPE;
        this.errorCorrectionLevel = Constants.Default.ERROR_CORRECTION;
        this.baseUrl = Constants.Default.BASIC_URL;
    }

    public QRImageSettings(
            Integer x, Integer maxQrSize, Color color, Color backgroundColor,
            FileType fileType, ErrorCorrectionLevel errorCorrectionLevel,
            String baseUrl
    ) {
        setX(x);
        setMaxQrSize(maxQrSize);
        setColor(color);
        setBackgroundColor(backgroundColor);
        setFileType(fileType);
        setErrorCorrectionLevel(errorCorrectionLevel);
        setBaseUrl(baseUrl);
    }

    public Integer getMaxQrSize() {
        return maxQrSize;
    }

    public void setMaxQrSize(Integer maxQrSize) {
        this.maxQrSize = maxQrSize;
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
        this.x = Math.min(x, getMaxQrSize());
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
