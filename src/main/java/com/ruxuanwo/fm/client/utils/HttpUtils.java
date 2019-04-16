package com.ruxuanwo.fm.client.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruxuanwo.fm.client.config.AuthTokenConfig;
import com.ruxuanwo.fm.client.config.FmConfig;
import com.ruxuanwo.fm.client.constants.FmConstants;
import lombok.Builder;
import lombok.ToString;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ruxuanwo.fm.client.constants.MediaType.*;


/**
 * 封装okhttp3网络请求操作类
 *
 * @author ruxuanwo
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_PATCH = "PATCH";

    private static final String UTF8 = "UTF-8";
    private static final String DEFAULT_CHARSET = UTF8;
    private static final String DEFAULT_METHOD = METHOD_GET;
    private static final String DEFAULT_MEDIA_TYPE = APPLICATION_JSON;

    private static OkHttpClient client;

    static {
        initHttpClient();
    }

    public static void initHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (FmConfig.getConnectTime() > 0) {
            builder.connectTimeout(FmConfig.getConnectTime(), TimeUnit.SECONDS);
        }
        if (FmConfig.getWriteTimeout() > 0) {
            builder.writeTimeout(FmConfig.getWriteTimeout(), TimeUnit.SECONDS);
        }
        if (FmConfig.getReadTimeout() > 0) {
            builder.readTimeout(FmConfig.getReadTimeout(), TimeUnit.SECONDS);
        }
        if (FmConfig.getPingInterval() > 0) {
            builder.pingInterval(FmConfig.getPingInterval(), TimeUnit.SECONDS);
        }
        if (FmConfig.getPoolMaxIdleConnections() > 0 && FmConfig.getPoolKeepAliveDuration() > 0) {
            builder.connectionPool(new ConnectionPool(FmConfig.getPoolMaxIdleConnections(), FmConfig.getPoolKeepAliveDuration(), TimeUnit.SECONDS));
        }
        client = builder.build();
        //初始化根目录
        String rootDirectory = FmConfig.getRootDirectory();
        if (!StringUtils.isBlank(rootDirectory) && !
                FmConstants.FORWARD_SLASH.equals(rootDirectory)) {
            postForm(FmConfig.getProjectResourceUrl());
        }
    }

    public static void init() {
        //不做任何事，只是方便静态代码块加载
    }

    private HttpUtils() {
    }


    /**
     * GET请求
     *
     * @param url URL地址
     * @return
     */
    public static String get(String url) {
        return executeToStr(OkHttp.builder().url(url).build());
    }

    /**
     * GET请求
     *
     * @param url URL地址
     * @return
     */
    public static String get(String url, String charset) {
        return executeToStr(OkHttp.builder().url(url).responseCharset(charset).build());
    }

    /**
     * 带查询参数的GET查询
     *
     * @param url      URL地址
     * @param queryMap 查询参数
     * @return
     */
    public static String get(String url, Map<String, String> queryMap) {
        return executeToStr(OkHttp.builder().url(url).queryMap(queryMap).build());
    }

    /**
     * 带查询参数的GET查询
     *
     * @param url      URL地址
     * @param queryMap 查询参数
     * @return
     */
    public static String get(String url, Map<String, String> queryMap, String charset) {
        return executeToStr(OkHttp.builder().url(url).queryMap(queryMap).responseCharset(charset).build());
    }

    /**
     * METHOD_POST
     * application/json
     *
     * @param url
     * @param obj
     * @return
     */
    public static String postJson(String url, Object obj) {
        return post(url, JSON.toJSONString(obj).getBytes(Charset.forName(DEFAULT_CHARSET)), APPLICATION_JSON, null);
    }

    /**
     * METHOD_POST
     * application/x-www-form-urlencoded
     *
     * @param url
     * @return
     */
    public static String postForm(String url) {
        String data = "";
        return post(url, data, APPLICATION_FORM_URLENCODED, null);
    }

    /**
     * METHOD_POST
     * application/x-www-form-urlencoded
     *
     * @param url
     * @param formMap
     * @return
     */
    public static String postForm(String url, Map<String, String> formMap) {
        String data = "";
        if (CollectionUtils.isNotEmpty(formMap)) {
            data = formMap.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors
                    .joining("&"));
        }
        return post(url, data, APPLICATION_FORM_URLENCODED, null);
    }


    /**
     * DELETE请求
     *
     * @param url URL地址
     * @return
     */
    public static String delete(String url) {
        return executeToStr(OkHttp.builder().url(url).data(null).method(METHOD_DELETE).build());
    }


    public static String upload(String url, byte[] data) {
        return post(url, data, APPLICATION_OCTET_STREAM, null);
    }

    public static byte[] download(String url) {
        return executeToByte(OkHttp.builder().url(url).build());
    }

    public static byte[] download(String url, Map<String, String> queryMap) {
        return executeToByte(OkHttp.builder().url(url).queryMap(queryMap).build());
    }

    private static String post(String url, byte[] data, String mediaType, String charset) {
        return executeToStr(OkHttp.builder().url(url).method(METHOD_POST).data(data).mediaType(mediaType).responseCharset(charset).build());
    }

    private static String post(String url, String data, String mediaType, String charset) {
        charset = (charset == null) ? DEFAULT_CHARSET : charset;
        return executeToStr(OkHttp.builder().url(url).method(METHOD_POST).data(data == null ? null : data.getBytes(Charset.forName(charset))).mediaType(mediaType).responseCharset(charset).build());
    }

    public static String getAutToken() {
        AuthTokenConfig tokenConfig = FmConfig.getAuthTokenConfig();
        if (tokenConfig != null) {
            if (!tokenConfig.isExpires()) {
                return tokenConfig.getToken();
            } else {
                String token = auth(FmConfig.getAuthUserName(), FmConfig.getAuthPassword());
                AuthTokenConfig tokenConfigNew = new AuthTokenConfig();
                Date now = new Date();
                tokenConfigNew.setCreateTime(now);
                tokenConfigNew.setExpiresTime(new Date(now.getTime() + FmConfig.getTokenExpiresTimeMS()));
                tokenConfigNew.setToken(token);
                FmConfig.setAuthTokenConfig(tokenConfigNew);
                return token;
            }
        } else {
            String token = auth(FmConfig.getAuthUserName(), FmConfig.getAuthPassword());
            AuthTokenConfig tokenConfigNew = new AuthTokenConfig();
            Date now = new Date();
            tokenConfigNew.setCreateTime(now);
            tokenConfigNew.setExpiresTime(new Date(now.getTime() + FmConfig.getTokenExpiresTimeMS()));
            tokenConfigNew.setToken(token);
            FmConfig.setAuthTokenConfig(tokenConfigNew);
            return token;
        }
    }

    /**
     * 鉴权方法，返回Token
     *
     * @throws
     * @Title: auth
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @return
     * @return: String
     */
    public static String auth(String username, String password) {

        OkHttp tmpHttp = null;
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            String jsonString = json.toJSONString();
            tmpHttp = OkHttp.builder().url(FmConfig.getAuthURL()).method("POST").data(jsonString.getBytes("UTF-8"))
                    .build();
            Request.Builder builder = new Request.Builder();
            builder.url(tmpHttp.url);

            if (CollectionUtils.isNotEmpty(tmpHttp.headerMap)) {
                tmpHttp.headerMap.forEach(builder::addHeader);
            }
            String method = tmpHttp.method.toUpperCase();
            String mediaType = String.format("%s;charset=%s", tmpHttp.mediaType, tmpHttp.requestCharset);

            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), tmpHttp.data);
            tmpHttp.data = null;
            builder.method(method, requestBody);
            byte[] bytes;

            LOGGER.info("Auth Beginning to connect the URL:{}  ,request method:{}", tmpHttp.url, tmpHttp.method);
            OkHttpClient.Builder tmpBuilder = new OkHttpClient.Builder();
            if (FmConfig.getConnectTime() > 0) {
                tmpBuilder.connectTimeout(FmConfig.getConnectTime(), TimeUnit.SECONDS);
            }
            if (FmConfig.getWriteTimeout() > 0) {
                tmpBuilder.writeTimeout(FmConfig.getWriteTimeout(), TimeUnit.SECONDS);
            }
            if (FmConfig.getReadTimeout() > 0) {
                tmpBuilder.readTimeout(FmConfig.getReadTimeout(), TimeUnit.SECONDS);
            }
            if (FmConfig.getPingInterval() > 0) {
                tmpBuilder.pingInterval(FmConfig.getPingInterval(), TimeUnit.SECONDS);
            }
            if (FmConfig.getPoolMaxIdleConnections() > 0 && FmConfig.getPoolKeepAliveDuration() > 0) {
                tmpBuilder.connectionPool(new ConnectionPool(FmConfig.getPoolMaxIdleConnections(),
                        FmConfig.getPoolKeepAliveDuration(), TimeUnit.SECONDS));
            }
            OkHttpClient tmpClient = tmpBuilder.build();

            Response response = tmpClient.newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Auth Unexpected code " + response);
            }
            bytes = response.body().bytes();
            String token = "Bearer " + new String(bytes, Charset.forName(DEFAULT_CHARSET));
            LOGGER.info("FmClient auth token:{}", token);
            return token;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("FmClient auth UnsupportedEncodingException:", e);
            return "";
        } catch (Exception e) {
            LOGGER.error("FmClient auth Exception:", e);
            return "";
        }
    }


    /**
     * 通用执行方法,返回字节数组
     */
    private static byte[] executeToByte(OkHttp okHttp) {
        if (isBlank(okHttp.requestCharset)) {
            okHttp.requestCharset = DEFAULT_CHARSET;
        }
        if (isBlank(okHttp.responseCharset)) {
            okHttp.responseCharset = DEFAULT_CHARSET;
        }
        if (isBlank(okHttp.method)) {
            okHttp.method = DEFAULT_METHOD;
        }
        if (isBlank(okHttp.mediaType)) {
            okHttp.mediaType = DEFAULT_MEDIA_TYPE;
        }

        String token = getAutToken();
        if (okHttp.headerMap == null) {
            okHttp.headerMap = new HashMap<String, String>();
            okHttp.headerMap.put("Authorization", token);
        } else {
            okHttp.headerMap.put("Authorization", token);
        }

        String url = okHttp.url;

        Request.Builder builder = new Request.Builder();

        if (CollectionUtils.isNotEmpty(okHttp.queryMap)) {
            String queryParams = okHttp.queryMap.entrySet().stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("&"));
            url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
        }
        builder.url(url);

        if (CollectionUtils.isNotEmpty(okHttp.headerMap)) {
            okHttp.headerMap.forEach(builder::addHeader);
        }

        String method = okHttp.method.toUpperCase();
        String mediaType = String.format("%s;charset=%s", okHttp.mediaType, okHttp.requestCharset);

        if (METHOD_GET.equals(method)) {
            builder.get();
        } else if (CollectionUtils.contains(new String[]{METHOD_POST, METHOD_PUT, METHOD_DELETE, METHOD_PATCH}, method)) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), okHttp.data == null ? (new byte[]{}) : okHttp.data);
            okHttp.data = null;
            builder.method(method, requestBody);
        } else {
            throw new RuntimeException(String.format("http method:%s not support!", method));
        }

        byte[] bytes;
        try {
            LOGGER.info("Beginning to connect the URL:{}  ,request method:{}", url, okHttp.method);
            Response response = client.newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected code " + response);
            }
            bytes = response.body().bytes();
        } catch (Exception e) {
            throw new RuntimeException(okHttp.toString(), e);
        }

        return bytes;
    }

    /**
     * 通用执行方法,返回字符串
     */
    private static String executeToStr(OkHttp okHttp) {
        return new String(executeToByte(okHttp), Charset.forName(okHttp.responseCharset));
    }

    private static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }


    @Builder
    @ToString(exclude = {"requestCharset", "responseCharset", "requestLog", "responseLog"})
    static class OkHttp {
        private String url;
        private String method = DEFAULT_METHOD;
        private byte[] data;
        private String mediaType = DEFAULT_MEDIA_TYPE;
        private Map<String, String> queryMap;
        private Map<String, String> headerMap;
        private String requestCharset = DEFAULT_CHARSET;
        private String responseCharset = DEFAULT_CHARSET;
    }
}
