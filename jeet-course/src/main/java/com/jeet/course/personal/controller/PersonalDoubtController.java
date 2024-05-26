package com.jeet.course.personal.controller;

import com.alibaba.fastjson2.JSON;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.common.message.constant.MessageConstant;
import com.jeet.common.message.service.IMessageService;
import com.jeet.common.redis.service.RedisService;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.personal.service.IDoubtService;
import com.jeet.course.personal.vo.DoubtVo;
import com.jeet.course.study.domain.CourseStudyQuestion;
import com.jeet.course.study.vo.QuestionMessage;
import com.jeet.system.api.RemoteUserService;
import com.jeet.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: 答疑回复
 * @author: xue
 * @Date: 2023/9/27 11:33
 */
@RestController
@RequestMapping("/doubt")
public class PersonalDoubtController extends BaseController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IDoubtService doubtService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RedisService redisService;

    /**
     * 查询已回复的提问列表
     * @param status 0未回复 1已回复
     * @return
     */
    @GetMapping("/queryList")
    public TableDataInfo queryList(String status) {
        startPage();
        List<DoubtVo> doubtVos = doubtService.queryList(status);
        return getDataTable(doubtVos);
    }

    @PostMapping("/saveQuestion")
    public AjaxResult saveQuestion(@RequestBody CourseStudyQuestion courseStudyQuestion) {
        Long id = doubtService.saveQuestion(courseStudyQuestion);
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        //查询当前学生所关联的老师
        Long currentStudentId = SecurityUtils.getUserId();
        R<List<Long>> result = remoteUserService.queryTeachers(currentStudentId);
        if(result != null && result.getCode() == 200) {
            List<Long> teacherIds = result.getData();
            for (int i = 0; i < teacherIds.size(); i++) {
                QuestionMessage questionMessage = new QuestionMessage();
                questionMessage.setQuestionId(id);
                questionMessage.setContent(courseStudyQuestion.getQuestionContent());
                questionMessage.setType(MessageConstant.TYPE_ADVICE);
                questionMessage.setDept(sysUser.getDept().getDeptName());
                questionMessage.setNickname(sysUser.getNickName());
                questionMessage.setSender(SecurityUtils.getUserId());
                messageService.send(teacherIds.get(i)+"", JSON.toJSONString(questionMessage));
            }
        }
        //保存当前提问到redis
        QuestionMessage questionMessage = new QuestionMessage();
        questionMessage.setQuestionId(id);
        questionMessage.setContent(courseStudyQuestion.getQuestionContent());
        questionMessage.setType(MessageConstant.TYPE_QUEUE);
        questionMessage.setDept(sysUser.getDept().getDeptName());
        questionMessage.setNickname(sysUser.getNickName());
        questionMessage.setSender(SecurityUtils.getUserId());
        List<QuestionMessage> list = new ArrayList<>();
        list.add(questionMessage);
        redisService.setCacheList("ques-message-queue", list);
        List<QuestionMessage> messages = redisService.getCacheList("ques-message-queue");
        //给所有在线的用户发送最新的提问信息
        messageService.sendAllUser(JSON.toJSONString(messages));
        return AjaxResult.success();
    }

    /**
     * 条件查询
     */
    @GetMapping("/queryDoubt")
    public TableDataInfo queryDoubt(Long structId, String status) {
        List<DoubtVo> doubtVos = doubtService.queryDoubt(structId, status);
        return getDataTable(doubtVos);
    }

}
