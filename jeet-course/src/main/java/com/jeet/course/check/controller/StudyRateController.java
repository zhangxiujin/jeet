package com.jeet.course.check.controller;

import com.alibaba.fastjson2.JSON;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.check.service.IStudyRateService;
import com.jeet.course.personal.vo.RateVo;
import com.jeet.system.api.RemoteDeptService;
import com.jeet.system.api.domain.SysUser;
import com.jeet.system.api.vo.TreeSelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/check-studyRate")
public class StudyRateController {

    @Autowired
    private IStudyRateService studyRateService;
    @Autowired
    private RemoteDeptService remoteDeptService;

    /**
     * 学习进度模块下的左侧树
     * @return
     */
    @GetMapping("/queryOrgTree")
    public AjaxResult queryOrgTree() {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        //如果是超级管理员
        if (sysUser.getUserId().equals(1L)) {
            R<List<TreeSelectVo>> result = remoteDeptService.deptTreeUser();
            if(result != null && result.getCode() == 200) {
                List<TreeSelectVo> data = result.getData();
                return AjaxResult.success(data);
            } else {
                throw new RuntimeException(result.getMsg());
            }
        } else {
            String orgScope = sysUser.getOrgScope();
            TreeSelectVo treeSelectVo = JSON.parseObject(orgScope, TreeSelectVo.class);
            List<TreeSelectVo> treeSelectVoList = new ArrayList<>();
            treeSelectVoList.add(treeSelectVo);
            return AjaxResult.success(treeSelectVoList);
        }
    }

    @GetMapping("/queryStudentRateTree")
    public AjaxResult queryStudentRateTree(Long studentId) {
        List<RateVo> rateVos = studyRateService.queryStudentRateTree(studentId);
        return AjaxResult.success(rateVos);
    }
}
