package com.jeet.course.personal.vo;

import lombok.Data;

/**
 * @PackageName : com.jeet.course.personal.vo
 * @FileName : WordVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/11 10:15
 * @Version : 1.0.0
 */
@Data
public class WordTestVo {

    /**
     * 练习次数id
     */
    private Long recordId;

    /**
     * 英文单词
     */
    private String ehName;

    /**
     * 中文
     */
    private String zhName;

    /**
     * 回答
     */
    private String answer;

    /**
     * 是否正确
     */
    private String isSuccess;

}
