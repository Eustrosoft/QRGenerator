package org.eustrosoft;

import java.awt.Color;

public class QRParams {
    private String text = "";
    private FileType fileType = FileType.SVG;
    private Integer x = 300;
    private Integer y = 300;

    private Color color = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    public QRParams(String text) {
        this.text = text;
    }

    public QRParams(String text, Color color,
                    FileType fileType, Integer x, Integer y
    ) {
        this.text = text;
        if (x != null) {
            this.x = x;
        }
        if (y != null) {
            this.y = y;
        }
        if (fileType != null) {
            this.fileType = fileType;
        }
        if (color != null) {
            this.color = color;
        }
    }

    public static QRParams fromStrings(String text, String color, String background,
                                       String type, String x, String y
    ) {
        String qrText = text == null ? "" : text;
        QRParams params = new QRParams(qrText);
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
            params.setX(Integer.parseInt(x));
        }
        if (y != null) {
            params.setY(Integer.parseInt(y));
        }
        return params;
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

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
