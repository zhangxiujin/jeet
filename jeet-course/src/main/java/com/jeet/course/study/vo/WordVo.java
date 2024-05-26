package com.jeet.course.study.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: WordVo
 * @author: xue
 * @Date: 2023/8/31 8:47
 */
@Data
public class WordVo {

    /**
     * 英文单词
     */
    private String enName;

    /**
     * 中文翻译
     */
    private String zhName;

    /**
     * 输入的答案
     */
    private String answer;

    /**
     * 是否对错
     */
    private String correct;

}
