package org.eustrosoft.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AAbominationBot extends Thread {

    private TelegramBot telegramBot;
    private final String token;
    private final String qrGenerateUrl;

    public AAbominationBot(String token, String qrGenerateUrl) {
        this.token = token;
        this.qrGenerateUrl = qrGenerateUrl;
    }

    public void initBot() {
        try {
            telegramBot = new TelegramBot.Builder(token).build();

            telegramBot.setUpdatesListener(new AbominationBotListener(), e -> {
                if (e.response() != null) {
                    e.response().errorCode();
                    e.response().description();
                } else {
                    e.printStackTrace();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void shutdownBot() {
        if (telegramBot != null) {
            try {
                telegramBot.shutdown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendMessage(Update update, String message) {
        SendMessage request = new SendMessage(update.message().chat().id(), message)
                .parseMode(ParseMode.HTML)
                .allowSendingWithoutReply(true)
                .disableNotification(true);

        telegramBot.execute(request);
    }

    private void sendStartMessage(Update update) {
        sendMessage(update, "Write a message to convert it into QR code");
    }

    public class AbominationBotListener implements UpdatesListener {

        @Override
        public int process(List<Update> list) {
            int lastProcessedId = 1;
            for (Update update : list) {
                processMessage(update);
                lastProcessedId = update.updateId();
            }
            return lastProcessedId;
        }

        public void processMessage(Update update) {
            Message message = update.message();
            String messageText = message.text();
            if ("/start".equals(messageText)) {
                sendStartMessage(update);
                return;
            }
            try {
                String textToImage = URLEncoder.encode(messageText, String.valueOf(StandardCharsets.UTF_8));
                String req = String.format(qrGenerateUrl, textToImage);
                URI uri = URI.create(req);

                SendPhoto photo = new SendPhoto(update.message().chat().id(), streamToString(uri.toURL().openStream()))
                        .parseMode(ParseMode.HTML)
                        .caption(messageText)
                        .allowSendingWithoutReply(true)
                        .disableNotification(true);

                telegramBot.execute(photo);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public String streamToString(final InputStream stream) {
            try {
                if (stream == null) {
                    return "";
                }
                int bufferSize = 1024;
                char[] buffer = new char[bufferSize];
                StringBuilder out = new StringBuilder();
                try (Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                    for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
                        out.append(buffer, 0, numRead);
                    }
                }
                return out.toString();
            } catch (Exception exception) {
                exception.printStackTrace();
                return "";
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

    }
}
