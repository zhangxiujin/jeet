package com.jeet.course.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.utils.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.message.constant.MessageConstant;
import com.jeet.common.message.service.IMessageService;
import com.jeet.common.redis.service.RedisService;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.content.mapper.CourseContentBankMapper;
import com.jeet.course.content.vo.ContentSimpleVo;
import com.jeet.course.study.constant.BankTypeConstant;
import com.jeet.course.study.constant.Constant;
import com.jeet.course.study.domain.CoursePracticeRecord;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.study.domain.CourseStudyQuestion;
import com.jeet.course.study.mapper.CoursePracticeRecordMapper;
import com.jeet.course.study.service.ICourseStudyService;
import com.jeet.course.study.service.ICourseTeacherService;
import com.jeet.course.study.vo.CourseStudyProjectVo;
import com.jeet.course.study.vo.ChooseStudy;
import com.jeet.course.study.vo.*;
import com.jeet.system.api.RemoteUserService;
import com.jeet.system.api.domain.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseStudyServiceImpl implements ICourseStudyService {

    @Autowired
    private CoursePracticeRecordMapper coursePracticeRecordMapper;
    @Autowired
    private CourseContentBankMapper courseContentBankMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private ICourseTeacherService courseTeacherService;
    @Autowired
    private IMessageService messageService;

    @Override
    public CourseContent queryLearnById(Long id) {
        CourseContent courseContent = coursePracticeRecordMapper.selectList(id);
        return courseContent;
    }

    @Override
    public CourseContent queryLearn(Long id) {
        CourseContent contentVos = coursePracticeRecordMapper.selectList(id);
        return contentVos;
    }

    @Override
    public List<ProjectStructVo> queryStudyProject(ProjectVo projectVo) {
        return coursePracticeRecordMapper.selectStudyProject(projectVo);
    }

    @Override
    public void save(CourseStudyProjectVo courseStudyProjectVo) {
        try {
            Long courseId = coursePracticeRecordMapper.selectRootStructId(courseStudyProjectVo.getStructId());
            QueryPracticeVo queryPracticeVo = new QueryPracticeVo();
            queryPracticeVo.setContentId(courseStudyProjectVo.getContentId());
            queryPracticeVo.setType(courseStudyProjectVo.getBankType());
            queryPracticeVo.setUserId(SecurityUtils.getUserId());
            Integer number = coursePracticeRecordMapper.selectWordNumber(queryPracticeVo);
            courseStudyProjectVo.setId(IdUtil.nextId());
            courseStudyProjectVo.setContentId(courseStudyProjectVo.getContentId());
            courseStudyProjectVo.setCourseId(courseId);
            courseStudyProjectVo.setUserId(SecurityUtils.getUserId());
            courseStudyProjectVo.setBankType(courseStudyProjectVo.getBankType());
            courseStudyProjectVo.setNumber(number + 1);
            ObjectMapper objectMapper = new ObjectMapper();
            String answer = objectMapper.writeValueAsString(courseStudyProjectVo.getAnswersList());
            courseStudyProjectVo.setAnswers(answer);
            courseStudyProjectVo.setCreateTime(new Date(System.currentTimeMillis()));
            courseStudyProjectVo.setCheckStatus("0");
            coursePracticeRecordMapper.insertProject(courseStudyProjectVo);
            sendPracticeMessage(courseId, courseStudyProjectVo.getId(), BankTypeConstant.PROJECT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveWord(WordContentVo wordVo) throws JsonProcessingException {
        //查询某节课属于哪个课程
        Long courseId = coursePracticeRecordMapper.selectRootStructId(wordVo.getStructId());
        QueryPracticeVo queryPracticeVo = new QueryPracticeVo();
        Long userId = SecurityUtils.getUserId();
        queryPracticeVo.setType(wordVo.getWordType());
        queryPracticeVo.setContentId(wordVo.getContentId());
        queryPracticeVo.setUserId(userId);
        Integer number = coursePracticeRecordMapper.selectWordNumber(queryPracticeVo);
        List<WordVo> list = wordVo.getAnswers();
        Double score = 100D;
        Double averageScore = score / list.size();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getZhName().equals(list.get(i).getAnswer())) {
                list.get(i).setCorrect(Constant.CORRECT);
            }else {
                list.get(i).setCorrect(Constant.ERROR);
                score -= averageScore;
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String answer = objectMapper.writeValueAsString(list);
        CoursePracticeRecord coursePracticeRecord = new CoursePracticeRecord();
        coursePracticeRecord.setAnswers(answer);
        coursePracticeRecord.setCourseId(courseId);
        coursePracticeRecord.setScore(score.intValue());
        coursePracticeRecord.setNumber(number + 1);
        coursePracticeRecord.setUserId(userId);
        coursePracticeRecord.setContentId(wordVo.getContentId());
        coursePracticeRecord.setId(IdUtil.nextId());
        coursePracticeRecord.setBankType(wordVo.getWordType());
        coursePracticeRecord.setCreateTime(new Date(System.currentTimeMillis()));
        coursePracticeRecord.setCheckStatus("0");
        coursePracticeRecordMapper.insert(coursePracticeRecord);
        sendPracticeMessage(courseId, coursePracticeRecord.getId(), BankTypeConstant.WORD);
    }

    @Override
    public void saveSimple(SimpleContentVo simpleContentVo) throws JsonProcessingException {
        Long courseId = coursePracticeRecordMapper.selectRootStructId(simpleContentVo.getStructId());
        QueryPracticeVo queryPracticeVo = new QueryPracticeVo();
        CoursePracticeRecord coursePracticeRecord = new CoursePracticeRecord();
        coursePracticeRecord.setCourseId(courseId);
        Long userId = SecurityUtils.getUserId();
        queryPracticeVo.setType(simpleContentVo.getSimpleType());
        queryPracticeVo.setContentId(simpleContentVo.getContentId());
        queryPracticeVo.setUserId(userId);
        Integer number = coursePracticeRecordMapper.selectWordNumber(queryPracticeVo);
        List<SimpleAnswerVo> list = simpleContentVo.getAnswer();
        ObjectMapper objectMapper = new ObjectMapper();
        String answer = objectMapper.writeValueAsString(list);
        coursePracticeRecord.setAnswers(answer);
        coursePracticeRecord.setNumber(number + 1);
        coursePracticeRecord.setUserId(userId);
        coursePracticeRecord.setContentId(simpleContentVo.getContentId());
        coursePracticeRecord.setId(IdUtil.nextId());
        coursePracticeRecord.setBankType(simpleContentVo.getSimpleType());
        coursePracticeRecord.setCreateTime(new Date(System.currentTimeMillis()));
        coursePracticeRecord.setCheckStatus("0");
        coursePracticeRecordMapper.insert(coursePracticeRecord);
        sendPracticeMessage(courseId, coursePracticeRecord.getId(), BankTypeConstant.SIMPLE);
    }

    @Override
    public void saveChoose(ChooseStudy chooseStudy) throws JsonProcessingException {
        //查询某节课属于哪个课程
        Long courseId = coursePracticeRecordMapper.selectRootStructId(chooseStudy.getStructId());
        CoursePracticeRecord coursePracticeRecord = new CoursePracticeRecord();
        coursePracticeRecord.setId(IdUtil.nextId());
        coursePracticeRecord.setCourseId(courseId);
        coursePracticeRecord.setContentId(chooseStudy.getContentId());
        Long userId = SecurityUtils.getUserId();
        coursePracticeRecord.setUserId(userId);
        QueryPracticeVo queryPracticeVo = new QueryPracticeVo();
        queryPracticeVo.setContentId(chooseStudy.getContentId());
        queryPracticeVo.setType("1");  //1为选择题
        queryPracticeVo.setUserId(userId);
        Integer number = coursePracticeRecordMapper.selectWordNumber(queryPracticeVo);
        coursePracticeRecord.setNumber(number + 1);
        coursePracticeRecord.setBankType("1");  //选择题
        List<ChooseStructureVo> chooseStructureVos =
                coursePracticeRecordMapper.selectChooseList(chooseStudy.getContentId());
        Double score = 100D;  //满分100
        Double scorePerProblem = score / chooseStructureVos.size();
        Map<Long, List<String>> map = new HashMap<>();  //选择题库map集合
        for (int i = 0; i < chooseStructureVos.size(); i++) {
            ChooseStructureVo chooseStructureVo = chooseStructureVos.get(i);
            String answer = chooseStructureVo.getAnswer();
            List<String> answerList = new ArrayList<>();
            if(answer != null) {
                String[] answerArray = answer.split(",");
                for (String ans : answerArray) {
                    if(ans.equals("A")) {
                        answerList.add("option1");
                    } else if (ans.equals("B")) {
                        answerList.add("option2");
                    } else if (ans.equals("C")) {
                        answerList.add("option3");
                    } else if(ans.equals("D")) {
                        answerList.add("option4");
                    } else {
                        answerList.add("unknown");
                    }
                }
            }
            map.put(chooseStructureVo.getId(), answerList);
        }
        List<Answers> answersList = chooseStudy.getAnswersList();
        int sumScore = 0;
        if(answersList != null) {
            for (int i = 0; i < answersList.size(); i++) {
                Answers answers = answersList.get(i);
                List<String> correctAnswerList = map.get(answers.getBankId());
                List<String> options = answers.getOptions();
                if (options == null || options.size() != correctAnswerList.size()
                        || !options.containsAll(correctAnswerList)) {
                    //选择题回答错误
                    answers.setCorrect("0"); //0 错误
                } else {
                    sumScore += Math.ceil(scorePerProblem);
                    //选择题回答正确
                    answers.setCorrect("1"); //1 正确
                }
            }
            if(sumScore > score) {
                sumScore = score.intValue();
            }
        } else {
            answersList = new ArrayList<>();
        }
        coursePracticeRecord.setScore(sumScore);
        String answer = JSON.toJSONString(answersList);
        coursePracticeRecord.setAnswers(answer);
        coursePracticeRecord.setCreateTime(new Date(System.currentTimeMillis()));
        coursePracticeRecord.setCheckStatus("0");
        coursePracticeRecordMapper.insert(coursePracticeRecord);
        sendPracticeMessage(courseId, coursePracticeRecord.getId(), BankTypeConstant.CHOOSE);
    }

    @Override
    public List<ChooseStructureVo> queryChoose(Long contentId) {
        List<ChooseStructureVo> chooseStructureVos = coursePracticeRecordMapper.selectChooseList(contentId);
        return chooseStructureVos;
    }

    @Override
    public List<ContentSimpleVo> querySimpleList(Long contentId) {
        List<ContentSimpleVo> contentSimpleVos = courseContentBankMapper.selectSimple(contentId);
        return contentSimpleVos;
    }

    @Override
    public Long saveQuestion(CourseStudyQuestion courseStudyQuestion) {
        long questionId = IdUtil.nextId();
        courseStudyQuestion.setId(questionId);
        courseStudyQuestion.setStatus("0");
        courseStudyQuestion.setUserId(SecurityUtils.getUserId());
        courseStudyQuestion.setCreateTime(new Date(System.currentTimeMillis()));
        coursePracticeRecordMapper.insertQuestion(courseStudyQuestion);
        return questionId;
    }

    @Override
    public List<QuestionReplyVo> queryQuestionAnswers(CourseStudyQuestion courseStudyQuestion) {
       courseStudyQuestion.setUserId(SecurityUtils.getUserId());
        List<QuestionReplyVo> courseStudyQuestions =
                coursePracticeRecordMapper.selectQuestionAnswers(courseStudyQuestion);
        return courseStudyQuestions;
    }

    @Override
    public List<QuestionReplyVo> queryOtherQuestionAnswers(CourseStudyQuestion courseStudyQuestion) {
        courseStudyQuestion.setUserId(SecurityUtils.getUserId());
        List<QuestionReplyVo> questionReplyVos
                = coursePracticeRecordMapper.selectOtherQuestionAnswers(courseStudyQuestion);
        return questionReplyVos;
    }

    @Override
    public Long queryStructByQuesId(Long questionId) {
        return coursePracticeRecordMapper.selectStructByQuesId(questionId);
    }

    @Override
    public void cancelMsgBadge(CancelMsgBadgeVo cancelMsgBadgeVo) {
        Long teacherId = SecurityUtils.getUserId();
        redisService.deleteCacheSet("msg-badge-" + teacherId,
                cancelMsgBadgeVo.getStructId());
    }

    @Override
    public Long queryCourseId(Long contentId) {
        return coursePracticeRecordMapper.selectRootStructId(contentId);
    }

    /**
     * 发送练习通知消息
     * @param courseId 课程id
     * @param practiceId 练习id
     * @param type 练习题类型
     */
    private void sendPracticeMessage(Long courseId, Long practiceId, String type) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        //查询当前学生所关联的老师
        Long currentStudentId = SecurityUtils.getUserId();
        R<List<Long>> result = remoteUserService.queryTeachers(currentStudentId);
        //查询当前选择的课程关联的老师
        List<Long> teacherIds2 = courseTeacherService.queryTeachers(courseId);
        if(result != null && result.getCode() == 200) {
            List<Long> teacherIds = result.getData();
            //两个集合取交集
            Long teacher = new ArrayList<>(CollectionUtils.intersection(teacherIds2, teacherIds)).get(0);
            //发送提问待回复消息通知
            PracticeMessage practiceMessage = new PracticeMessage();
            practiceMessage.setPracticeId(practiceId);
            practiceMessage.setContent(BankTypeConstant.TYPE_MAP.get(type) + "练习已完成");
            practiceMessage.setType(MessageConstant.TYPE_ADVICE);
            practiceMessage.setDept(sysUser.getDept().getDeptName());
            practiceMessage.setNickname(sysUser.getNickName());
            practiceMessage.setSender(currentStudentId);
            messageService.send(teacher+"", JSON.toJSONString(practiceMessage));
        } else {
            throw new RuntimeException(result.getMsg());
        }
    }
}
