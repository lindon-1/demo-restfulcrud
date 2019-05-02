package com.example.demo.utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 11:59  2019/5/2
 * @return :
 */
public class VideoUtils {
    // 文件允许格式
    public static String[] allowFiles = {  ".mp4", ".avi", ".wmv", ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb" };
    // 允许转码的视频格式（ffmpeg）
    public static String[] allowFLV = { ".avi", ".mpg", ".wmv", ".3gp", ".mov", ".asf", ".asx", ".vob" ,".mp4"};

    // 允许的视频转码格式(mencoder)
    public  static String[] allowAVI = { ".wmv9", ".rm", ".rmvb" };

    /**
     * 文件类型判断
     *
     * @param fileName
     * @return
     */

    public static boolean  checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 视频类型判断(flv)
     *
     * @param fileEnd
     * @return
     */
    public static boolean checkMediaType(String fileEnd) {
        Iterator<String> type = Arrays.asList(allowFLV).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileEnd.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 视频类型判断(AVI)
     *
     * @param fileEnd
     * @return
     */
    public static boolean checkAVIType(String fileEnd) {
        Iterator<String> type = Arrays.asList(allowAVI).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileEnd.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }



    /**
     * 依据原始文件名生成新文件名
     * UUID：全局唯一标识符，由一个十六位的数字组成,由三部分组成：当前日期和时间、时钟序列、全局唯一的IEEE机器识别号
     * @return string
     */

    public static String getName(String fileName) {
        Random random = new Random();
        return "" + random.nextInt(10000) + System.currentTimeMillis();
//return UUID.randomUUID().toString() + "_" + fileName;
    }


    /**
     * 文件大小，返回kb.mb
     *
     * @return
     */
    public static String getSize(File file) {
        String size = "";
        long fileLength = file.length();
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileLength < 1024) {
            size = df.format((double) fileLength) + "BT";
        } else if (fileLength < 1048576) {
            size = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824) {
            size = df.format((double) fileLength / 1048576) + "MB";
        } else {
            size = df.format((double) fileLength / 1073741824) + "GB";
        }
        return size;
    }
    public static boolean createParentPath(String path) {
        File file = new File(path);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
