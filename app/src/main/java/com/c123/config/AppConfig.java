package com.c123.config;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private static final Properties PROPS;

    static {
        try {
            PROPS = new Properties();
            PROPS.load(AppConfig.class.getClassLoader().getResourceAsStream("server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getProperty(String entry) {
        return PROPS.getProperty(entry);
    }

}
