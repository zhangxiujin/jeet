package com.jeet.course.troubleshoot.vo;

import com.jeet.course.study.domain.CourseStudyReply;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SaveAnswerVo extends CourseStudyReply {
    /**
     * 提问者id
     */
    private Long questionUserId;
    /**
     * 回复的语音
     */
    private MultipartFile file;
}
