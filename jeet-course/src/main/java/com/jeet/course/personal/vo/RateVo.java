package com.jeet.course.personal.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc: RateVo
 * @author: xue
 * @Date: 2023/9/20 16:00
 */
@Getter
@Setter
public class RateVo {

    /**
     * 课程id
     */
    private Long contentId;

    /**
     * 节点id
     */
    private Long structId;

    /**
     * 学习进度id
     */
    private Long rateId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 课程名称
     */
    private String structName;

    /**
     * 课程类型
     */
    private String type;

    /**
     * 父课程id
     */
    private Long parentId;

    /**
     * 父节点
     */
    @JsonIgnore
    private RateVo parent;

    /**
     * 是否播放完成
     */
    private String isPlayComplete = "0";

    /**
     * 学习进度
     */
    private String studyRate;

    /**
     * 节数
     */
    private Integer total = 0;

    /**
     * 学习完成的数
     */
    private Integer learned = 0;

    /**
     * 学习时间
     */
    private Date createTime;

    /**
     * 子节点
     */
    private List<RateVo> children = new ArrayList<>();


    public String getStudyRate() {
        if(type.equals("4")) {
            return studyRate;
        }
        return getLearned() + " / " + getTotal();
    }

    /** 保留
    public String getIsPlayComplete() {
        if (children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).getIsPlayComplete().equals("0")) {
                    return "0";
                }
            }
            return "1";
        } else {
            if (!type.equals("4")) {
                return "1";
            }
            return isPlayComplete;
        }
    }
     */

}
