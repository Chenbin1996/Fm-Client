package com.ruxuanwo.fm.client.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ruxuanwo.fm.client.client.FileManagerClient;
import com.ruxuanwo.fm.client.config.FmConfig;
import com.ruxuanwo.fm.client.model.FileItem;
import com.ruxuanwo.fm.client.utils.FileUtil;
import com.ruxuanwo.fm.client.utils.HttpUtils;
import com.ruxuanwo.fm.client.utils.ResponseMsgUtil;
import com.ruxuanwo.fm.client.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实际操作文件上传下载等功能实现类
 *
 * @author ruxuanwo
 */
public class DefaultFileManagerClient implements FileManagerClient {
    private static final Integer MAP_SIZE = 16;


    public DefaultFileManagerClient() {
        super();
        //初始化HttpUtils
        HttpUtils.init();
    }

    @Override
    public Result<String> createFolder(String folderName) {
        folderName = FileUtil.checkFolderName(folderName);
        String url = FmConfig.getProjectResourceUrl().concat(folderName);
        String result = HttpUtils.postForm(url, null);
        return ResponseMsgUtil.success(result);
    }

    @Override
    public Result<List<FileItem>> listFolder(String folderName) {
        folderName = (folderName == null) ? "" : FileUtil.checkFolderName(folderName);
        String resultStr = HttpUtils.get(FmConfig.getProjectResourceUrl() + folderName);
        if (resultStr == null) {
            throw new RuntimeException("Not found !");
        }
        List<FileItem> items;
        try {
            items = convertObject(JSONObject.parseObject(resultStr).getString("items"), new TypeReference<List<FileItem>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Not found !", e);
        }
        return ResponseMsgUtil.success(items);
    }

    @Override
    public Result<FileItem> upload(String folderName, byte[] data, String fileName) {
        FileUtil.checkFileSize(data);
        String url = FmConfig.getProjectResourceUrl();
        String virtualPath = FmConfig.SEPARATOR + FmConfig.getRootDirectory();
        if (folderName != null && folderName.trim().length() > 0) {
            folderName = FileUtil.checkFolderName(folderName);
            //创建文件夹
            createFolder(folderName);
            virtualPath += folderName;
            url += folderName;
        }
        fileName = FileUtil.checkFileName(fileName);
        String fmFileName = FileUtil.makeFileName(fileName);
        url += fmFileName;
        virtualPath += fmFileName;
        HttpUtils.upload(url, data);
        Result<FileItem> result;
        try {
            result = getFile(virtualPath);
        } catch (Exception e) {
            throw new RuntimeException("Upload file failed:", e);
        }
        if (result == null) {
            throw new RuntimeException("Upload file failed !");
        }
        return result;
    }

    @Override
    public Result<FileItem> upload(byte[] data, String fileName) {
        return upload(null, data, fileName);
    }

    @Override
    public Result<byte[]> download(String folderName, String fileName) {
        fileName = FileUtil.checkFileName(fileName);
        String virtualPath = FmConfig.SEPARATOR + FmConfig.getRootDirectory();
        if (folderName != null && folderName.trim().length() > 0) {
            folderName = FileUtil.checkFolderName(folderName);
            virtualPath += folderName;
        }
        virtualPath += fileName;
        return download(virtualPath);
    }

    @Override
    public Result<byte[]> download(String virtualPath) {
        return ResponseMsgUtil.success(HttpUtils.download(FmConfig.getDownloadUrl().concat(FileUtil.checkFirst(virtualPath))));
    }

    @Override
    public Result<byte[]> downloadToZip(String virtualPaths) {
        Map<String, String> queryMap = new HashMap<>(MAP_SIZE);
        String url = FmConfig.getProjectDownloadUrl();
        if (virtualPaths != null && !"".equals(virtualPaths.trim())) {
            queryMap.put("files", virtualPaths);
            url = FmConfig.getDownloadUrl();
        }
        queryMap.put("format", "zip");
        byte[] fileData = HttpUtils.download(url, queryMap);
        return ResponseMsgUtil.success(fileData);
    }

    @Override
    public Result<FileItem> getFile(String virtualPath) {
        String url = FmConfig.getResourceUrl() + FileUtil.checkFirst(virtualPath);
        String result = HttpUtils.get(url);
        FileItem item;
        try {
            item = convertObject(result, new TypeReference<FileItem>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Get file failed");
        }
        item.setOriginalName(FileUtil.getOriginalName(item.getName()));
        item.setUrl(FmConfig.getAgentUrl() + item.getVirtualPath());
        item.setExtension(item.getExtension().substring(1));
        return ResponseMsgUtil.success(item);
    }

    private <T> T convertObject(String json,
                                TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }
}
