package com.ruxuanwo.fm.client.client;


import com.ruxuanwo.fm.client.model.FileItem;
import com.ruxuanwo.fm.client.utils.Result;

import java.util.List;

/**
 * 操作文件上传下载等功能
 *
 * @author ruxuanwo
 */
public interface FileManagerClient {
    /**
     * 创建文件夹
     * 1.当文件夹已经存在时，不会覆盖，仍然会返回成功。
     * 2.支持递归创建
     *
     * @param folderName 文件名,如：myFolder,递归创建示例:myFolder/yourFolder/hisFolder
     * @return
     */
    Result<String> createFolder(String folderName);

    /**
     * 文件夹遍历，返回改文件夹下所有的的一级子文件夹和文件
     *
     * @param folderName 文件夹名称
     * @return
     */
    Result<List<FileItem>> listFolder(String folderName);

    /**
     * 文件上传到指定目录
     *
     * @param folderName 上传的目录名，该文件夹位于项目根目录下，如果不存在会自动创建
     * @param data
     * @param fileName   文件名
     * @return 文件上传以后的相关属性
     */
    Result<FileItem> upload(String folderName, byte[] data, String fileName);

    /**
     * 文件上传，位于项目根目录下
     *
     * @param data
     * @param fileName 文件名
     * @return 文件上传以后的相关属性
     */
    Result<FileItem> upload(byte[] data, String fileName);

    /**
     * 从指定目录下载文件
     *
     * @param folderName 下载的目录路径，该文件夹位于项目根目录下，如果不存在会下载失败
     * @param fileName   文件名
     * @return 文件二进流
     */
    Result<byte[]> download(String folderName, String fileName);

    /**
     * 从项目根目录下下载文件
     *
     * @param virtualPath 文件虚拟路径
     * @return 文件二进流
     */
    Result<byte[]> download(String virtualPath);

    /**
     * 将多个文件打包成zip下载
     *
     * @param virtualPaths 多个virtualPath以","隔开,如果该参数为空，则会下载项目更目录下的所有文件
     * @return
     */
    Result<byte[]> downloadToZip(String virtualPaths);


    /**
     * 获取文件相关信息
     *
     * @param virtualPath
     * @return
     */
    Result<FileItem> getFile(String virtualPath);
}
