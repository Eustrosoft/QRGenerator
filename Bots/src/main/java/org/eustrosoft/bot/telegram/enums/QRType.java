package org.eustrosoft.bot.telegram.enums;

import org.eustrosoft.bot.telegram.Constants;

import java.util.Arrays;

public enum QRType implements KeyboardEnum {
    TEXT,
    URL,
    PHONE,
    SMS,
    EMAIL,
    CONTACT,
    WIFI,
    LOCATION;

    public static boolean isAllowedType(QRType type) {
        if (type == null) {
            return false;
        }
        return Arrays.asList(Constants.ALLOWED_TYPES).contains(type);
    }

    @Override
    public String[] getKeyboard() {
        QRType[] values = values();
        String[] types = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            types[i] = values[i].name();
        }
        return types;
    }

    public static boolean has(String val) {
        try {
            valueOf(val);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
