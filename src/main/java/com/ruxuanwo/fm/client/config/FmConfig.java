package com.ruxuanwo.fm.client.config;


import com.ruxuanwo.fm.client.utils.FileUtil;

/**
 * 获取配置类属性
 *
 * @author ruxuanwo
 */
public class FmConfig {
    private static final IFmConfig CONFIG;


    public static final String SEPARATOR = "/";

    private static Boolean isEnabledApollo;

    private static AuthTokenConfig authTokenConfig = null;

    static {
        CONFIG = initConfig();

    }

    private FmConfig() {
    }

    private static IFmConfig initConfig() {
        return FmLocalConfig.getConfig();
    }

    public static String getResourceUri() {
        return CONFIG.getResourceUri();
    }

    public static String getDownloadUri() {
        return CONFIG.getDownloadUri();
    }

    /**
     * 返回RESOURCE对应的URL，形如:http://127.0.0.1/api/resource/
     */
    public static String getResourceUrl() {
        return FmConfig.getFmUrl() + CONFIG.getResourceUri();
    }

    /**
     * DOWNLOAD对应的URL,形如:http://127.0.0.1/api/download/
     *
     * @return
     */
    public static String getDownloadUrl() {
        return FmConfig.getFmUrl() + CONFIG.getDownloadUri();
    }

    public static String getFmUrl() {
        return CONFIG.getUrl();
    }

    public static String getFmPrefix() {
        return CONFIG.getPrefix();
    }

    /**
     * 项目根目录下
     */
    public static String getRootDirectory() {
        String folderName = CONFIG.getUploadRootDirectory();
        return (folderName != null && folderName.trim().length() > 0) ? FileUtil.checkFolderName(folderName) : "";
    }

    /**
     * 远程服务器资源地址,加上项目根目录,形如:http://127.0.0.1/api/resource/TEST/
     */
    public static String getProjectResourceUrl() {
        return FmConfig.getResourceUrl() + getRootDirectory();
    }

    /**
     * 远程服务器下载地址,加上项目根目录，形如:http://127.0.0.1/api/download/TEST/
     */
    public static String getProjectDownloadUrl() {
        return FmConfig.getDownloadUrl() + FmConfig.getRootDirectory();
    }

    public static int getMaxUploadSize() {
        return CONFIG.getMaxUploadSize();
    }

    public static int getConnectTime() {
        return CONFIG.getConnectTimeout();
    }

    public static int getWriteTimeout() {
        return CONFIG.getWriteTimeout();
    }

    public static int getReadTimeout() {
        return CONFIG.getReadTimeout();
    }

    public static int getPingInterval() {
        return CONFIG.getPingInterval();
    }

    public static int getPoolMaxIdleConnections() {
        return CONFIG.getPoolMaxIdleConnections();
    }

    public static int getPoolKeepAliveDuration() {
        return CONFIG.getPoolKeepAliveDuration();
    }

    public static String getAuthUserName() {
        return CONFIG.getAuthUserName();
    }

    public static String getAuthPassword() {
        return CONFIG.getAuthPassword();
    }

    public static Long getTokenExpiresTimeMS() {
        return CONFIG.getTokenExpiresTimeMS();
    }

    public static AuthTokenConfig getAuthTokenConfig() {
        return authTokenConfig;
    }

    public static void setAuthTokenConfig(AuthTokenConfig authTokenConfig) {
        FmConfig.authTokenConfig = authTokenConfig;
    }

    public static String getAuthURL() {
        return CONFIG.getUrl() + CONFIG.getAuthUri();
    }

    public static String getAgentUrl() {
        return CONFIG.getAgentUrl();
    }
}
