package com.jeet.course.content.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: DeleteProjectVo
 * @author: xue
 * @Date: 2023/8/29 16:41
 */
@Data
public class DeleteProjectVo {

    /**
     * 多选框选中的数据id
     */
    private List<Long> projectIdList;

    /**
     * 内容id
     */
    private Long projectContentId;

}
