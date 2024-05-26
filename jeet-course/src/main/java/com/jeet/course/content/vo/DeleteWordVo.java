package com.jeet.course.content.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: DeleteWordVo
 * @author: xue
 * @Date: 2023/8/29 16:43
 */
@Data
public class DeleteWordVo {

    /**
     * 多选框选中的数据id
     */
    private List<Long> wordIdList;

    /**
     * 内容id
     */
    private Long wordContentId;

}
