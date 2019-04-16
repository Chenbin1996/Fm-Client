package com.ruxuanwo.fm.client.model;

/**
 * 请求响应实体类，用于返回数据结果
 *
 * @author ruxuanwo
 */
public class FileItem {
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件原名称
     */
    private String originalName;
    /**
     * 文件大小，单位：字节(B)
     * 1M = 1024KB = 1024*1024B
     */
    private int size;
    /**
     * 相对URL，如:/files/myFolder/test.txt
     * 文件服务器+url便可直接访问该文件，而virtualPath是必须调用接口才能访问
     */
    private String url;
    /**
     * 文件拓展名，如:.txt .jpeg
     */
    private String extension;

    /**
     * 是否为文件夹
     */
    private Boolean isDir;


    /**
     * 相对虚拟路径,如：/myFolder/test.txt
     */
    private String virtualPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getDir() {
        return isDir;
    }

    public void setDir(Boolean dir) {
        isDir = dir;
    }

    public String getVirtualPath() {
        return virtualPath;
    }

    public void setVirtualPath(String virtualPath) {
        this.virtualPath = virtualPath;
    }
}
