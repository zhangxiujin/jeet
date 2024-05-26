package com.jeet.course.content.vo;

import lombok.Data;

import java.util.List;

/**
 * 课程内容扩展实体
 */
@Data
public class ContentVo {

    /**
     * 选择题集合
     */
    private List<Long> chooseList;

    /**
     * 单词题集合
     */
    private List<Long> wordList;

    /**
     * 项目题集合
     */
    private List<Long> projectList;

    /**
     * 简答题集合
     */
    private List<Long> simpleList;

    /**
     * 内容名称
     */
    private String name;

    /**
     * 内容描述
     */
    private String desc;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频扩展名
     */
    private String ext;

    /**
     * 结构id
     */
    private Long structId;

    /**
     * 内容id
     */
    private Long contentId;
}
