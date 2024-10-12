package org.eustrosoft.bot.telegram;

import org.eustrosoft.bot.telegram.enums.QRType;

public final class Constants {

    public static final String URL_GENERATE_FORMAT = "%s?%s=%s&type=%s&color=%%23000000&background=%%23ffffff&x=300&fileType=JPEG&correctionLevel=M";

    public static final QRType[] ALLOWED_TYPES = new QRType[]{QRType.TEXT, QRType.URL};

    public final static class Messages {
        public static final String GO_TO_OUT_SERVICE_TEXT = "For this type use out service! We are located here: https://qrgen.qxyz.ru/";
        public static final String CHOOSE_QR_TYPE_MESSAGE = "Choose a QR Type to generate: ";
        public static final String WRITE_QR_TEXT = "Write QR code text with type ";
    }

    public final static class Commands {
        public static final String TYPES = "/types";
        public static final String START = "/start";
    }

    private Constants() {}
}
