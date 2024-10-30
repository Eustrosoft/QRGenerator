package org.eustrosoft.bot.telegram.config;

import java.util.Properties;

public class BotProperties {
    public final static String PROPERTIES_FILE_NAME = "resources/application.properties";

    public final static String PROPERTIES_BOT_TOKEN = "bot.token";
    public final static String PROPERTIES_QXYZ_URL = "qxyz.url";
    public final static String PROPERTIES_IS_DEMON = "demon";

    private static BotProperties instance;
    private Properties properties;

    public static BotProperties getInstance() throws Exception {
        synchronized (BotProperties.class) {
            if (instance == null) {
                instance = new BotProperties();
                return instance;
            }
            return instance;
        }
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void reloadProperties() throws Exception {
        Properties prop = new Properties();
        prop.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
        this.properties = prop;
        validateProperties();
    }

    private void validateProperties() throws Exception {
        try {
            Object token = properties.get(PROPERTIES_BOT_TOKEN);
            String tokenStr = token.toString();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Bot token is invalid or empty");
        }
        try {
            Object qxyzUrl = properties.get(PROPERTIES_QXYZ_URL);
            String qxyzUrlStr = qxyzUrl.toString();
        } catch (Exception ex) {
            throw new IllegalArgumentException("QXYZ url is invalid or empty");
        }
        try {
            Object demon = properties.get(PROPERTIES_IS_DEMON);
            Boolean demonBool = Boolean.valueOf(demon.toString());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Demon property is invalid or empty");
        }
    }

    public String getBotToken() {
        return properties.get(PROPERTIES_BOT_TOKEN).toString();
    }

    public String getQxyzUrl() {
        return properties.get(PROPERTIES_QXYZ_URL).toString();
    }

    public Boolean getIsDemon() {
        return Boolean.valueOf(properties.get(PROPERTIES_QXYZ_URL).toString());
    }

    private BotProperties() throws Exception {
        reloadProperties();
    }
}
