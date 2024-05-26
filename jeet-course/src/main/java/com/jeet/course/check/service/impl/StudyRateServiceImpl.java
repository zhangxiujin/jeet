package com.jeet.course.check.service.impl;

import com.jeet.course.check.service.IStudyRateService;
import com.jeet.course.personal.mapper.PersonalStudyRateMapper;
import com.jeet.course.personal.vo.RateVo;
import com.jeet.course.personal.vo.StructureRateVo;
import com.jeet.course.study.mapper.CourseStuCouMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyRateServiceImpl implements IStudyRateService {

    @Autowired
    private CourseStuCouMapper courseStuCouMapper;
    @Autowired
    private PersonalStudyRateMapper personalStudyRateMapper;

    /**
     * 老师看学生的学习进度树
     * @param userId 学生id
     * @return
     */
    @Override
    public List<RateVo> queryStudentRateTree(Long userId) {
        List<StructureRateVo> structureRateVos = personalStudyRateMapper.selectList(userId);
        List<RateVo> sections = new ArrayList<>();
        List<RateVo> rateVos = genTree(structureRateVos, sections);
            List<Long> courseIds = courseStuCouMapper.selectCourseStuCouList(userId);
            if(courseIds != null) {
                rateVos = rateVos.stream().filter(r ->
                        courseIds.contains(r.getStructId())).collect(Collectors.toList());
            }
        for (int i = 0; i < sections.size(); i++) {
            RateVo rateVo = sections.get(i);
            if (rateVo.getIsPlayComplete().equals("1")) {
                rateVo.getParent().setLearned(rateVo.getParent().getLearned() + 1);
                if(rateVo.getParent().getLearned().equals(rateVo.getParent().getTotal())) {
                    rateVo.getParent().setIsPlayComplete("1");
                    rateVo.getParent().getParent().setLearned(rateVo.getParent().getParent().getLearned() + 1);
                    if(rateVo.getParent().getParent().getLearned().equals(rateVo.getParent().getParent().getTotal())) {
                        rateVo.getParent().getParent().setIsPlayComplete("1");
                        rateVo.getParent().getParent().getParent().setLearned
                                (rateVo.getParent().getParent().getParent().getLearned() + 1);
                        if (rateVo.getParent().getParent().getParent().getLearned().equals
                                (rateVo.getParent().getParent().getParent().getTotal())) {
                            rateVo.getParent().getParent().getParent().setIsPlayComplete("1");
                        }
                    }
                }
            }
        }
        return rateVos;
    }

    /**
     * 构造结构树
     * @param list
     * @return
     */
    private List<RateVo> genTree(List<StructureRateVo> list, List<RateVo> sections) {
        List<RateVo> trees = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StructureRateVo structureRateVo = list.get(i);
            if(structureRateVo.getParentId().equals(0L)) {
                RateVo rateVo = new RateVo();
                rateVo.setStructId(structureRateVo.getStructId());
                rateVo.setStructName(structureRateVo.getStructName());
                rateVo.setRateId(structureRateVo.getRateId());
                rateVo.setContentId(structureRateVo.getContentId());
                rateVo.setParentId(structureRateVo.getParentId());
                rateVo.setType(structureRateVo.getType());
                rateVo.setUserId(structureRateVo.getUserId());
                rateVo.setCreateTime(structureRateVo.getCreateTime());
                trees.add(rateVo);
            }
        }
        for (int i = 0; i < trees.size(); i++) {
            buildTree(list, trees.get(i), sections);
        }
        return trees;
    }

    /**
     * 递归构造结构树
     * @param list
     * @param treeVo
     */
    private void buildTree(List<StructureRateVo> list,RateVo treeVo, List<RateVo> sections) {
        for (int i = 0; i < list.size(); i++) {
            StructureRateVo structureRateVo = list.get(i);
            if (structureRateVo.getParentId().equals(treeVo.getStructId())) {
                RateVo rateVo = new RateVo();
                rateVo.setStructId(structureRateVo.getStructId());
                rateVo.setStructName(structureRateVo.getStructName());
                rateVo.setRateId(structureRateVo.getRateId());
                rateVo.setContentId(structureRateVo.getContentId());
                rateVo.setParentId(structureRateVo.getParentId());
                rateVo.setParent(treeVo);
                treeVo.setTotal(treeVo.getTotal() + 1);
                rateVo.setType(structureRateVo.getType());
                rateVo.setUserId(structureRateVo.getUserId());
                rateVo.setIsPlayComplete(structureRateVo.getIsPlayComplete());
                rateVo.setCreateTime(structureRateVo.getCreateTime());
                treeVo.getChildren().add(rateVo);
                if (structureRateVo.getType().equals("4")) {
                    sections.add(rateVo);
                    rateVo.setStudyRate(structureRateVo.getStudyRate() + "%");
                }

                buildTree(list, rateVo,sections);
            }
        }
    }
}
