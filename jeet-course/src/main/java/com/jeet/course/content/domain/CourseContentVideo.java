package com.jeet.course.content.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 课程内容视频实体类
 */
@Data
public class CourseContentVideo extends BaseEntity {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 内容id
     */
    private Long contentId;
    /**
     * 视频地址
     */
    private String url;
    /**
     * 视频名称
     */
    private String name;
    /**
     * 视频类型 (.mp4,.wav)
     */
    private String ext;
}
