package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 读取配置文件工具类
 */
public class MessageUtils {

    private static final String file = "/i18n/message_zh_CN.properties";
    private static Properties properties = new Properties();

    private static void load() {
        try {
            InputStream in = MessageUtils.class.getResourceAsStream(file);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key, Object... values) {
        if (properties.isEmpty()) {
            load();
        }
        return MessageFormat.format(properties.getProperty(key), values);
    }

    public static String getValue(String key) {
        if (properties.isEmpty()) {
            load();
        }
        return properties.getProperty(key);
    }

}
