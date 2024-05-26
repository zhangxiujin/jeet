package com.jeet.course.study.vo;

import com.jeet.course.bank.vo.ChooseVo;
import com.jeet.course.study.domain.CoursePracticeRecord;
import lombok.Data;

import java.util.List;

/**
 * @PackageName : com.jeet.course.study.vo
 * @FileName : ChooseStudy
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/1 14:59
 * @Version : 1.0.0
 */
@Data
public class ChooseStudy {

    private List<Answers> answersList;

    private Long contentId;

    private String userId;

    /**
     * 当前选择的课程结构id
     */
    private Long structId;

}
