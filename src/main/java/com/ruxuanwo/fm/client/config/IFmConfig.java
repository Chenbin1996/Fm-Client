package com.ruxuanwo.fm.client.config;

/**
 * 配置读取类接口
 *
 * @author ruxuanwo
 */
public interface IFmConfig {
    /**
     * 远程服务器resource对应的URI
     *
     * @return
     */
    String getResourceUri();

    /**
     * 远程服务器下载对应的URI
     *
     * @return
     */
    String getDownloadUri();

    /**
     * 远程服务之前缀
     *
     * @return
     */
    String getPrefix();

    /**
     * 远程服务器资源地址，非80端口，需要加上端口,形如:127.0.0.1:8080
     *
     * @return
     */
    String getUrl();

    /**
     * 项目文件上传默认根目录不存在会自动创建,如果不指定文件则会上传到服务器根目录下
     *
     * @return
     */
    String getUploadRootDirectory();

    /**
     * 以字节为单位的最大上传文件的大小(单位:B),1M = 1024KB = 1024*1024B=1048576B
     *
     * @return
     */
    Integer getMaxUploadSize();

    /**
     * 连接超时时间(秒)
     *
     * @return
     */
    Integer getConnectTimeout();

    /**
     * 写入超时时间(秒)
     *
     * @return
     */
    Integer getWriteTimeout();

    /**
     * 读取超时时间(秒)
     *
     * @return
     */
    Integer getReadTimeout();

    /**
     * websocket轮训间隔(单位:秒)
     *
     * @return
     */
    Integer getPingInterval();

    /**
     * 连接池websocket轮训间隔(单位:秒)
     *
     * @return
     */
    Integer getPoolMaxIdleConnections();

    /**
     * 连接池中socket的keepAlive时间(单位:秒)
     *
     * @return
     */
    Integer getPoolKeepAliveDuration();

    /**
     * 是否启用Apollo配置
     *
     * @return
     */
    Boolean isEnabledApollo();

    /**
     * 权限认证url
     *
     * @return
     */
    String getAuthUri();

    /**
     * 用户名
     *
     * @return
     */
    String getAuthUserName();

    /**
     * 用户密码
     *
     * @return
     */
    String getAuthPassword();

    /**
     * 获取过期时间，单位：毫秒
     *
     * @throws
     * @Title: getTokenExpiresTimeMS
     * @Description: 获取过期时间，单位：毫秒
     * @param: @return
     * @return: Long
     */
    Long getTokenExpiresTimeMS();

    /**
     * 远程服务器读取地址，用于前台显示及下载,用于读写分离
     *
     * @return
     */
    String getAgentUrl();
}
