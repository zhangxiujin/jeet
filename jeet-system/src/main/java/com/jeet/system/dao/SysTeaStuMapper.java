package com.jeet.system.dao;

import com.jeet.system.api.domain.SysTeaStu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 老师学生关联mapper接口
 * @author zhangxiujin
 */
public interface SysTeaStuMapper {

    void insertList(@Param(value = "sysTeaStuList") List<SysTeaStu> sysTeaStuList);

    void delete(Long teacherId);

    List<Long> selectTeachers(Long studentId);

    List<Long> selectStudents(@Param("teacherId") Long teacherId);
}
