package org.eustrosoft.bot.telegram;

public class Launcher {

    // put token & qr request url with substitution
    public static void main(String[] args) {
        Thread botThread = new AAbominationBot(args[0], args[1]);

        botThread.setDaemon(false); // optional
        botThread.start();

        System.out.println("App started");
    }
}
