package com.ruxuanwo.fm.client.utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 图片压缩工具类
 *
 * @author ruxuanwo
 */
public class ImageUtil {

    // 图片默认缩放比率
    private static final Double DEFAULT_SCALE = 0.8d;

    private static final Integer DEFAULT_WIDTH = 200;

    private static final Integer DEFAULT_HEIGHT = 200;


    /**
     * 根据文件扩展名判断文件是否图片格式
     *
     * @param extension 文件扩展名
     * @return
     */
    public static boolean isImage(String extension) {
        String[] imageExtension = new String[]{"jpeg", "jpg", "gif", "bmp", "png"};
        for (String e : imageExtension) {
            if (extension.toLowerCase().equals(e)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] setImageSize(InputStream fileStream, String extension) throws Exception {
        return setImageSize(DEFAULT_WIDTH, DEFAULT_HEIGHT , fileStream, extension);
    }

    public static byte[] setImageScale(InputStream fileStream, String extension) throws Exception {
        return setImageScale(DEFAULT_SCALE, fileStream, extension);
    }

    public static byte[] setImageScale(Double scale, InputStream fileStream, String extension) throws Exception {
        if (!isImage(extension)){
            return toByte(fileStream);
        }
        BufferedImage bufferedImage = Thumbnails.of(fileStream).scale(scale).asBufferedImage();
        return getImageData(bufferedImage, extension);
    }

    public static byte[] setImageSize(Integer width, Integer height, InputStream fileStream, String extension) throws Exception {
        if (!isImage(extension)){
            return toByte(fileStream);
        }
        BufferedImage bufferedImage = Thumbnails.of(fileStream).size(width, height).asBufferedImage();
        return getImageData(bufferedImage, extension);
    }

    private static byte[] getImageData(BufferedImage bufferedImage, String extension) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, extension, out);
        return out.toByteArray();
    }

    private static byte[] toByte(InputStream inputStream){
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()){
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
