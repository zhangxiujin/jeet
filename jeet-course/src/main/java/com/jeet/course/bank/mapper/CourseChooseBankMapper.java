package com.jeet.course.bank.mapper;

import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ChooseVo;
import com.jeet.course.check.vo.ChooseAnswerVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseChooseBankMapper {

    void insertCourseChooseBank(CourseChooseBank courseChooseBank);

    List<ChooseStructureVo> selectCourseChooseBank(ChooseVo chooseVo);

    void deleteChoose(Long id);

    void deleteChooseList(List<Long> list);

    ChooseStructureVo selectChoose(Long id);

    void updateChoose(CourseChooseBank courseChooseBank);

    List<ChooseStructureVo> selectChooseByContentId(ChooseVo chooseVo);

    List<ChooseStructureVo> selectChooseList(List<ChooseAnswerVo> list);

}
