package com.jeet.course.study.vo;

import com.jeet.common.file.entity.UploadFile;
import lombok.Data;

/**
 * @projectName: jeet
 * @package: com.jeet.course.study.vo
 * @className: UploadFileVo
 * @author: CYH
 * @description: TODO
 * @date: 2023/9/4 16:22
 */
@Data
public class UploadFileVo {

    private UploadFile uploadFile;
    private Long bankId;

    public UploadFileVo(UploadFile uploadFile, Long bankId) {
        this.uploadFile = uploadFile;
        this.bankId = bankId;
    }
}

