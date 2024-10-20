package org.eustrosoft.bot.telegram.abomination;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendPhoto;
import org.eustrosoft.bot.telegram.BasicTelegramBot;
import org.eustrosoft.bot.telegram.Constants;
import org.eustrosoft.bot.telegram.enums.QRType;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eustrosoft.bot.telegram.Constants.Messages.GO_TO_OUT_SERVICE_TEXT;

public class AAbominationBot extends BasicTelegramBot implements Runnable {
    private final static Map<Long, QRType> currentUserTypes = new HashMap<>();

    private TelegramBot telegramBot;
    private final String token;
    private final String qrGenerateUrl;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void run() {
        initBot();
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

            logger.log(Level.INFO, "Bot successfully started");
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

    @Override
    public TelegramBot getTelegramBot() {
        return this.telegramBot;
    }

    public AAbominationBot(String token, String qrGenerateUrl) {
        this.token = token;
        this.qrGenerateUrl = qrGenerateUrl;
    }

    public class AbominationBotListener implements UpdatesListener {

        @Override
        public int process(List<Update> list) {
            int lastProcessedId = 1;
            for (Update update : list) {
                processUpdate(update);
                lastProcessedId = update.updateId();
            }
            return lastProcessedId;
        }

        public void processUpdate(Update update) {
            if (update.message() != null) {
                processMessage(update);
            } else if (update.callbackQuery() != null) {
                processCallback(update);
            }
        }

        private void processCallback(Update update) {
            CallbackQuery callbackQuery = update.callbackQuery();
            if (callbackQuery == null) {
                logger.log(Level.WARNING, "Callback query is null. Skipping.");
                return;
            }
            String data = callbackQuery.data();
            Long userId = callbackQuery.from().id();

            QRType type = QRType.valueOf(data);
            if (!QRType.isAllowedType(type)) {
                sendMessage(update, GO_TO_OUT_SERVICE_TEXT);
                responseToButtons.remove(userId);
                sendInlineButtons(update, Constants.Messages.CHOOSE_QR_TYPE_MESSAGE, QRType.TEXT.getInlineKeyboard());
                return;
            }
            sendMessage(
                    update,
                    Constants.Messages.WRITE_QR_TEXT + type.name()
            );
            currentUserTypes.put(userId, type);
            responseToButtons.remove(userId);
        }

        private void processMessage(Update update) {
            Message message = update.message();
            if (message == null) {
                logger.log(Level.WARNING, "Message is null. Skipping.");
                return;
            }
            String messageText = message.text();
            Chat chat = message.chat();
            logger.log(
                    Level.INFO,
                    String.format(
                            "Processing query: Username: %s, FirstName: %s, LastName: %s; Message: %s",
                            chat.username(), chat.firstName(), chat.lastName(), messageText
                    )
            );
            if (messageText.equals(Constants.Commands.START)) {
                sendStartMessage(update);
                sendInlineButtons(update, Constants.Messages.CHOOSE_QR_TYPE_MESSAGE, QRType.TEXT.getInlineKeyboard());
                return;
            }
            if (messageText.equals(Constants.Commands.TYPES)) {
                sendInlineButtons(update, Constants.Messages.CHOOSE_QR_TYPE_MESSAGE, QRType.TEXT.getInlineKeyboard());
                return;
            }
            if (responseToButtons.containsKey(update.message().from().id()) && QRType.has(messageText)) {
                QRType type = QRType.valueOf(messageText);
                if (!QRType.isAllowedType(type)) {
                    sendMessage(update, GO_TO_OUT_SERVICE_TEXT);
                    responseToButtons.remove(update.message().from().id());
                    sendInlineButtons(update, Constants.Messages.CHOOSE_QR_TYPE_MESSAGE, QRType.TEXT.getInlineKeyboard());
                    return;
                }
                sendMessage(
                        update,
                        Constants.Messages.WRITE_QR_TEXT + type.name()
                );
                currentUserTypes.put(update.message().from().id(), type);
                responseToButtons.remove(update.message().from().id());
                return;
            }
            try {
                String textToImage = URLEncoder.encode(messageText, StandardCharsets.UTF_8.toString());
                QRType qrType = QRType.TEXT;
                if (currentUserTypes.containsKey(update.message().from().id())) {
                    qrType = currentUserTypes.get(update.message().from().id());
                }

                String req = String.format(
                        Constants.URL_GENERATE_FORMAT,
                        qrGenerateUrl,
                        qrType.name().toLowerCase(),
                        textToImage,
                        qrType.name()
                );
                URI uri = URI.create(req);
                logger.log(Level.INFO, "Request: " + uri);
                try (InputStream stream = uri.toURL().openStream()) {
                    SendPhoto photo = new SendPhoto(update.message().chat().id(), streamToBytes(stream))
                            .parseMode(ParseMode.HTML)
                            .caption(URLDecoder.decode(textToImage, StandardCharsets.UTF_8.toString()))
                            .allowSendingWithoutReply(true)
                            .disableNotification(true);

                    telegramBot.execute(photo);
                }
                sendInlineButtons(update, Constants.Messages.CHOOSE_QR_TYPE_MESSAGE, QRType.TEXT.getInlineKeyboard());
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.log(Level.INFO, ex.getLocalizedMessage());
            } finally {
                currentUserTypes.remove(update.message().from().id());
            }
        }

        private byte[] streamToBytes(final InputStream stream) {
            try {
                if (stream == null) {
                    return new byte[0];
                }
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = stream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                return buffer.toByteArray();
            } catch (Exception exception) {
                exception.printStackTrace();
                return new byte[0];
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
