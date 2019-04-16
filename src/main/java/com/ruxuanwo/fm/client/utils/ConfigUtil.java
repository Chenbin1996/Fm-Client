package com.ruxuanwo.fm.client.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 读取配置文件操作类
 *
 * @author ruxuanwo
 */
public class ConfigUtil {
    private static final String CONFIG_FILENAME = "fm-config.properties";

    private static final String DEFAULT_CHAR_SET = "UTF-8";
    private static final Properties PROP = new Properties();

    private static class ConfigHolder {
        private static final ConfigUtil CONFIG = new ConfigUtil();
    }

    static {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME), DEFAULT_CHAR_SET))) {
            PROP.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Can not find config file：" + CONFIG_FILENAME, e);
        }
    }

    public static ConfigUtil getConfig() {
        return ConfigHolder.CONFIG;
    }

    public String get(String key) {
        return (String) PROP.get(key);
    }

    public Integer getInt(String key) {
        return Integer.parseInt((String) PROP.get(key));
    }

    private ConfigUtil() {
    }

    public Long getLong(String key) {
        return Long.parseLong(PROP.getProperty(key));
    }
}
