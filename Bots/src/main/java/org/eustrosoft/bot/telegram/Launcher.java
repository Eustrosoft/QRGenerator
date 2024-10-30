package org.eustrosoft.bot.telegram;

import org.eustrosoft.bot.telegram.abomination.AAbominationBot;
import org.eustrosoft.bot.telegram.config.BotProperties;

public class Launcher {

    public static void main(String[] args) {
        System.out.println("Trying startup");

        BotProperties properties = null;
        try {
            properties = BotProperties.getInstance();
        } catch (Exception ex) {
            System.err.println("========================================");
            System.err.println("Failed to load properties file or insufficient properties, aborting");
            System.err.println("========================================");
            ex.printStackTrace();
            System.exit(1);
        }

        try {
            if (properties.getIsDemon()) {
                deamonize();
            }
            Thread thread = new Thread(new AAbominationBot());
            thread.start();
        } catch (Exception ex) {
            System.err.println("========================================");
            System.err.println("Failed to startup, aborting");
            System.err.println("========================================");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public static void deamonize() {
        System.out.close();
        System.err.close();
    }
}
