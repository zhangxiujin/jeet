package com.jeet.course.study.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.vo.ContentSimpleVo;
import com.jeet.course.study.domain.CourseStudyQuestion;
import com.jeet.course.study.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;



public interface ICourseStudyService {

    CourseContent queryLearnById(Long id);

    CourseContent queryLearn(Long id);

    /**
     * 查询方法
     * @param projectVo
     * @return
     */
    List<ProjectStructVo> queryStudyProject(ProjectVo projectVo);

    /**
     * 保存方法
     * @param courseStudyProjectVo
     */
    void save(CourseStudyProjectVo courseStudyProjectVo);
    /**
     * 新增单词练习数据
     * @param wordVo
     */
    void saveWord(WordContentVo wordVo) throws JsonProcessingException;

    /**
     * 新增简答练习数据
     */
    void saveSimple(SimpleContentVo simpleContentVo) throws JsonProcessingException;

    /**
     * 新增选择联系数据
     * @param chooseStudy
     */
    void saveChoose(ChooseStudy chooseStudy) throws JsonProcessingException;

    /**
     * 查询选额题数据
     * @param contentId
     * @return
     */
    List<ChooseStructureVo> queryChoose(Long contentId);

    /**
     * 查询某个内容下的简单题
     */
    List<ContentSimpleVo> querySimpleList(Long contentId);

    /**
     * 保存疑问
     * @param courseStudyQuestion
     */
    Long saveQuestion(CourseStudyQuestion courseStudyQuestion);

    /**
     * 查询疑问回答
     * @param courseStudyQuestion
     * @return
     */
    List<QuestionReplyVo> queryQuestionAnswers(CourseStudyQuestion courseStudyQuestion);

    /**
     * 查询疑问回答
     * @param courseStudyQuestion
     * @return
     */
    List<QuestionReplyVo> queryOtherQuestionAnswers(CourseStudyQuestion courseStudyQuestion);

    /**
     * 通过问题id查询所属结构
     * @param questionId  问题id
     * @return 结构id
     */
    Long queryStructByQuesId(Long questionId);

    /**
     * 取消消息标记(红点)
     * @param cancelMsgBadgeVo
     */
    void cancelMsgBadge(CancelMsgBadgeVo cancelMsgBadgeVo);

    /**
     * 通过课程内容id，查询课程id
     */
    Long queryCourseId(Long contentId);

}

