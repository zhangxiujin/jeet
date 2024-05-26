package com.jeet.course.check.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.utils.PageUtils;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.bank.domain.CourseBankAttachment;
import com.jeet.course.bank.mapper.*;
import com.jeet.course.bank.vo.*;
import com.jeet.course.check.mapper.CoursePracticeRecordMappers;
import com.jeet.course.check.service.ICheckPracticeService;
import com.jeet.course.check.vo.*;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.mapper.CourseContentMapper;
import com.jeet.course.study.domain.CoursePracticeRecord;
import com.jeet.course.study.mapper.CoursePracticeRecordMapper;
import com.jeet.course.study.mapper.CourseTeaCouMapper;
import com.jeet.course.study.vo.WordContentVo;
import com.jeet.course.study.vo.WordVo;
import com.jeet.system.api.RemoteUserService;
import com.mysql.cj.xdevapi.JsonArray;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Desc: CheckPracticeServiceImpl
 * @author: xue
 * @Date: 2023/9/7 15:01
 */
@Service
public class CheckPracticeServiceImpl implements ICheckPracticeService {

    @Autowired
    private CourseContentMapper courseContentMapper;
    @Autowired
    private CoursePracticeRecordMappers coursePracticeRecordMappers;
    @Autowired
    private CourseSimpleBankMapper courseSimpleBankMapper;
    @Autowired
    private CourseProjectBankMapper courseProjectBankMapper;
    @Autowired
    private CourseChooseBankMapper courseChooseBankMapper;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private CourseTeaCouMapper courseTeaCouMapper;

    @Override
    public List<PracticeUserVo> queryPracticeUser(PracticeVo practiceVo) {
        CourseContent content = courseContentMapper.select(practiceVo.getStructId());
        if(content != null) {
            practiceVo.setContentId(content.getId());
            return coursePracticeRecordMappers.selectPracticeUser(practiceVo);
        }
        return new ArrayList<>();
    }

    @Override
    public List<PracticeRecordVo> queryPractice(PracticeVo practiceVo) {
        List<PracticeRecordVo> practiceRecordVos =
                coursePracticeRecordMappers.selectPractice(practiceVo);
        if(practiceRecordVos != null) {
            for (int i = 0; i < practiceRecordVos.size(); i++) {
                if (practiceRecordVos.get(i).getCheckStatus().equals("0")) {
                    practiceRecordVos.get(i).setCheckStatus("未审核");
                }else if (practiceRecordVos.get(i).getCheckStatus().equals("1")) {
                    practiceRecordVos.get(i).setCheckStatus("已审核");
                }
            }
        }
        return practiceRecordVos;
    }

    @Override
    public List<PracticeRecordVo> queryUnCheckPractice(
            QueryPracticeRecordsVo practiceRecord) {
        //查询当前登录的老师id
        Long teacherId = SecurityUtils.getUserId();
        if(practiceRecord.getUserId() == null) {
            R<List<Long>> r = remoteUserService.queryStudents(teacherId);
            if(r != null && r.getCode() == 200) {
                List<Long> studentIds = r.getData();
                practiceRecord.setStudentIds(studentIds);
            }
        }
        if(practiceRecord.getStructId() == null) {
            List<Long> courseIds = courseTeaCouMapper.selectCourseTeaCouList(teacherId);
            practiceRecord.setCourseIds(courseIds);
        }
        PageUtils.startPage();
        practiceRecord.setCheckStatus("0");
        List<PracticeRecordVo> practiceRecordVos =
                coursePracticeRecordMappers.selectPracticeRecords(practiceRecord);
        return practiceRecordVos;
    }

    @Override
    public List<PracticeRecordVo> queryCheckedPractice(QueryPracticeRecordsVo practiceRecord) {
        //查询当前登录的老师id
        Long teacherId = SecurityUtils.getUserId();
        if(practiceRecord.getUserId() == null) {
            R<List<Long>> r = remoteUserService.queryStudents(teacherId);
            if(r != null && r.getCode() == 200) {
                List<Long> studentIds = r.getData();
                practiceRecord.setStudentIds(studentIds);
            }
        }
        if(practiceRecord.getStructId() == null) {
            List<Long> courseIds = courseTeaCouMapper.selectCourseTeaCouList(teacherId);
            practiceRecord.setCourseIds(courseIds);
        }
        PageUtils.startPage();
        practiceRecord.setCheckStatus("1");
        practiceRecord.setSort("desc");
        List<PracticeRecordVo> practiceRecordVos =
                coursePracticeRecordMappers.selectPracticeRecords(practiceRecord);
        return practiceRecordVos;
    }

    @Override
    public Object queryPracticeDetails(PracticeVo practiceVo) {
        if (practiceVo.getType().equals("0")) {
            CoursePracticeRecord coursePracticeRecord =
                    coursePracticeRecordMappers.selectById(practiceVo.getId());
            String answers = coursePracticeRecord.getAnswers();
            List<JSONObject> wordVos = JSONObject.parseObject(answers, List.class);
            ArrayList<WordVo> list = new ArrayList<>();
            for (int i = 0; i < wordVos.size(); i++) {
                WordVo wordVo = new WordVo();
                wordVo.setEnName((String) wordVos.get(i).get("enName"));
                wordVo.setZhName((String) wordVos.get(i).get("zhName"));
                wordVo.setAnswer((String) wordVos.get(i).get("answer"));
                wordVo.setCorrect((String) wordVos.get(i).get("correct"));
                list.add(wordVo);
            }
            WordContentVo wordContentVo = new WordContentVo();
            wordContentVo.setAnswers(list);
            wordContentVo.setScore(coursePracticeRecord.getScore());
            wordContentVo.setComment(coursePracticeRecord.getComment());
            return wordContentVo;
        }else {
            CoursePracticeRecord coursePracticeRecord =
                    coursePracticeRecordMappers.selectById(practiceVo.getId());
            String answers = coursePracticeRecord.getAnswers();
            if (practiceVo.getType().equals("1")) {
               return queryChoose(answers, coursePracticeRecord.getScore(),coursePracticeRecord.getComment());
            } else if (practiceVo.getType().equals("2")) {
                return queryProject(answers, coursePracticeRecord.getScore(),coursePracticeRecord.getComment());
            } else if (practiceVo.getType().equals("3")) {
                return querySimple(answers, coursePracticeRecord.getScore(),coursePracticeRecord.getComment());
            }
            return null;
        }
    }

    @Override
    public void modifyWord(WordContentVo wordContentVo) {
        CoursePracticeRecord coursePracticeRecord = new CoursePracticeRecord();
        List<WordVo> answers = wordContentVo.getAnswers();
        Integer averageScore = 100 / answers.size();
        Integer score = 100;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).getCorrect().equals("0")) {
                score -= averageScore;
            }
        }
        coursePracticeRecord.setScore(score);
        String answer = JSON.toJSONString(answers);
        coursePracticeRecord.setAnswers(answer);
        coursePracticeRecord.setBankType("0");
        coursePracticeRecord.setCheckStatus("1");
        coursePracticeRecord.setId(wordContentVo.getId());
        coursePracticeRecord.setComment(wordContentVo.getComment());
        coursePracticeRecordMappers.updatePractice(coursePracticeRecord);
    }

    @Override
    public void modifySimple(PracticeProjectVo practiceProjectVo) {
        CoursePracticeRecord coursePracticeRecord =
                coursePracticeRecordMappers.selectById(practiceProjectVo.getId());
        String answers = coursePracticeRecord.getAnswers();
        List<JSONObject> bankAnswersVos = JSON.parseObject(answers, List.class);
        for (int i = 0; i < bankAnswersVos.size(); i++) {
            if (bankAnswersVos.get(i).get("bankId").equals
                    (practiceProjectVo.getCorrect().get(i).getBankId())) {
                bankAnswersVos.get(i).put("correct", practiceProjectVo.getCorrect().get(i).getCorrect());
            }
        }
        String answer = JSON.toJSONString(bankAnswersVos);
        CoursePracticeRecord coursePracticeRecord1 = new CoursePracticeRecord();
        coursePracticeRecord1.setScore(practiceProjectVo.getScore());
        coursePracticeRecord1.setAnswers(answer);
        coursePracticeRecord1.setCheckStatus("1");
        coursePracticeRecord1.setId(practiceProjectVo.getId());
        coursePracticeRecord1.setComment(practiceProjectVo.getComment());
        coursePracticeRecordMappers.updatePractice(coursePracticeRecord1);
    }

    @Override
    public void modifyProject(PracticeProjectVo practiceProjectVo) {
        CoursePracticeRecord coursePracticeRecord =
                coursePracticeRecordMappers.selectById(practiceProjectVo.getId());
        String answers = coursePracticeRecord.getAnswers();
        List<BankAnswersVo> bankAnswersVos = JSON.parseArray(answers, BankAnswersVo.class);
        Map<Long, BankAnswersVo> map = new HashMap<>();
        for (int i = 0; i < bankAnswersVos.size(); i++) {
            map.put(bankAnswersVos.get(i).getBankId(), bankAnswersVos.get(i));
        }
        List<ScoreVo> list = practiceProjectVo.getCorrect();
        for (int i = 0; i < list.size(); i++) {
            BankAnswersVo bankAnswersVo = map.get(list.get(i).getBankId());
            bankAnswersVo.setCorrect(list.get(i).getCorrect());
        }
        String answer = JSON.toJSONString(bankAnswersVos);
        CoursePracticeRecord coursePracticeRecord1 = new CoursePracticeRecord();
        coursePracticeRecord1.setScore(practiceProjectVo.getScore());
        coursePracticeRecord1.setAnswers(answer);
        coursePracticeRecord1.setCheckStatus("1");
        coursePracticeRecord1.setId(practiceProjectVo.getId());
        coursePracticeRecord1.setComment(practiceProjectVo.getComment());
        coursePracticeRecordMappers.updatePractice(coursePracticeRecord1);
    }

    @Override
    public void modifyChoose(ChoosePracticeVo choosePracticeVo) {
        List<ChooseStructureVo> answer = choosePracticeVo.getAnswer();
        CoursePracticeRecord coursePracticeRecord = new CoursePracticeRecord();
        ArrayList<ChooseAnswerVo> list = new ArrayList<>();
        Integer averageScore = 100/answer.size();
        Integer score = 100;
        for (int i = 0; i < answer.size(); i++) {
            ChooseAnswerVo chooseAnswerVo = new ChooseAnswerVo();
            chooseAnswerVo.setOptions(answer.get(i).getInputAnswer());
            chooseAnswerVo.setBankId(answer.get(i).getId());
            chooseAnswerVo.setCorrect(answer.get(i).getCorrect());
            list.add(chooseAnswerVo);
            if (answer.get(i).getCorrect().equals("0")) {
                score -= averageScore;
            }
        }
        String answers = JSON.toJSONString(list);
        coursePracticeRecord.setAnswers(answers);
        coursePracticeRecord.setCheckStatus("1");
        coursePracticeRecord.setScore(score);
        coursePracticeRecord.setId(choosePracticeVo.getId());
        coursePracticeRecord.setComment(choosePracticeVo.getComment());
        coursePracticeRecordMappers.updatePractice(coursePracticeRecord);
    }

    private List<ChooseStructureVo> queryChoose(String answers, Integer score,String comment) {
        Map<Long, ChooseStructureVo> map = new HashMap<>();
        List<ChooseAnswerVo> bankAnswersVo = JSON.parseArray(answers, ChooseAnswerVo.class);
        for (int i = 0; i < bankAnswersVo.size(); i++) {
            ChooseStructureVo chooseStructureVo = new ChooseStructureVo();
            chooseStructureVo.setInputAnswer(bankAnswersVo.get(i).getOptions());
            chooseStructureVo.setCorrect(bankAnswersVo.get(i).getCorrect());
            map.put(bankAnswersVo.get(i).getBankId(), chooseStructureVo);
        }
        List<ChooseStructureVo> chooseStructureVos = courseChooseBankMapper.selectChooseList(bankAnswersVo);
        for (int i = 0; i < chooseStructureVos.size(); i++) {
            ChooseStructureVo chooseStructureVo = chooseStructureVos.get(i);
            ChooseStructureVo chooseStructureVo1 = map.get(chooseStructureVo.getId());
            chooseStructureVo1.setAnswer(chooseStructureVo.getAnswer());
            chooseStructureVo1.setId(chooseStructureVo.getId());
            chooseStructureVo1.setScore(score);
            chooseStructureVo1.setTitle(chooseStructureVo.getTitle());
            chooseStructureVo1.setOptions(chooseStructureVo.getOptions());
            chooseStructureVo1.setType(chooseStructureVo.getType());
            chooseStructureVo1.setComment(comment);
        }
        List<ChooseStructureVo> collect = map.values().stream().collect(Collectors.toList());
        return collect;
    }

    private List<SimpleVo> querySimple(String answers, Integer score, String comment) {
        Map<Long, SimpleVo> map = new HashMap<>();
        List<JSONObject> bankAnswersVo = JSON.parseObject(answers, List.class);
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < bankAnswersVo.size(); i++) {
            SimpleVo simpleVo = new SimpleVo();
            map.put((Long) bankAnswersVo.get(i).get("bankId"), simpleVo);
            list.add((Long) bankAnswersVo.get(i).get("bankId"));
        }
        List<SimpleVo> simpleVos = courseSimpleBankMapper.selectSimpleList(list);
        for (int i = 0; i < simpleVos.size(); i++) {
            SimpleVo simpleVo = map.get(simpleVos.get(i).getId());
            simpleVo.setAnswerInput((String) bankAnswersVo.get(i).get("inputAnswer"));
            simpleVo.setScore(score);
            simpleVo.setCorrect((String) bankAnswersVo.get(i).get("correct"));
            simpleVo.setName(simpleVos.get(i).getName());
            simpleVo.setId(simpleVos.get(i).getId());
            simpleVo.setDetails(simpleVos.get(i).getDetails());
            simpleVo.setComment(comment);
        }
        List<SimpleVo> collect = map.values().stream().collect(Collectors.toList());
        return collect;
    }

    private List<ProjectBankVo> queryProject(String answers, Integer score, String comment) {
        Map<Long, ProjectBankVo> map = new HashMap<>();
        List<BankAnswersVo> bankAnswersVos = JSON.parseArray(answers, BankAnswersVo.class);
        ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < bankAnswersVos.size(); i++) {
            ProjectBankVo projectBankVo = new ProjectBankVo();
            projectBankVo.setProjectInput(bankAnswersVos.get(i).getAttachUrls());
            projectBankVo.setCorrect(bankAnswersVos.get(i).getCorrect());
            map.put(bankAnswersVos.get(i).getBankId(), projectBankVo);
            list.add(bankAnswersVos.get(i).getBankId());
        }
        List<ProjectBankVo> projectBankVos = courseProjectBankMapper.selectProjectList(list);
        for (int i = 0; i < projectBankVos.size(); i++) {
            ProjectBankVo projectBankVo = map.get(projectBankVos.get(i).getId());
            projectBankVo.setScore(score);
            projectBankVo.setName(projectBankVos.get(i).getName());
            projectBankVo.setId(projectBankVos.get(i).getId());
            projectBankVo.setDetails(projectBankVos.get(i).getDetails());
            projectBankVo.setComment(comment);
            projectBankVo.setAttachList(projectBankVos.get(i).getAttachList());
        }
        List<ProjectBankVo> collect = map.values().stream().collect(Collectors.toList());
        return collect;
    }


}
