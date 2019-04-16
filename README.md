# 简介

`fm-client`是基于okhttp3实现的文件管理工具客户端，实现与File Browser进行数据交互，包括文件上传、下载、查看等功能。
* 当前版本：1.0
* 该工程实现了创建文件夹、创建文件、~~~删除文件夹~~~、~~~删除文件~~~、上传、下载。
* 目前已经测试过的文件类型有：
   - *.docx
   - *.xlsx
   - *.pptx
   - *.png
   - *.jpg
   - *.txt
   - *.pdf
   - *.zip
   - *.mp4
* File Browser支持图片、TXT、PDF在线预览，github官网：[https://github.com/filebrowser/filebrowser](https://github.com/filebrowser/filebrowser)


# 坏境
- JDK：1.8
- okhttp3：3.9.1
- Maven：3.x.x
- lombok 1.16.18 (IDEA中需要在Setting中配置lombok的Plugins里加入lombok插件)

# 关于okhttp3

目前使用版本: 3.9.1 <br>
一个处理网络请求的开源项目，用于替代HttpUrlConnection和Apache HttpClient，具有下列优势：
  - 允许连接到同一个主机地址的所有请求,提高请求效率
  - 共享Socket,减少对服务器的请求次数
  - 通过连接池,减少了请求延迟
  - 缓存响应数据来减少重复的网络请求
  - 减少了对数据流量的消耗
  - 自动处理GZip压缩
  
# 使用步骤
## 1. 下载
在Github地址下载HttpClient，地址：[HttpClient](https://github.com/Chenbin1996/HttpClient)

## 2. 添加并修改配置文件
在resources目录下新建`fm-config-default.properties`文件，内容如下：
``` properties
#*************文件管理系统相关配置*****************

#远程服务器资源地址，非80端口，需要加上端口,形如:127.0.0.1:8080
fm.url = http://192.168.8.241
#远程服务器读取地址，用于前台显示及下载,用于读写分离
fm.agentUrl = http://192.168.8.241:8888
#项目文件上传默认根目录不存在会自动创建,如果不指定文件则会上传到服务器根目录下
fm.upload.rootDirectory=hm/
#以字节为单位的最大上传文件的大小(单位:B),1M = 1024KB = 1024*1024B=1048576B
fm.max.uploadSize =10485760
#连接超时时间(秒)
fm.connect.timeout = 10
#写入超时时间(秒)
fm.write.timeout = 10
#读取超时时间(秒)
fm.read.timeout = 10
#websocket轮训间隔(单位:秒)
fm.pingInterval = 10

#用户名与密码配置，暂无
#文件服务器鉴权用户名
fm.auth.username=admin
#文件服务器鉴权密码
fm.auth.password=admin

#连接池配置
#空闲的socket最大连接数(单位:秒)
fm.pool.maxIdleConnections = 30
#socket的keepAlive时间(单位:秒)
fm.pool.keepAliveDuration = 300


#以下请勿轻易修改
#远程服务器resource对应的URI
fm.resource.uri = /api/resource/
#远程服务器下载对应的URI
fm.download.uri = /api/download/
#远程服务之前缀
fm.prefix = files


#文件服务器鉴权uri
fm.auth.uri=/api/auth/get
#token过期时间 单位：毫秒;例如23小时过期：23*60分钟*60秒*1000 = 82800000
fm.auth.token.expires.time.ms=82800000

```

**注意：可根据实际情况调整相关配置参数。**

## 3. 接口使用
```java
import com.example.client.http.FileManagerClient;
import com.example.client.http.impl.FileManagerClientImpl;
import com.example.client.utils.FileUtil;

/**
 * @Author: ruxuanwo
 * @Date: 2018/5/21/0021 13:51
 */
public class text {
    private static FileManagerClient client = new FileManagerClientImpl();
    public static void main(String[] args) {
        upload();
        downloadToZip();
    }



    private static void upload(){
        String path = "D:/code.jpg";
        String fileName = "code.jpg";
        System.out.println("开始读取文件....");
        byte[] result = FileUtil.readFile(path);
        System.out.println(String.format("文件读取完毕，文件长度:%d", result.length));
        System.out.println("开始上传文件....");
        System.out.println(client.upload(result, fileName));
    }
    
    private static void downloadToZip(){
        String fileName = "test.zip";
        String filePath = "D:/";
        Result<byte[]> result = fileManagerClient.downloadToZip(null);
        //3. 将二进制文件保存到本地
        System.out.printf(result.getData().toString());
        byte[] fileData = result.getData();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(fileData);
        FileOutputStream fos = new FileOutputStream(filePath + "/" + fileName);
        output.writeTo(fos);
        output.flush();
        output.close();
        fos.close();
    }
}
```

# 4. 接口说明
``` java
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

```

## 注意
> 文件上传到服务器以后，保存在服务器的文件名规则为：**年月日时分秒 + "_" + 原始文件名**。如上传一个名为"test.jpg"到服务器上，则在服务器保存的文件名为:**20190416113953_test.jpg**
