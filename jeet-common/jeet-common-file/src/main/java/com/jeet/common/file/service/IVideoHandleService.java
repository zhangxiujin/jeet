package com.jeet.common.file.service;

import java.io.IOException;

/**
 * 视频处理服务接口
 */
public interface IVideoHandleService {


    /**
     * 视频hls加密处理
     * @param transcoding
     * @param inPath
     * @param inputFileName
     * @param cosPath
     * @param outVideoPath
     * @param keyFileName
     * @param tsName
     * @param m3u8Name
     * @throws IOException
     */
    void hlsEncryptHandle(boolean transcoding, String inPath, String inputFileName, String cosPath, String outVideoPath, String keyFileName, String tsName, String m3u8Name) throws IOException;

    /**
     * 视频处理
     * @param transcoding
     * @param crf
     * @param hls_time
     * @param encrypt
     * @param inPath
     * @param inputFileName
     * @param cosPath
     * @param outVideoPath
     * @param keyFileName
     * @param tsName
     * @param m3u8Name
     * @throws IOException
     */
    void hlsHandle(boolean transcoding, int crf, int hls_time, boolean encrypt,
                       String inPath, String inputFileName, String cosPath, String outVideoPath,
                       String keyFileName, String tsName, String m3u8Name) throws IOException;

    /**
     * 生成hls视频加密的密钥文件
     */
    void generateEncryptKeyFile(String path, String keyName) throws IOException;

    /**
     * 生成一个iv
     * @return
     * @throws IOException
     */
    String generateIV() throws IOException;

    /**
     * 生成 encrypt.keyinfo 文件
     */
    void generateKeyInfoFile(String outVideoPath, String keyFileName, String cosPath) throws IOException;
}
