package com.ruxuanwo.fm.client.constants;

/**
 * 此类很重要，对应配置文件中的属性，不可轻易修改
 *
 * @author ruxuanwo
 */
public final class FmConstants {
    public static final String FM_URL = "fm.url";
    public static final String FM_UPLOAD_ROOT_DIRECTORY = "fm.upload.rootDirectory";
    public static final String FM_MAX_UPLOAD_SIZE = "fm.max.uploadSize";
    public static final String FM_CONNECT_TIMEOUT = "fm.connect.timeout";
    public static final String FM_WRITE_TIMEOUT = "fm.write.timeout";
    public static final String FM_READ_TIMEOUT = "fm.read.timeout";
    public static final String FM_PING_INTERVAL = "fm.pingInterval";
    public static final String FM_POOL_MAX_IDLE_CONNECTIONS = "fm.pool.maxIdleConnections";
    public static final String FM_POOL_KEEP_ALIVE_DURATION = "fm.pool.keepAliveDuration";
    public static final String FM_RESOURCE_URI = "fm.resource.uri";
    public static final String FM_AGENT_URL = "fm.agentUrl";
    public static final String FM_PREFIX = "fm.prefix";


    public static final String FM_AUTH_URI = "fm.auth.uri";
    public static final String FM_AUTH_USER_NAME = "fm.auth.username";
    public static final String FM_AUTH_PD = "fm.auth.password";

    public static final String FM_AUTH_TOKEN_EXPIRES_TIME_MS = "fm.auth.token.expires.time.ms";
    public static final String FM_DOWNLOAD_URI = "fm.download.uri";

    /**
     * 文件存储自定义Public Namespace名称
     */
    public static final String FM_PUBLIC_NAMESPACE_NAME = "fm.publicNamespace.name";

    public static final String IS_ENABLED_APOLLO = "isEnabledApollo";
    public static final String FORWARD_SLASH = "/";

    private FmConstants() {
    }
}
