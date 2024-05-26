package com.jeet.common.datasource.config.tkmapper;

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zhangxiujin
 * @date 2021/5/28 14:28
 */
public interface CommonMapper<T> extends
        Mapper<T>,
        IdsMapper<T>,
        InsertListMapper<T>,
        ExampleMapper<T> {
}
