package org.eustrosoft.bot.telegram;

import org.eustrosoft.bot.telegram.abomination.AAbominationBot;

public class Launcher {

    // put token & qr request url with substitution
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException(
                    "Illegal arguments to start Telegram Bot application\n" +
                    "\tPass a ${token} and ${qrGenerateURL}\n" +
                    "\t- ${token} - your telegram bot token\n" +
                    "\t- ${qrGenerateURL} - your QRGenerator url to servlet, which will generate QR pictures\n" +
                    "\t\tFor example: [java -jar bots.jar NajqnHqh http://127.0.0.1/qrGenerator]\n"
            );
        }

        Thread botThread = new Thread(new AAbominationBot(args[0], args[1]));

        botThread.setDaemon(false); // optional
        botThread.start();

        System.out.println("App started");
    }
}
