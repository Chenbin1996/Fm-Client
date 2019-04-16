package com.ruxuanwo.fm.client.config;


import com.ruxuanwo.fm.client.constants.FmConstants;
import com.ruxuanwo.fm.client.utils.ConfigUtil;

/**
 * 加载并读取本地配置文件
 *
 * @author ruxuanwo
 */
public class FmLocalConfig implements IFmConfig {
    private String resourceUri;
    private String downloadUri;
    private String prefix;
    private String url;
    private String uploadRootDirectory;
    private Integer maxUploadSize;
    private Integer connectTimeout;
    private Integer writeTimeout;
    private Integer readTimeout;
    private Integer pingInterval;
    private Integer poolMaxIdleConnections;
    private Integer poolKeepAliveDuration;
    private String authUri;
    private String authUserName;
    private String authPassword;
    private Long tokenExpiresTimeMS;
    private String agentUrl;

    private static class ConfigHolder {
        private static final IFmConfig CONFIG = new FmLocalConfig();
    }

    public static IFmConfig getConfig() {
        return ConfigHolder.CONFIG;
    }

    private FmLocalConfig() {
    }

    @Override
    public String getResourceUri() {
        return resourceUri == null ? (resourceUri = ConfigUtil.getConfig().get(FmConstants.FM_RESOURCE_URI)) : resourceUri;
    }

    @Override
    public String getDownloadUri() {
        return downloadUri == null ? (downloadUri = ConfigUtil.getConfig().get(FmConstants.FM_DOWNLOAD_URI)) : downloadUri;
    }

    @Override
    public String getPrefix() {
        return prefix == null ? (prefix = ConfigUtil.getConfig().get(FmConstants.FM_PREFIX)) : prefix;
    }

    @Override
    public String getUrl() {
        return url == null ? (url = ConfigUtil.getConfig().get(FmConstants.FM_URL)) : url;
    }

    @Override
    public String getUploadRootDirectory() {
        return uploadRootDirectory == null ? (uploadRootDirectory = ConfigUtil.getConfig().get(FmConstants.FM_UPLOAD_ROOT_DIRECTORY)) : uploadRootDirectory;
    }

    @Override
    public Integer getMaxUploadSize() {
        return maxUploadSize == null ? (maxUploadSize = ConfigUtil.getConfig().getInt(FmConstants.FM_MAX_UPLOAD_SIZE)) : maxUploadSize;
    }

    @Override
    public Integer getConnectTimeout() {
        return connectTimeout == null ? (connectTimeout = ConfigUtil.getConfig().getInt(FmConstants.FM_CONNECT_TIMEOUT)) : connectTimeout;
    }

    @Override
    public Integer getWriteTimeout() {
        return writeTimeout == null ? (writeTimeout = ConfigUtil.getConfig().getInt(FmConstants.FM_WRITE_TIMEOUT)) : writeTimeout;
    }

    @Override
    public Integer getReadTimeout() {
        return readTimeout == null ? (readTimeout = ConfigUtil.getConfig().getInt(FmConstants.FM_READ_TIMEOUT)) : readTimeout;
    }

    @Override
    public Integer getPingInterval() {
        return pingInterval == null ? (pingInterval = ConfigUtil.getConfig().getInt(FmConstants.FM_PING_INTERVAL)) : pingInterval;
    }

    @Override
    public Integer getPoolMaxIdleConnections() {
        return poolMaxIdleConnections == null ? (poolMaxIdleConnections = ConfigUtil.getConfig().getInt(FmConstants.FM_POOL_MAX_IDLE_CONNECTIONS)) : poolMaxIdleConnections;
    }

    @Override
    public Integer getPoolKeepAliveDuration() {
        return poolKeepAliveDuration == null ? (poolKeepAliveDuration = ConfigUtil.getConfig().getInt(FmConstants.FM_POOL_KEEP_ALIVE_DURATION)) : poolKeepAliveDuration;
    }

    @Override
    public Boolean isEnabledApollo() {
        return false;
    }

    @Override
    public String getAuthUri() {
        return authUri == null ? (authUri = ConfigUtil.getConfig().get(FmConstants.FM_AUTH_URI)) : authUri;
    }

    @Override
    public String getAuthUserName() {
        return authUserName == null ? (authUserName = ConfigUtil.getConfig().get(FmConstants.FM_AUTH_USER_NAME)) : authUserName;
    }

    @Override
    public String getAuthPassword() {
        return authPassword == null ? (authPassword = ConfigUtil.getConfig().get(FmConstants.FM_AUTH_PD)) : authPassword;
    }

    @Override
    public Long getTokenExpiresTimeMS() {
        //fm.auth.token.expires.time.ms
        return tokenExpiresTimeMS == null ? (tokenExpiresTimeMS = ConfigUtil.getConfig().getLong(FmConstants.FM_AUTH_TOKEN_EXPIRES_TIME_MS)) : tokenExpiresTimeMS;
    }

    @Override
    public String getAgentUrl() {
        return agentUrl == null ? (agentUrl = ConfigUtil.getConfig().get(FmConstants.FM_AGENT_URL)) : agentUrl;
    }
}
