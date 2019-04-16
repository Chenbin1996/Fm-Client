package com.ruxuanwo.fm.client.utils;


import com.ruxuanwo.fm.client.config.FmConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 文件操作类
 *
 * @author ruxuanwo
 */
public class FileUtil {
    private static final Integer BYTE_SIZE = 1024;

    private static final String FILE_EXT_SEPARATOR = ".";
    /**
     * 匹配特殊字符,如 \:*?"<>|
     **/
    private static final Pattern PATTERN = Pattern.compile("[\\u005C:\\u002A\\u003F\"<>\'\\u007C’‘“”：？]");

    private FileUtil() {
    }

    /**
     * 检查文件夹或文件的路径
     *
     * @param fileName
     * @param isDir
     * @return
     */
    public static String checkFirstAndLast(String fileName, Boolean isDir) {
        //检查第一个字符
        if (fileName.startsWith(FmConfig.SEPARATOR)) {
            fileName = fileName.substring(1);
        }
        //检查最后一个字符

        return isDir ? checkFolderLast(fileName) : checkFileLast(fileName);
    }

    /**
     * 检查是否以“/”开始，如果是截掉第一个"/"
     *
     * @param fileName
     * @return
     */
    public static String checkFirst(String fileName) {
        return fileName.startsWith(FmConfig.SEPARATOR) ? fileName.substring(1) : fileName;
    }

    /**
     * 检查文件路径是否是以“/”结尾，如果是则截掉最后一个"/"
     *
     * @param fileName 文件名
     * @return
     */
    public static String checkFileLast(String fileName) {
        return fileName.endsWith(FmConfig.SEPARATOR) ? fileName.substring(0, fileName.length() - 1) : fileName;
    }

    /**
     * 检查文件路径是否是以“/”结尾，如果不是是则在最后加上"/"
     *
     * @param fileName 文件名
     * @return
     */
    public static String checkFolderLast(String fileName) {
        return fileName.endsWith(FmConfig.SEPARATOR) ? fileName : fileName.concat(FmConfig.SEPARATOR);
    }

    /**
     * 检查文件路径或者文件名是否含有特殊字符
     *
     * @param fileName
     */
    public static String checkFileName(String fileName) {
        checkNameIsLegal(fileName);
        return checkFirstAndLast(fileName, false);
    }

    /**
     * 检查文件夹名字或者路径是否合法，合法以后，返回一个以"/"结尾的路径
     *
     * @param folderName
     * @return
     */
    public static String checkFolderName(String folderName) {
        checkNameIsLegal(folderName);
        return checkFirstAndLast(folderName, true);
    }

    public static void checkNameIsLegal(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new RuntimeException("The name of file must bt not null! ");
        }
        if (PATTERN.matcher(name).find()) {
            throw new RuntimeException("The name of file cant't include special characters,like '\\/:*?'");
        }
    }

    /**
     * 读取文件，返回字节数组
     *
     * @param path 绝对路径
     * @return
     */
    public static byte[] readFile(String path) {
        byte[] resultBytes = null;
        try (FileInputStream fis = new FileInputStream(new File(path));
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] tmpBytes = new byte[1024];
            int len;
            while ((len = fis.read(tmpBytes, 0, BYTE_SIZE)) != -1) {
                baos.write(tmpBytes, 0, len);
            }
            resultBytes = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultBytes;
    }

    /**
     * 注意，请勿修改该方法，与该方法相关的方法为:getOriginalName(String fmFileName)
     *
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     */
    public static String makeFileName(String filename) {
        ///为防止文件名冲的现象发生，要为上传文件产生一个唯一的文件名
        return DateUtil.format(new Date(), "yyyyMMddHHmmssSSS")+ "_" + filename;
        //return UUID.randomUUID().toString().replace("-", "") + "_" + filename;
    }

    /**
     * 根据makeFileName(String filename)方法生成的文件名解析出原来的文件名
     *
     * @param fmFileName
     * @return
     */
    public static String getOriginalName(String fmFileName) {
        //boolean flag = fmFileName != null && fmFileName.length() > 32 && fmFileName.charAt(32) == '_';
        boolean flag = fmFileName != null && fmFileName.contains("_");
        return flag ? fmFileName.substring(fmFileName.indexOf("_") + 1) : fmFileName;
    }

    /**
     * 获取文件的拓展名，如果文件名为空或者没有拓展名，则返回空
     *
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
        return fileName != null && fileName.contains(FILE_EXT_SEPARATOR) ? fileName.substring((fileName.lastIndexOf(FILE_EXT_SEPARATOR) + 1)) : null;
    }

    public static void checkFileSize(byte[] data) {
        if (data == null) {
            return;
        }
        if (data.length > FmConfig.getMaxUploadSize()) {
            throw new RuntimeException("超出最大文件限制！");
        }
    }
}
