package com.jeet.course.study.vo;

import com.jeet.course.bank.vo.SimpleVo;
import lombok.Data;

import java.util.List;

/**
 * @Desc: SimpleContentVo
 * @author: xue
 * @Date: 2023/9/4 16:37
 */
@Data
public class SimpleContentVo {

    private List<SimpleAnswerVo> answer;
    private Long contentId;
    private String simpleType;
    private Long structId;

}
