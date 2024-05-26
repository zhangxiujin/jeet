package com.jeet.task.api.vo;

import com.jeet.task.api.domain.CourseStudyRate;
import lombok.Data;

@Data
public class CourseStudyRateVo extends CourseStudyRate {

    /**
     * 视频已播放时长，单位毫秒
     */
    private Long playTimeLength;

    /**
     * 总时长，单位毫秒
     */
    private Long duration;

    /**
     * 是否已同步
     */
    private String isSync = "0";     //0是未同步，1是已同步

    /**
     * 更新或新增
     */
    private String isSave = "0";   //0是新增，1是更新
}
