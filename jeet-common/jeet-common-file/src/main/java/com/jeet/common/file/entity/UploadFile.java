package com.jeet.common.file.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Desc: File
 * @author: qingmei
 * @Date 2023/8/21 16:22
 */
@Data
@AllArgsConstructor
public class UploadFile {
    /**
     * private String id;
     */
    private Long id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 扩展名
     */
    private String extName;
    /**
     * 上传地址
     */
    private String url;


}
