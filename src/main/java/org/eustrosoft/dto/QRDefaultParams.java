package org.eustrosoft.dto;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.eustrosoft.FileType;

import java.awt.Color;

public class QRDefaultParams implements QRDto {
    public static final Integer MAX_TEXT_SIZE = 1900;

    private String text = "";
    private FileType fileType = FileType.SVG;
    private Integer x = 300;
    private ErrorCorrectionLevel correctionLevel = ErrorCorrectionLevel.L;

    private Color color = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    public QRDefaultParams(String text) {
        this.text = text;
    }

    public QRDefaultParams(String text, Color color,
                           FileType fileType, Integer x
    ) {
        this.text = text;
        if (x != null) {
            this.x = x;
        }
        if (fileType != null) {
            this.fileType = fileType;
        }
        if (color != null) {
            this.color = color;
        }
    }

    public QRDefaultParams(String text, FileType fileType, Integer x,
                           ErrorCorrectionLevel correctionLevel, Color color, Color backgroundColor) {
        this.text = text;
        this.fileType = fileType;
        this.x = x;
        this.correctionLevel = correctionLevel;
        this.color = color;
        this.backgroundColor = backgroundColor;
    }

    public static QRDto fromStrings(String text, String color, String background,
                                              String type, String x, String correctionLevel
    ) {
        String qrText = getMaxStringForGenerate(text);
        QRDefaultParams params = new QRDefaultParams(qrText);
        if (color != null) {
            params.setColor(Color.decode(color));
        }
        if (background != null) {
            params.setBackgroundColor(Color.decode(background));
        }
        if (type != null) {
            params.setFileType(FileType.of(type));
        }
        if (x != null) {
            params.setX(x);
        }
        if (correctionLevel != null) {
            params.setCorrectionLevel(correctionLevel);
        }
        return params;
    }

    public ErrorCorrectionLevel getCorrectionLevel() {
        return correctionLevel;
    }

    public void setCorrectionLevel(String correctionLevel) {
        try {
            this.correctionLevel = ErrorCorrectionLevel.valueOf(correctionLevel);
        } catch (Exception ex) {
            this.correctionLevel = ErrorCorrectionLevel.L;
        }
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Integer getX() {
        return x;
    }

    public void setX(String x) {
        this.x = getValueForDimension(x);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private Integer getValueForDimension(String str) {
        try {
            int value = Integer.parseInt(str);
            if (value <= 50) {
                return 50;
            }
            return Math.min(value, 2048);
        } catch (Exception ex) {
            return 300;
        }
    }

    private static String getMaxStringForGenerate(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        int length = str.length();
        if (length > MAX_TEXT_SIZE) {
            return str.substring(0, MAX_TEXT_SIZE);
        }
        return str;
    }
}
