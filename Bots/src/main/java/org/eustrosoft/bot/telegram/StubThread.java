package org.eustrosoft.bot.telegram;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class StubThread implements Runnable {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final static File LOG_FILE = new File("logs.txt");

    @Override
    public void run() {
        initLogger();
        printNumbers();
    }

    private void initLogger() {
        FileHandler fh;
        try {
            if (!LOG_FILE.exists()) {
                LOG_FILE.createNewFile();
            }
            fh = new FileHandler(LOG_FILE.getAbsolutePath(), true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Logger was registered");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printNumbers() {
        for (int i = 0;; i++) {
            logger.log(Level.INFO, String.valueOf(i));
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
