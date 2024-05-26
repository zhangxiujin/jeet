package com.jeet.course.check.service;

import com.jeet.course.personal.vo.RateVo;

import java.util.List;

public interface IStudyRateService {

    List<RateVo> queryStudentRateTree(Long studentId);
}
