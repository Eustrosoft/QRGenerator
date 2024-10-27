package org.eustrosoft.bot.telegram.enums;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.eustrosoft.bot.telegram.Constants;

import java.util.Arrays;

public enum QRType implements KeyboardEnum {
    TEXT,
    URL,
    OTHER;

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

    @Override
    public InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardButton[][] buttons = new InlineKeyboardButton[getKeyboard().length][1];
        for (int i = 0; i < getKeyboard().length; i++) {
            buttons[i][0] = new InlineKeyboardButton(getKeyboard()[i])
                    .callbackData(getKeyboard()[i]);
        }
        return new InlineKeyboardMarkup(buttons);

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
