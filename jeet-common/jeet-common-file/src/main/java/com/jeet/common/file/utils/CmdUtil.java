package com.jeet.common.file.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * cmd命令运行工具类
 */
@Slf4j
public class CmdUtil {

    /**
     * 执行有返回结果的cmd命令
     * @param command cmd命令
     * @return
     * @throws IOException
     */
    public static String executeCmd(String command) throws IOException {
        log.info("Execute command : " + command);
        String path = System.getProperty("user.dir");
        return executeCmd(command, path);
    }

    /**
     * 执行有返回结果的cmd命令
     * @param command cmd命令
     * @param path 要执行cmd命令的所在目录
     * @return
     * @throws IOException
     */
    public static String executeCmd(String command, String path) throws IOException {
        log.info("Execute command : " + command);
        File dir = FileUtil.createDir(path);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /c " + command, null, dir);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder build = new StringBuilder();
        while ((line = br.readLine()) != null) {
            build.append(line);
        }
        return build.toString();
    }

    /**
     * 执行无返回结果的cmd命令
     * @param command cmd命令
     * @param path 要执行cmd命令的所在目录
     * @throws IOException
     */
    public static void executeCmdNotReturn(String command, String path) throws IOException {
        log.info("Execute command : " + command);
        File dir = FileUtil.createDir(path);
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("cmd /c " + command, null, dir);
        try {
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ( (line = br.readLine()) != null) {
                log.debug(line);
            }
            int exitVal = proc.waitFor();
            log.debug("Process exitValue: " + exitVal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
