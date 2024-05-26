package com.jeet.task.api.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: TreeVo
 * @author: 李宇乐
 * @Date: 2023/8/11 10:19
 */
@Data
public class TreeVo {

    private Long id;
    private String label;
    private List<TreeVo> children = new ArrayList<>();

}
