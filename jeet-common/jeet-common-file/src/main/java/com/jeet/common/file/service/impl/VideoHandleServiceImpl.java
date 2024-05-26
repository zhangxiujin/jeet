package com.jeet.common.file.service.impl;

import com.jeet.common.file.service.IVideoHandleService;
import com.jeet.common.file.utils.CmdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class VideoHandleServiceImpl implements IVideoHandleService {

    private final static String KEY_INFO_FILE_NAME = "encrypt.keyinfo";
    private final static String KEY_FILE_NAME = "encrypt.key";

    @Value("${minio.video.oos-path-prefix}")
    private String oosPathPrefix;

    @Override
    public void hlsEncryptHandle(boolean transcoding, String inPath, String inputFileName,
             String cosPath, String outVideoPath, String keyFileName, String tsName, String m3u8Name) throws IOException {
        hlsHandle(transcoding, 18, 10, true, inPath, inputFileName, cosPath,
                outVideoPath, keyFileName, tsName, m3u8Name);
    }

    @Override
    public void hlsHandle(boolean transcoding, int crf, int hls_time, boolean encrypt, String inPath,
                    String inputFileName, String cosPath, String outVideoPath, String keyFileName,
                          String tsName, String m3u8Name) throws IOException {
        if (encrypt) {
            generateKeyInfoFile(outVideoPath, keyFileName, cosPath);
        }
        CmdUtil.executeCmdNotReturn(
                "ffmpeg" +
                        " -y" +
                        " -i " + inPath + "/" + inputFileName +
                        " -hls_time " + hls_time +
                        (encrypt ? " -hls_key_info_file " + KEY_INFO_FILE_NAME : "") +
                        (transcoding ? " -c:v \"libx264\"" : " -c:a copy -c:v copy")  +
                        " -crf " + crf +
                        " -hls_playlist_type vod" +
                        " -hls_segment_filename \"" + tsName + "-%5d.ts\" " + m3u8Name + ".m3u8",
                outVideoPath);
    }

    @Override
    public void generateEncryptKeyFile(String path, String keyName) throws IOException {
        CmdUtil.executeCmdNotReturn("openssl rand  16 > " + keyName + ".key", path);
    }

    @Override
    public String generateIV() throws IOException {
        return CmdUtil.executeCmd("openssl rand -hex 16");
    }

    @Override
    public void generateKeyInfoFile(String outVideoPath, String keyFileName, String cosPath) throws IOException {
        FileWriter fw = null;
        try {
            generateEncryptKeyFile(outVideoPath, keyFileName);
            String iv = generateIV();
            File keyInfoFile = new File(outVideoPath + "/" + KEY_INFO_FILE_NAME);
            fw = new FileWriter(keyInfoFile);
            fw.write(oosPathPrefix + cosPath + "/" + keyFileName + ".key" + "\n");
            fw.write(keyFileName + ".key" + "\n");
            fw.write(iv);
        } catch (IOException e) {
            throw e;
        } finally {
            if (fw != null) {
                fw.flush();
                fw.close();
            }
        }
    }
}
