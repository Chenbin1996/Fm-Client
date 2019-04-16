package com.ruxuanwo.fm.client.config;

import java.util.Date;

/**
 * 登录鉴权，文件资源服务器需要登录token
 *
 * @author ruxuanwo
 */
public class AuthTokenConfig {

    private String token;

    private Date createTime;

    /**
     * @Fields expiresTime : FileManager默认过期时间是24小时，系统在23小时后刷新Token
     */
    private Date expiresTime;

    //private boolean expires; //是否过期


    /**
     * 返回Token是否过期
     * @Title: isExpires
     * @Description: 返回Token是否过期
     * @param: @return
     * @return: boolean
     * @throws
     */
    public boolean isExpires() {
        Date now = new Date();
        return now.after(expiresTime);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }
}
