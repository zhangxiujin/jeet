package com.jeet.course.study.mapper;

import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.vo.ContentVo;
import com.jeet.course.study.domain.CoursePracticeRecord;
import com.jeet.course.study.domain.CourseStudyQuestion;
import com.jeet.course.study.vo.CourseStudyProjectVo;
import com.jeet.course.study.vo.QueryPracticeVo;
import com.jeet.course.study.vo.QuestionReplyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePracticeRecordMapper {

    CourseContent selectList(Long structId);

    /**
     * 查询数据
     * @param projectVo
     * @return
     */
    List<ProjectStructVo> selectStudyProject(ProjectVo projectVo);

    /**
     * 新增
     */
    void insertProject(CourseStudyProjectVo courseStudyProjectVo);

    ContentVo selectListById(Long id);

    /**
     * 新增练习信息
     * @param coursePracticeRecord
     */
    void insert(CoursePracticeRecord coursePracticeRecord);

    /**
     * 查询数量
     */
    Integer selectWordNumber(QueryPracticeVo queryPracticeVo);

    /**
     * 查询选择题
     * @param contentId
     * @return
     */
    List<ChooseStructureVo> selectChooseList(Long contentId);

    /**
     * 保存疑问数据
     * @param courseStudyQuestion
     */
    void insertQuestion(CourseStudyQuestion courseStudyQuestion);

    /**
     * 查询疑问回答数据
     * @param courseStudyQuestion
     * @return
     */
    List<QuestionReplyVo> selectQuestionAnswers(CourseStudyQuestion courseStudyQuestion);

    /**
     * 查询其他人疑问回答数据
     * @param courseStudyQuestion
     * @return
     */
    List<QuestionReplyVo> selectOtherQuestionAnswers(CourseStudyQuestion courseStudyQuestion);

    /**
     * 通过问题id查询所属结构
     * @param questionId  问题id
     * @return 结构id
     */
    Long selectStructByQuesId(@Param("questionId") Long questionId);

    /**
     * 通过子结构id查询根结构id
     */
    Long selectRootStructId(Long childStructId);

}
