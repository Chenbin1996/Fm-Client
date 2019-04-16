package com.ruxuanwo.fm.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Result风格实体类，用于返回
 *
 * @author ruxuanwo
 */
public class Result<T> {

    private Integer resCode;
    private String resMsg;
    private T data;

    public Result() {
    }

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return this.resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toJson() {
        return this.data == null ? JSON.toJSONString(this) : this.toJson(SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);
    }

    public String toJson(SerializerFeature... features) {
        return features == null ? this.toJson() : JSON.toJSONString(this, features);
    }

    public String toString() {
        return "Result{resCode=" + this.resCode + ", resMsg='" + this.resMsg + '\'' + ", data=" + this.data + '}';
    }
}
