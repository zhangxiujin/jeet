package com.jeet.course.study.vo;

import com.jeet.course.study.domain.CourseStudyQuestion;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 保存问题vo实体
 * @author zhangxiujin
 */
@Getter
@Setter
public class SaveQuestionVo extends CourseStudyQuestion {
    /**
     * 上传的语音文件
     */
    private MultipartFile file;

    /**
     * 当前选中的结构id
     */
    private Long currentSelectedStructId;
}
