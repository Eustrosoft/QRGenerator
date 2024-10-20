package org.eustrosoft.bot.telegram.enums;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public interface KeyboardEnum {

    String[] getKeyboard();
    InlineKeyboardMarkup getInlineKeyboard();
}
