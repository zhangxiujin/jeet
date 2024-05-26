package com.jeet.common.file.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Desc: FileUploadUtils
 * @author: qingmei
 * @Date 2023/8/21 15:57
 */
public class FileUploadUtils {

    /**
     * 获取文件名
     * @param file
     */
    public static String extractFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf('.');
        return originalFilename.substring(0, index);
    }

    /**
     * 提取扩展名
     * @param file
     * @return
     */
    public static String extractExtName(MultipartFile file) {
        String[] arr = file.getOriginalFilename().split("\\.");
        return arr[arr.length - 1];
    }
}
