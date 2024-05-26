package com.jeet.course.content.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.content.domain.CourseContentBank;
import com.jeet.course.content.mapper.CourseContentBankMapper;
import com.jeet.course.content.service.ICourseContentBankService;
import com.jeet.course.content.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc: CourseContentBankServiceImpl
 * @author: xue
 * @Date: 2023/8/25 14:46
 */
@Service
public class CourseContentBankServiceImpl implements ICourseContentBankService {

    @Autowired
    private CourseContentBankMapper courseContentBankMapper;

    @Override
    public void save(ContentVo contentVo) {
        List<Long> chooseList = contentVo.getChooseList();
        List<Long> wordList = contentVo.getWordList();
        List<Long> projectList = contentVo.getProjectList();
        List<Long> simpleList = contentVo.getSimpleList();
        List<CourseContentBank> list = new ArrayList<>();
        if (chooseList != null) {
            for (int i = 0; i < chooseList.size(); i++) {
                CourseContentBank courseContentBank = new CourseContentBank();
                courseContentBank.setId(IdUtil.nextId());
                courseContentBank.setBankId(chooseList.get(i));
                courseContentBank.setContentId(contentVo.getContentId());
                courseContentBank.setType("1");
                courseContentBank.setCreateTime(new Date(System.currentTimeMillis()));
                list.add(courseContentBank);
            }
        }
        if (wordList != null) {
            for (int i = 0; i < wordList.size(); i++) {
                CourseContentBank courseContentBank = new CourseContentBank();
                courseContentBank.setId(IdUtil.nextId());
                courseContentBank.setBankId(wordList.get(i));
                courseContentBank.setContentId(contentVo.getContentId());
                courseContentBank.setType("0");
                courseContentBank.setCreateTime(new Date(System.currentTimeMillis()));
                list.add(courseContentBank);
            }
        }
        if (projectList != null) {
            for (int i = 0; i < projectList.size(); i++) {
                CourseContentBank courseContentBank = new CourseContentBank();
                courseContentBank.setId(IdUtil.nextId());
                courseContentBank.setBankId(projectList.get(i));
                courseContentBank.setContentId(contentVo.getContentId());
                courseContentBank.setType("2");
                courseContentBank.setCreateTime(new Date(System.currentTimeMillis()));
                list.add(courseContentBank);
            }
        }
        if (simpleList != null) {
            for (int i = 0; i < simpleList.size(); i++) {
                CourseContentBank courseContentBank = new CourseContentBank();
                courseContentBank.setId(IdUtil.nextId());
                courseContentBank.setBankId(simpleList.get(i));
                courseContentBank.setContentId(contentVo.getContentId());
                courseContentBank.setType("3");
                courseContentBank.setCreateTime(new Date(System.currentTimeMillis()));
                list.add(courseContentBank);
            }
        }
        courseContentBankMapper.insertList(list);
    }

    @Override
    public CourseContentVo query(Long contentId) {
        CourseContentVo courseContentVo = new CourseContentVo();
        List<ContentWordVo> contentWordVos = courseContentBankMapper.selectWord(contentId);
        courseContentVo.setWordBankList(contentWordVos);
        List<ContentChooseVo> contentChooseVos = courseContentBankMapper.selectChoose(contentId);
        courseContentVo.setChooseBankList(contentChooseVos);
        List<ContentProjectVo> contentProjectVos = courseContentBankMapper.selectProject(contentId);
        courseContentVo.setProjectBankList(contentProjectVos);
        List<ContentSimpleVo> contentSimpleVos = courseContentBankMapper.selectSimple(contentId);
        courseContentVo.setSimpleBankList(contentSimpleVos);
        return courseContentVo;
    }

    @Override
    public void removeWord(DeleteWordVo deleteWordVo) {
        courseContentBankMapper.deleteWord(deleteWordVo);
    }

    @Override
    public void removeChoose(DeleteChooseVo deleteChooseVo) {
        courseContentBankMapper.deleteChoose(deleteChooseVo);
    }

    @Override
    public void removeProject(DeleteProjectVo deleteProjectVo) {
        courseContentBankMapper.deleteProject(deleteProjectVo);
    }

    @Override
    public void removeSimple(DeleteSimpleVo deleteSimpleVo) {
        courseContentBankMapper.deleteSimple(deleteSimpleVo);
    }

}
