package com.jeet.course.content.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: DeleteChooseVo
 * @author: xue
 * @Date: 2023/8/29 16:42
 */
@Data
public class DeleteChooseVo {

    /**
     * 多选框选中的数据id
     */
    private List<Long> ChooseIdList;

    /**
     * 内容id
     */
    private Long ChooseContentId;

}
