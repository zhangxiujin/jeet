package com.jeet.common.file.utils;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zhangxiujin
 * @date: 2022/4/23 13:17
 * @descripton 视频处理工具类
 */
@Slf4j
public class VideoUtil {
    /**
     * 初始化支持的视频格式
     */
    private static ImmutableList<String> videoSuffixList = ImmutableList.of("mp4", "mov", "avi", "mkv", "m4v", "wmv",
            "asf", "asx", "rm", "rmvb", "3gp", "dat", "flv", "vob");

    private static final String ZERO = "0";

    /**
     * 获取视频文件的数量
     * @param path
     * @return
     */
    public static int getVideoCount(String path) {
        File file = new File(path);
        Stack<File> stack = new Stack<File>();
        videoToStack(file, stack, videoSuffixList);
        return stack.size();
    }

    /**
     * 获取某个目录及子目录下所有视频的时长 (单位秒)
     * @param path
     * @return
     */
    public static long getVideoDuration(String path) {
        long sumDuration = 0L;
        File file = new File(path);
        Stack<File> stack = new Stack<File>();
        videoToStack(file, stack, videoSuffixList);
        while (!stack.isEmpty()) {
            File video = stack.pop();
            long videoDuration = getVideoDuration(video);
            sumDuration += videoDuration;
        }
        return sumDuration;
    }

    /**
     * 获取某个视频的时长 (单位秒)
     * @param video
     * @return
     */
    public static long getVideoDuration(File video) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(video);
            MultimediaInfo info = multimediaObject.getInfo();
            return info.getDuration() / 1000;
        } catch (Exception e) {
            log.error("获取视频时长失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断文件是否是视频
     * @param file 文件
     * @return 是否是视频
     */
    public static boolean isVideo(File file) {
        return videoSuffixList.contains(FilenameUtils.getExtension(file.getName()));
    }

    /**
     * 进目录，递归思想
     * @param dir
     */
    private static void videoToStack(File dir, Stack stack, List<String> videoSuffixList) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files.length == 0) {  //目录是空
                return;
            } else {
                for (File file : files) {
                    if (isVideo(file)) {
                        stack.push(file);
                    }
                    videoToStack(file, stack, videoSuffixList);
                }
            }
        }
    }

    /**
     * 通过ffmpeg获取视频的时长
     * @param filePath
     * @param ffmpegPath
     * @return
     * @throws IOException
     */
    public static long readVideoTimeFromCommand(String filePath, String ffmpegPath) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        List<String> commands = new ArrayList<>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(filePath);
        builder.command(commands);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        // 获取执行输出信息
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder outInfo = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            outInfo.append(line);
        }
        br.close();
        // 通过正则获取时长信息
        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
        Pattern pattern = Pattern.compile(regexDuration);
        Matcher matcher = pattern.matcher(outInfo.toString());
        if (matcher.find()) {
            return getTime(matcher.group(1));
        }
        return 0;
    }

    /**
     * 获取时间毫秒
     * @param time 格式:"00:00:10.68"
     * @return
     */
    private static long getTime(String time) {
        int min = 0;
        String[] strs = time.split(":");
        if (strs[0].compareTo(ZERO) > 0) {
            // 秒
            min += Long.parseLong(strs[0]) * 60 * 60 * 1000;
        }
        if (strs[1].compareTo(ZERO) > 0) {
            min += Long.parseLong(strs[1]) * 60 * 1000;
        }
        if (strs[2].compareTo(ZERO) > 0) {
            min += Math.round(Double.parseDouble(strs[2]) * 1000);
        }
        return min;
    }

}
