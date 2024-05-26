package com.jeet.common.file.service.impl;

import com.jeet.common.file.config.MinioConfig;
import com.jeet.common.file.entity.UploadFile;
import com.jeet.common.file.service.IFileService;
import com.jeet.common.file.service.IVideoHandleService;
import com.jeet.common.file.utils.FileUploadUtils;
import com.jeet.common.file.utils.FileUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * @Desc: minio实现文件上传
 * @author: qingmei
 * @Date 2023/8/21 15:54
 */
@Service
public class MinioFileServiceImpl implements IFileService {
    @Value("${minio.video.local-path}")
    private String localVideoPath;
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private IVideoHandleService videoHandleService;

    @Override
    public UploadFile uploadFile(MultipartFile file, Long fileId) {
        try {
            String fileName = FileUploadUtils.extractFilename(file);
            String extName = FileUploadUtils.extractExtName(file);
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(String.valueOf(fileId))
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(args);
            String url = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileId;
            return new UploadFile(fileId, fileName, extName, url);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UploadFile uploadAudioFile(MultipartFile file, Long fileId) {
        try {
            String fileName = FileUploadUtils.extractFilename(file);
            String extName = FileUploadUtils.extractExtName(file);
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object("/audios/" + fileId + "/" + fileId + "." + extName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(args);
            String url = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/audios/" + fileId;
            return new UploadFile(fileId, fileName, extName, url);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UploadFile uploadVideo(MultipartFile file, Long fileId) {
        try {
            String fileName = FileUploadUtils.extractFilename(file);
            String extName = FileUploadUtils.extractExtName(file);
            //上传文件到本地
            String _fileId = String.valueOf(fileId);
            FileUtil.toLocal(file.getInputStream(), localVideoPath + "/vod/" + _fileId, _fileId);
            videoHandleService.hlsHandle(false, 18, 10, false,
                    localVideoPath + "/vod/" + _fileId, _fileId, "/course/videos",
                    localVideoPath + "/" + _fileId, _fileId, _fileId, _fileId);
            File localTsDir = new File(localVideoPath + "/" + _fileId);
            if (localTsDir.exists()) {
                File[] files = localTsDir.listFiles();
                for (File f : files) {
                    upload(f, file.getContentType(), _fileId);
                }
            }
            String url = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/videos/" + fileId + "/" + fileId + ".m3u8";
            return new UploadFile(fileId, fileName, extName, url);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void upload(File file, String contentType, String dir) throws Exception {
        long length = file.length();
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object("/videos/" + dir + "/" + file.getName())
                .stream(new FileInputStream(file), length, -1)
                .contentType(contentType)
                .build();
        minioClient.putObject(args);
    }

}
