package org.eustrosoft.qr;

import java.awt.Color;

public class Constants {

    public static final String EMPTY = "";

    public static final String PARAM_Q = "q";
    public static final String PARAM_P = "p";
    public static final String PARAM_D = "d";
    public static final String PARAM_CMD = "cmd";
    public static final String PARAM_SITE = "site";

    public static final String PARAM_URL = "url";
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_SUBJECT = "subject";
    public static final String PARAM_FIRST_NAME = "firstName";
    public static final String PARAM_LAST_NAME = "lastName";
    public static final String PARAM_ORGANIZATION = "organization";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_MOBILE_PHONE = "mobilePhone";
    public static final String PARAM_FAX = "fax";
    public static final String PARAM_STREET = "street";
    public static final String PARAM_CITY = "city";
    public static final String PARAM_REGION = "region";
    public static final String PARAM_POSTCODE = "postcode";
    public static final String PARAM_COUNTRY = "country";
    public static final String PARAM_SSID = "ssid";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_ENCRYPTION = "encryption";
    public static final String PARAM_LATITUDE = "latitude";
    public static final String PARAM_LONGITUDE = "longitude";
    public static final String PARAM_DISTANCE = "distance";
    public static final String PARAM_TYPE = "type";

    public static final String PARAM_TEXT = "text";
    public static final String PARAM_COLOR = "color";
    public static final String PARAM_BACKGROUND = "background";
    public static final String PARAM_FILE_TYPE = "fileType";
    public static final String PARAM_X = "x";
    public static final String PARAM_CORRECTION_LEVEL = "correctionLevel";

    public static final String FORMAT_LONGITUDE = "${LONGITUDE}";
    public static final String FORMAT_LATITUDE = "${LATITUDE}";
    public static final String FORMAT_DISTANCE = "${DISTANCE}";

    // todo: move to web.xml later and use it
    public static final Integer MAX_QR_TEXT_SIZE = 1900;

    public static class Default {
        public static final Integer IMAGE_SIZE = 165;
        public static final String BASIC_URL = "http://qr.qxyz.ru";
        public static final Color COLOR = Color.BLACK;
        public static final Color BACKGROUND = Color.WHITE;

        public static final String MAP_URL_FORMAT = "https://yandex.ru/maps?ll=${LATITUDE}%2C${LONGITUDE}&z=${DISTANCE}";

        public static final Float LATITUDE = 37.617700f;
        public static final Float LONGITUDE = 55.755863f;
        public static final Integer DISTANCE = 15;
    }

    public static class Query {
        public static final String AND = "&";
        public static final String EQUALS = "=";
        public static final String QUESTION = "?";
    }

    public enum QRType {
        TEXT, URL, PHONE, SMS,
        EMAIL, CONTACT, WIFI, LOCATION;

        public static QRType fromString(String value) {
            if (value == null || value.isEmpty()) {
                return null;
            }
            String valUpper = value.toUpperCase();
            try {
                return QRType.valueOf(valUpper);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid QR Type: " + valUpper);
            }
        }
    }
}
