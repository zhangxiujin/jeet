package com.jeet.course.content.vo;

import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.bank.domain.CourseProjectBank;
import com.jeet.course.bank.domain.CourseWordBank;
import lombok.Data;

import java.util.List;

/**
 * @Desc: CourseContentVo
 * @author: xue
 * @Date: 2023/8/27 14:20
 */
@Data
public class CourseContentVo {

    /**
     * 单词题库
     */
    private List<ContentWordVo> wordBankList;

    /**
     * 选择题库
     */
    private List<ContentChooseVo> chooseBankList;

    /**
     * 项目题库
     */
    private List<ContentProjectVo> projectBankList;

    /**
     * 简答题库
     */
    private List<ContentSimpleVo> simpleBankList;

    /**
     * 内容id
     */
    private Long contentId;

    /**
     * 内容名称
     */
    private String contentName;

    /**
     * 内容描述
     */
    private String desc;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频扩展名
     */
    private String ext;

    /**
     * 视频id
     */
    private Long videoId;


}
