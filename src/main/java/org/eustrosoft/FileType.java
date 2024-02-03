package org.eustrosoft;

public enum FileType {
    SVG("svg"),
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg");

    public final static String[] CONTENT_TYPES = {"image/svg+xml", "image/png", "image/jpg", "image/jpeg"};

    private String type;

    public static FileType of(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        FileType[] values = values();
        for (FileType fileType : values) {
            if (value.equalsIgnoreCase(fileType.type)) {
                return fileType;
            }
        }
        return null;
    }

    public static String getContentType(FileType value) {
        if (value == null) {
            return null;
        }
        return CONTENT_TYPES[value.ordinal()];
    }

    public String getType() {
        return type;
    }

    FileType(String type) {
        this.type = type;
    }
}
