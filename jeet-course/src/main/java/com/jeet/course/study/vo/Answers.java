package com.jeet.course.study.vo;

import lombok.Data;

import java.util.List;

/**
 * @PackageName : com.jeet.course.study.vo
 * @FileName : Answers
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/5 15:01
 * @Version : 1.0.0
 */
@Data
public class Answers {

        /**
         * 课程id
         */
        private Long bankId;

        /**
         * 单选回答
         */
        private String option;

        /**
         * 多选回答
         */
        private List<String> options;

        /**
         * 是否正确 0否 1是
         */
        private String correct;


}
