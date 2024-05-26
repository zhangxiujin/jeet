package com.jeet.course.personal.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.redis.service.RedisService;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.personal.mapper.PersonalStudyRateMapper;
import com.jeet.course.personal.service.IPersonalStudyRateService;
import com.jeet.course.personal.vo.RateVo;
import com.jeet.course.personal.vo.StructureRateVo;
import com.jeet.course.struct.mapper.CourseStructureMapper;
import com.jeet.course.study.mapper.CourseStuCouMapper;
import com.jeet.course.study.mapper.CourseTeaCouMapper;
import com.jeet.system.api.constant.UserConstant;
import com.jeet.task.api.domain.CourseStudyRate;
import com.jeet.task.api.vo.CourseStudyRateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Desc: PersonalStudyRateServiceImpl
 * @author: xue
 * @Date: 2023/9/14 17:14
 */
@Service
public class PersonalStudyRateServiceImpl implements IPersonalStudyRateService {

    @Autowired
    private PersonalStudyRateMapper personalStudyRateMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CourseStructureMapper courseStructureMapper;
    @Autowired
    private CourseStuCouMapper courseStuCouMapper;
    @Autowired
    private CourseTeaCouMapper courseTeaCouMapper;

    @Override
    public Map<String, Object> saveStudyRate(CourseStudyRateVo courseStudyRateVo) {
        long duration = courseStudyRateVo.getDuration();
        long playTimeLength = courseStudyRateVo.getPlayTimeLength();
        Double rate = (double)playTimeLength / duration * 100;
        int r = rate.intValue();
        Map<String, Object> result = new HashMap<>();
        Long userId = SecurityUtils.getUserId();
        CourseStudyRateVo studyRateVo = redisService.getCacheObject(
                "studyRates:" + courseStudyRateVo.getContentId() +"-"+userId);
        if(studyRateVo != null) {
            System.out.println("rate : " + r);
            if(r == 100) {
                studyRateVo.setIsPlayComplete("1");  //0是未学完  1是已学完
            }
            if (studyRateVo.getPlayTimeLength() < playTimeLength) {
                studyRateVo.setPlayTimeLength(playTimeLength);
                studyRateVo.setStudyRate(r);
                studyRateVo.setIsSync("0");
                redisService.setCacheObject("studyRates:" + courseStudyRateVo.getContentId() +"-"+userId, studyRateVo);
            }
        } else {
            courseStudyRateVo.setUserId(userId);
            long id = IdUtil.nextId();
            courseStudyRateVo.setId(id);
            courseStudyRateVo.setStudyRate(r);
            courseStudyRateVo.setCreateTime(new Date(System.currentTimeMillis()));
            courseStudyRateVo.setIsSync("0");
            redisService.setCacheObject("studyRates:" + courseStudyRateVo.getContentId() +"-"+userId, courseStudyRateVo);
        }
        result.put("status", r == 100 ? true : false);
        return result;
    }

    @Override
    public List<CourseStudyRate> queryStudyRate(CourseStudyRate courseStudyRate) {
        return personalStudyRateMapper.selectStudyRate(courseStudyRate);
    }

    @Override
    public void modifyStudyRate(CourseStudyRate courseStudyRate) {
        personalStudyRateMapper.updateStudyRate(courseStudyRate);
    }

    @Override
    public List<RateVo> queryList(Long userId) {
        List<StructureRateVo> structureRateVos = personalStudyRateMapper.selectList(userId);
        List<RateVo> sections = new ArrayList<>();
        List<RateVo> rateVos = genTree(structureRateVos, sections);
        if (SecurityUtils.getLoginUser().getSysUser()
                .getUserType().equals(UserConstant.USER_TYPE_STUDENT)) {
            List<Long> courseIds = courseStuCouMapper.selectCourseStuCouList(userId);
            if(courseIds != null) {
                rateVos = rateVos.stream().filter(r ->
                    courseIds.contains(r.getStructId())).collect(Collectors.toList());
            }
        } else if (SecurityUtils.getLoginUser().getSysUser()
                .getUserType().equals(UserConstant.USER_TYPE_TEACHER)) {
            List<Long> courseIds = courseTeaCouMapper.selectCourseTeaCouList(userId);
            if(courseIds != null) {
                rateVos = rateVos.stream().filter(r ->
                    courseIds.contains(r.getStructId())).collect(Collectors.toList());
            }
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

    @Override
    public void saveList(List<CourseStudyRateVo> list) {
        ArrayList<CourseStudyRate> list1 = new ArrayList<>();
        ArrayList<CourseStudyRate> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CourseStudyRate courseStudyRate = new CourseStudyRate();
            courseStudyRate.setStudyRate(list.get(i).getStudyRate());
            courseStudyRate.setContentId(list.get(i).getContentId());
            courseStudyRate.setCreateTime(list.get(i).getCreateTime());
            courseStudyRate.setId(list.get(i).getId());
            courseStudyRate.setStructId(list.get(i).getStructId());
            courseStudyRate.setUserId(list.get(i).getUserId());
            courseStudyRate.setIsPlayComplete(list.get(i).getIsPlayComplete());
            if (list.get(i).getIsSave().equals("0")) {
                list1.add(courseStudyRate);
            }else if (list.get(i).getIsSave().equals("1")){
                list2.add(courseStudyRate);
            }
        }
        if (list1.size() > 0) {
            personalStudyRateMapper.insertList(list1);
        }
        if (list2.size() > 0) {
            personalStudyRateMapper.updateList(list2);
        }
    }

    @Override
    public void modifyList(List<CourseStudyRateVo> list) {
        ArrayList<CourseStudyRate> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CourseStudyRate courseStudyRate = new CourseStudyRate();
            courseStudyRate.setStudyRate(list.get(i).getStudyRate());
            courseStudyRate.setContentId(list.get(i).getContentId());
            courseStudyRate.setCreateTime(list.get(i).getCreateTime());
            courseStudyRate.setId(list.get(i).getId());
            courseStudyRate.setStructId(list.get(i).getStructId());
            courseStudyRate.setUserId(list.get(i).getUserId());
            courseStudyRate.setIsPlayComplete(list.get(i).getIsPlayComplete());
            list1.add(courseStudyRate);
        }
        personalStudyRateMapper.updateList(list1);
    }

    @Override
    public List<CourseStudyRate> queryTaskList(Long id) {
        return personalStudyRateMapper.selectTaskList();
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
