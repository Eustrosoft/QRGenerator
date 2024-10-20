package org.eustrosoft.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicTelegramBot {

    protected static final Map<Long, Boolean> responseToButtons = new HashMap<>();

    public abstract TelegramBot getTelegramBot();

    protected void sendStartMessage(Update update) {
        sendMessage(update, "Write a message to convert it into QR code");
    }

    protected void sendMessage(Update update, String message) {
        SendMessage request = new SendMessage(tryGetChatId(update), message)
                .parseMode(ParseMode.HTML)
                .allowSendingWithoutReply(true)
                .disableNotification(true);

        getTelegramBot().execute(request);
    }

    protected void sendButtons(Update update, String message, String[] keyboard) {
        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard)
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);

        Long chatId = tryGetChatId(update);
        SendMessage keyboardMessage = new SendMessage(chatId, message)
                .parseMode(ParseMode.HTML)
                .allowSendingWithoutReply(true)
                .disableNotification(true)
                .replyMarkup(replyKeyboardMarkup);

        getTelegramBot().execute(keyboardMessage);

        responseToButtons.putIfAbsent(chatId, true);
    }

    protected void sendInlineButtons(Update update, String message, InlineKeyboardMarkup inlineKeyboard) {
        Long chatId = tryGetChatId(update);
        SendMessage keyboardMessage = new SendMessage(chatId, message)
                .parseMode(ParseMode.HTML)
                .allowSendingWithoutReply(true)
                .disableNotification(true)
                .replyMarkup(inlineKeyboard);

        getTelegramBot().execute(keyboardMessage);

        responseToButtons.putIfAbsent(chatId, true);
    }

    protected Long tryGetChatId(Update update) {
        return update.message() != null
                ? update.message().chat().id()
                : update.callbackQuery().maybeInaccessibleMessage().chat().id();
    }
}