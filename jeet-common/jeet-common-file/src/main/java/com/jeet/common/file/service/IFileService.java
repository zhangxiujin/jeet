package com.jeet.common.file.service;

import com.jeet.common.file.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 * @author jeet
 */
public interface IFileService {

    /**
     * 文件上传接口
     */
    UploadFile uploadFile(MultipartFile file, Long fileId);

    /**
     * 上传音频文件
     * @param file
     * @param fileId
     * @return
     */
    UploadFile uploadAudioFile(MultipartFile file, Long fileId);

    /**
     * 视频上传接口
     */
    UploadFile uploadVideo(MultipartFile file, Long fileId);
}
