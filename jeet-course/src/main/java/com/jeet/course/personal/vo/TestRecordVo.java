package com.jeet.course.personal.vo;

import lombok.Data;

/**
 * @PackageName : com.jeet.course.personal.vo
 * @FileName : TestRecordVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/8 12:05
 * @Version : 1.0.0
 */
@Data
public class TestRecordVo {

    /**
     *
     */
    private Long recordId;

    /**
     * 结构id
     */
    private Long structId;

    /**
     * 内容id
     */
    private Long contentId;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 用户id
     */
    private Long userId;
}
