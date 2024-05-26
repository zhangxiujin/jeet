package com.jeet.course.study.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: WordContentVo
 * @author: xue
 * @Date: 2023/8/31 10:39
 */
@Data
public class WordContentVo {

    private List<WordVo> answers;
    private Long contentId;
    private String wordType;
    private Integer score;
    private Long id;
    private String comment;
    private Long structId;

}
