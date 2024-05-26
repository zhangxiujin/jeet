package com.jeet.course.personal.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.personal.constant.QualifiedConstant;
import com.jeet.course.personal.mapper.TestRecordMapper;
import com.jeet.course.personal.service.TestRecordService;
import com.jeet.course.personal.vo.*;
import com.jeet.course.study.domain.CoursePracticeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人中心 - 测考记录
 * @FileName : testRecordServiceImpl
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/7 16:11
 * @Version : 1.0.0
 */
@Service
public class TestRecordServiceImpl implements TestRecordService {

    @Autowired
    private TestRecordMapper testRecordMapper;

    @Override
    public List<CoursePracticeRecord> query(TestRecordVo testRecordVo) {
        testRecordVo.setUserId(SecurityUtils.getUserId());
        List<CoursePracticeRecord> coursePracticeRecords = testRecordMapper.selectTest(testRecordVo);
        return coursePracticeRecords;
    }


    /**
     * 单词
     * @param recordId
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<WordTestVo> queryWorkBankId(Long recordId) throws JsonProcessingException {
        String jsonString = testRecordMapper.selectTestAnswer(recordId);
        List<WordTestVo> wordVoList = new ArrayList<>();
            JSONArray jsonArray = JSON.parseArray(jsonString);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            WordTestVo wordTestVo = new WordTestVo();
            wordTestVo.setEhName(jsonObject.getString("enName"));
            wordTestVo.setZhName(jsonObject.getString("zhName"));
            wordTestVo.setAnswer(jsonObject.getString("answer"));
            wordTestVo.setIsSuccess(jsonObject.getString("isSuccess"));
            wordVoList.add(wordTestVo);
        }
        return wordVoList;
    }

    /**
     * 选择
     * @param recordId
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<BankErrorVo> queryChooseId(Long recordId) throws JsonProcessingException {
        String jsonString = testRecordMapper.selectTestAnswer(recordId);
        List bankIdList = new ArrayList();
        Map<Long, BankErrorVo> chooseErrorBankVoMap = new HashMap<>();
            JSONArray jsonArray = JSON.parseArray(jsonString);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject)jsonArray.get(i);
            ErrorChooseBankVo errorBankVo = new ErrorChooseBankVo();
            Long bankId = jsonObject.getLong("bankId");
            JSONArray optionsArray = jsonObject.getJSONArray("options");
            List<String> optionsList = new ArrayList<>();
            if (optionsArray != null){
                for (int j  = 0; j <  optionsArray.size(); j++) {
                    String options = (String)optionsArray.get(j);
                    optionsList.add(options);
                    System.out.println(options);
//                String json = chooseErrorBankVoMap.get(errorBankVo.getBankId());
//                System.out.println(json);
                }
            }
            errorBankVo.setBankId(bankId);
            errorBankVo.setAnswers(optionsList);
            chooseErrorBankVoMap.put(bankId,errorBankVo);
        }
        Set<Long> longs = chooseErrorBankVoMap.keySet();
        Iterator<Long> iterator = longs.iterator();
        while (iterator.hasNext()){
            bankIdList.add(iterator.next());
        }
        System.out.println(bankIdList);
        List<ErrorChooseBankVo> errorChooseBankVos = testRecordMapper.selectChooseId(bankIdList);
        System.out.println(errorChooseBankVos);
        System.out.println(chooseErrorBankVoMap);
        List<BankErrorVo> chooseErrorBankVoList = new ArrayList<>();
        for (int i = 0; i < errorChooseBankVos.size(); i++) {
            ErrorChooseBankVo chooseErrorBankVo1 = errorChooseBankVos.get(i);
            ErrorChooseBankVo chooseErrorBankVo = (ErrorChooseBankVo)chooseErrorBankVoMap.get(chooseErrorBankVo1.getBankId());
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
     * @param recordId
     * @return
     */
    @Override
    public List<BankErrorVo> queryProjectId(Long recordId)   {
        String answers = testRecordMapper.selectTestAnswer(recordId);
        Map<Long, BankErrorVo> errorBankVoMap = new HashMap<>();
        List<ProjectErrorBankVo> projectErrorBankVos1 = JSON.parseArray(answers, ProjectErrorBankVo.class);
        for (ProjectErrorBankVo key :  projectErrorBankVos1) {
            errorBankVoMap.put(key.getBankId(), key);
        }
        List<Long> bankIdList = errorBankVoMap.keySet().stream().collect(Collectors.toList());
        List<ProjectErrorBankVo> projectErrorBankVos = testRecordMapper.selectProjectId(bankIdList);
        List<BankErrorVo> projectErrorBankVoList = new ArrayList<>();
        for (int i = 0; i < projectErrorBankVos.size(); i++) {
            ProjectErrorBankVo projectErrorBankVo = projectErrorBankVos.get(i);
            ProjectErrorBankVo projectErrorBankVo1 = (ProjectErrorBankVo) errorBankVoMap.get(projectErrorBankVo.getBankId());
            projectErrorBankVo1.setName(projectErrorBankVo.getName());
            projectErrorBankVo1.setDetails(projectErrorBankVo.getDetails());
            projectErrorBankVo1.setQuesAttachList(projectErrorBankVo.getQuesAttachList());
            projectErrorBankVoList.add(projectErrorBankVo1);
        }
        return projectErrorBankVoList;
    }

    /**
     * 简答
     * @param recordId
     * @return
     */
    @Override
    public List<BankErrorVo> querySimId(Long recordId) {
        String jsonString = testRecordMapper.selectTestAnswer(recordId);
        System.out.println(jsonString);
        List bankIdList = new ArrayList();
        Map<Long, BankErrorVo> errorBankVoMap = new HashMap<>();
        JSONArray jsonArray = JSON.parseArray(jsonString);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            SimErrorVo simErrorVo = new SimErrorVo();
            Long bankId = jsonObject.getLong("bankId");
            String inputAnswer = jsonObject.getString("inputAnswer");
            System.out.println(inputAnswer);
            simErrorVo.setBankId(bankId);
            simErrorVo.setInputAnswer(inputAnswer);
            bankIdList.add(bankId);
                errorBankVoMap.put(bankId, simErrorVo);
        }
        Set<Long> longs = errorBankVoMap.keySet();
        System.out.println(bankIdList);
        List<SimErrorVo> simErrorVos = testRecordMapper.selectSimId(bankIdList);
        System.out.println(simErrorVos);
        List<BankErrorVo> simError = new ArrayList<>();
        for (int i = 0; i < simErrorVos.size(); i++) {
            SimErrorVo simErrorVo = simErrorVos.get(i);
            System.out.println(simErrorVo);
            BankErrorVo bankErrorVo = errorBankVoMap.get(simErrorVo.getBankId());
            SimErrorVo simErrorVo1 = (SimErrorVo) errorBankVoMap.get(simErrorVo.getBankId());
//            SimErrorVo simErrorVo1 = JSONObject.parseObject(JSONObject.toJSONString(bankErrorVo), SimErrorVo.class);
            simErrorVo.setBankId(simErrorVo1.getBankId());
            simErrorVo1.setSimpleName(simErrorVo.getSimpleName());
            simErrorVo.setInputAnswer(simErrorVo1.getInputAnswer());
            simError.add(simErrorVo);
        }
        return simError;
    }

    /**
     * 选择具体走那个方法
     * @param testRecordVo
     * @throws JsonProcessingException
     */
    @Override
    public List select(TestRecordVo testRecordVo) throws JsonProcessingException {
        Long recordId = testRecordVo.getRecordId();
        if (testRecordVo.getType().equals("0")){
            List<WordTestVo> wordTestVos = queryWorkBankId(recordId);
            return wordTestVos;
        }else if (testRecordVo.getType().equals("1")){
            List<BankErrorVo> errorBankVos = queryChooseId(recordId);
            return errorBankVos;
        }else if (testRecordVo.getType().equals("2")){
            List<BankErrorVo> bankErrorVos = queryProjectId(recordId);
            return bankErrorVos;
        }else if (testRecordVo.getType().equals("3")){
            List<BankErrorVo> bankErrorVos = querySimId(recordId);
            return bankErrorVos;
        }
        return null;
    }

    @Override
    public JSONObject queryPracticeIfQualified() {
        Long currentUserId = SecurityUtils.getUserId();
        List<PracticeScoresVo> practiceScoresVos = testRecordMapper.selectPracticeScores(currentUserId);
        Map<String, Boolean> qualifiedMap = new HashMap<>();
        if(practiceScoresVos != null && practiceScoresVos.size() > 0) {
            for (int i = 0; i < practiceScoresVos.size(); i++) {
                PracticeScoresVo practiceScoresVo = practiceScoresVos.get(i);
                List<PracticeScoresVo.BankScore> bankScoreList = practiceScoresVo.getBankScoreList();
                Boolean qualified = true;
                if(bankScoreList.size() == 4) {  //4种题都存在
                    for (int j = 0; j < bankScoreList.size(); j++) {
                        Integer score = bankScoreList.get(j).getScore();
                        if(score < QualifiedConstant.qualifiedScore) {
                            qualified = false;
                            break;
                        }
                    }
                } else {
                    qualified = false;
                }
                qualifiedMap.put("cou-" + practiceScoresVo.getStructId(), qualified);
            }
            return new JSONObject(qualifiedMap);
        }
        return new JSONObject();
    }
}
