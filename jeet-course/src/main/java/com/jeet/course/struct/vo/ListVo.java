package com.jeet.course.struct.vo;

import lombok.Data;

/**
 * @Desc: ListVo
 * @author: 李宇乐
 * @Date: 2023/8/9 10:57
 */
@Data
public class ListVo {

    private String structName;  //结构名称
    private String type;   //结构类型
    private Long parentId;

}
