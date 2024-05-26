package com.jeet.course.study.vo;

import com.jeet.course.study.domain.CoursePracticeRecord;
import lombok.Data;

import java.util.List;

/**
 * @projectName: jeet
 * @package: com.jeet.course.study.vo
 * @className: CourseStudyProjectVo
 * @author: CYH
 * @description: TODO
 * @date: 2023/9/1 9:01
 */
@Data
public class CourseStudyProjectVo extends CoursePracticeRecord {

    /**
     * 一个课程内容下的所有项目的回答
     */
    private List<Answers> answersList;
    /**
     * 当前选中的课程结构id
     */
    private Long structId;

    /**
     * 用户回答的内容
     */
    @Data
    static class Answers {
        /**
         * 题目id
         */
        private Long bankId;
        /**
         * 回答的附件urls
         */
        private List<Attach> attachUrls;
    }

    /**
     * 附件
     */
    @Data
    static class Attach {
        /**
         * 附件地址
         */
        private String url;
        /**
         * 附件名称
         */
        private String name;
    }
}

