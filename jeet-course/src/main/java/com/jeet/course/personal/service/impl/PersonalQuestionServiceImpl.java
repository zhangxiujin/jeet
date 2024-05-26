package com.jeet.course.personal.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.personal.mapper.PersonalQuestionMapper;
import com.jeet.course.personal.service.IPersonalQuestionService;
import com.jeet.course.personal.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalQuestionServiceImpl implements IPersonalQuestionService {

    @Autowired
    private PersonalQuestionMapper personalQuestionMapper;

    @Override
    public List<QuestionVo> queryQuestion(QuestionVo questionVo) {
        Long userId = SecurityUtils.getUserId();
        questionVo.setUserId(String.valueOf(userId));
        List<QuestionVo> questionVos = personalQuestionMapper.selectQuestion(questionVo);
        for (int i = 0; i < questionVos.size(); i++) {
            QuestionVo questionVo1 = questionVos.get(i);
            String answers = questionVo1.getAnswers();
            JSONArray jsonArray = JSON.parseArray(answers);
            int errorNumber = 0;
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject o = (JSONObject) jsonArray.get(j);
                String correct = (String) o.get("correct");
                if (correct != null) {
                    if (correct.equals("0")) {
                        ++errorNumber;
                    }
                }
            }
            questionVo1.setErrorNumber(errorNumber);
        }
        return questionVos;
    }

    @Override
    public List<ErrorBankVo> queryErrorBank(PersonQuestion personQuestion) {
        PersonQuestion questionVos = personalQuestionMapper.selectAnswers(personQuestion);
        String answers = questionVos.getAnswers();  // "[{bankId:123, correct:'0'},[]]"
        JSONArray objects = JSON.parseArray(answers);
        //将bandId集合拿到
        Map<Long, ErrorBankVo> errorBankMap = new HashMap<>();
        String bankType = personQuestion.getBankType();
        //bankid
        List<Long> bankIdList = new ArrayList<>();
        if (bankType != null) {
            if (bankType.equals("0")) {
                return handleWord(objects);
            } else if (bankType.equals("1")) {
                return handleChoose(objects);
            } else if (bankType.equals("2")) {
                return handleProject(answers);
            } else if (bankType.equals("3")) {
                return handleSimple(objects);
            }
        }
        return null;
    }

    /**
     * 单词
     *
     * @param objects
     * @return
     */
    private List<ErrorBankVo> handleWord(JSONArray objects) {
        List<ErrorBankVo> wordErrorBankVoList = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject o = (JSONObject) objects.get(i);
            if (o.get("correct").equals("0")) {
                WordErrorBankVo wordErrorBankVo = new WordErrorBankVo();
                wordErrorBankVo.setEnName((String) o.get("enName"));
                wordErrorBankVo.setAnswer((String) o.get("zhName"));
                wordErrorBankVo.setWordAnswer((String) o.get("answer"));
                wordErrorBankVoList.add(wordErrorBankVo);
            }
        }
        return wordErrorBankVoList;
    }

    /**
     * 选择
     *
     * @param objects
     * @return
     */
    private List<ErrorBankVo> handleChoose(JSONArray objects) {
        Map<Long, ErrorBankVo> errorBankVoMap = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject o = (JSONObject) objects.get(i);
            Object bankId = o.get("bankId");
            JSONArray options = (JSONArray) o.get("options");
            String correct = (String) o.get("correct");
            ChooseErrorBankVo chooseErrorBankVo = new ChooseErrorBankVo();
            List<String> optionList = new ArrayList<>();
            if (options != null) {
                for (int i1 = 0; i1 < options.size(); i1++) {
                    String option = (String) options.get(i1);
                    optionList.add(option);
                }
            }
            chooseErrorBankVo.setBankId((Long) bankId);
            chooseErrorBankVo.setReply(optionList); //自己的选项
            chooseErrorBankVo.setCorrect(correct);
            errorBankVoMap.put((Long) bankId, chooseErrorBankVo);
        }
        Set<Long> longs = errorBankVoMap.keySet();
        Iterator<Long> iterator = longs.iterator();
        List<Long> bandIdList = new ArrayList<>();
        while (iterator.hasNext()) {
            bandIdList.add(iterator.next());
        }
        //查询所有的选择题
        List<ChooseErrorBankVo> chooseErrorBankVos = personalQuestionMapper.selectChoose(bandIdList);
        List<ErrorBankVo> chooseErrorBankVoList = new ArrayList<>();
        for (int j = 0; j < chooseErrorBankVos.size(); j++) {
            ChooseErrorBankVo chooseErrorBankVo1 = chooseErrorBankVos.get(j);
            ChooseErrorBankVo chooseErrorBankVo = (ChooseErrorBankVo) errorBankVoMap.get(chooseErrorBankVo1.getBankId());
            chooseErrorBankVo.setTitle(chooseErrorBankVo1.getTitle());
            chooseErrorBankVo.setType(chooseErrorBankVo1.getType());
            chooseErrorBankVo.setOptions(chooseErrorBankVo1.getOptions());
            chooseErrorBankVo.setCorrectAnswers(chooseErrorBankVo1.getCorrectAnswers());
            chooseErrorBankVoList.add(chooseErrorBankVo);
        }
        return chooseErrorBankVoList;
    }

    /**
     * 项目
     *
     * @return
     */
    private List<ErrorBankVo> handleProject(String answerJson) {
        Map<Long, ErrorBankVo> errorBankVoMap = new HashMap<>();
        List<ProjectPersonalVo> projectErrorBankVos = JSON.parseArray(answerJson, ProjectPersonalVo.class);
        for (ProjectPersonalVo projectErrorBankVo : projectErrorBankVos) {
            if(projectErrorBankVo.getCorrect() != null
                    && projectErrorBankVo.getCorrect().equals("0")) {   //不合格的项目往里面放
                errorBankVoMap.put(projectErrorBankVo.getBankId(), projectErrorBankVo);
            }
        }
        List<Long> bankIdList = errorBankVoMap.keySet().stream().collect(Collectors.toList());
        List<ProjectPersonalVo> selectProject = personalQuestionMapper.selectProject(bankIdList);
        ArrayList<ErrorBankVo> projectList = new ArrayList<>();
        for (int j = 0; j < selectProject.size(); j++) {
            ProjectPersonalVo projectErrorBankVo1 = selectProject.get(j);
            ProjectPersonalVo projectError = (ProjectPersonalVo) errorBankVoMap.get(projectErrorBankVo1.getBankId());
            projectError.setName(projectErrorBankVo1.getName());
            projectError.setDetails(projectErrorBankVo1.getDetails());
            projectList.add(projectError);
        }
        return projectList;
    }

    /**
     * 简答
     *
     * @param objects
     * @return
     */
    private List<ErrorBankVo> handleSimple(JSONArray objects) {
        Map<Long, ErrorBankVo> errorBankVoMap = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject o = (JSONObject) objects.get(i);
            Object bankId = o.get("bankId");
            Object inputAnswer = o.get("inputAnswer");
            SimpleErrorBankVo simpleErrorBankVo = new SimpleErrorBankVo();
            simpleErrorBankVo.setBankId((Long) bankId);
            simpleErrorBankVo.setInputAnswer((String) inputAnswer);
            errorBankVoMap.put((Long) bankId, simpleErrorBankVo);
        }
        Set<Long> longs = errorBankVoMap.keySet();
        Iterator<Long> iterator = longs.iterator();
        List<Long> bankIdList = new ArrayList<>();
        while (iterator.hasNext()) {
            bankIdList.add(iterator.next());
        }
        List<SimpleErrorBankVo> simpleErrorBankVos = personalQuestionMapper.selectSimple(bankIdList);
        List<ErrorBankVo> simpleErrorBankVoList = new ArrayList<>();
        for (int i1 = 0; i1 < simpleErrorBankVos.size(); i1++) {
            SimpleErrorBankVo simpleErrorBankVo1 = simpleErrorBankVos.get(i1);
            ErrorBankVo errorBankVo = errorBankVoMap.get(simpleErrorBankVo1.getBankId());
            SimpleErrorBankVo simpleErrorBankVoJson = JSONObject.parseObject(JSONObject.toJSONString(errorBankVo), SimpleErrorBankVo.class);
            simpleErrorBankVoJson.setSimpleName(simpleErrorBankVo1.getSimpleName());
            simpleErrorBankVoJson.setDetails(simpleErrorBankVo1.getDetails());
            simpleErrorBankVoList.add(simpleErrorBankVoJson);
        }
        return simpleErrorBankVoList;
    }
}