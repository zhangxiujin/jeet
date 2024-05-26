package com.jeet.course.content.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: DeleteSimpleVo
 * @author: xue
 * @Date: 2023/9/4 12:03
 */
@Data
public class DeleteSimpleVo {

    /**
     * 多选框选中的数据id
     */
    private List<Long> simpleIdList;

    /**
     * 内容id
     */
    private Long simpleContentId;

}
