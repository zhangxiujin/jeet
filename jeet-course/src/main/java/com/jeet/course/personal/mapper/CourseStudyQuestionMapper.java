package com.jeet.course.personal.mapper;

import com.jeet.course.personal.vo.DoubtVo;
import com.jeet.course.study.domain.CourseStudyQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseStudyQuestionMapper {

    /**
     * 查询所有数据
     * @param currentUserId 当前登录人id
     * @return
     */
    List<DoubtVo> selectList(@Param("currentUserId") Long currentUserId, @Param("status") String status);

    /**
     * 新增数据
     */
    void insertQuestion(CourseStudyQuestion courseStudyQuestion);

    /**
     * 条件查询
     * @param contentId  课程内容id
     * @param userId 提问人id
     */
    List<DoubtVo> selectDoubt(@Param("contentId") Long contentId,
                              @Param("userId") Long userId, @Param("status") String status);

}
