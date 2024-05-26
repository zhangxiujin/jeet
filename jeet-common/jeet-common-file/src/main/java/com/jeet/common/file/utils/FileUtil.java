package com.jeet.common.file.utils;

import java.io.*;

/**
 * 文件工具类
 */
public class FileUtil {

    /**
     * 创建文件目录
     * @param path 可以是多级目录
     */
    public static File createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                throw new RuntimeException("创建文件失败!");
            }
        }
        return dir;
    }

    /**
     * 通过文件流生成本地文件
     */
    public static void toLocal(InputStream inputStream, String localPath, String fileName) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            File localDir = new File(localPath);
            if(!localDir.exists()) {
                localDir.mkdirs();
            }
            bos = new BufferedOutputStream(new FileOutputStream(
                    localPath + "/" + fileName));
            byte[] bytes = new byte[8192];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        } finally {
            if(bis != null) {
                bis.close();
            }
            if(bos != null) {
                bos.close();
            }
        }
    }
}
