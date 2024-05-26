package com.jeet.course.troubleshoot.controller;

import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.common.message.service.IMessageService;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.study.domain.CourseStudyReply;
import com.jeet.course.study.service.ICourseTeacherService;
import com.jeet.course.troubleshoot.service.IPracticeAnsService;
import com.jeet.course.troubleshoot.vo.QuestionsVo;
import com.jeet.course.troubleshoot.vo.ReplyVo;
import com.jeet.course.troubleshoot.vo.SaveAnswerVo;
import com.jeet.system.api.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/practiceAns")
public class PracticeAnsController extends BaseController {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private ICourseTeacherService courseTeacherService;
    @Autowired
    private IPracticeAnsService courseTroubleshootService;

    /**
     * 保存回答
     * @param courseReply
     * @return {@link AjaxResult}
     */
    @PostMapping("/saveAnswer")
    public AjaxResult saveAnswer(SaveAnswerVo courseReply){
        courseTroubleshootService.saveAnswer(courseReply);
        return AjaxResult.success("保存成功");
    }

    /**
     * 查询已回答或未回答列表
     * @return {@link AjaxResult}
     */
    @GetMapping("/queryReteList")
    public TableDataInfo queryReteList(String status, Long userId, Long structId) {
        Long currentUserId = SecurityUtils.getUserId();
        R<List<Long>> result = remoteUserService.queryStudents(currentUserId);
        List<Long> courseIds = null;
        if(structId == null) {
            courseIds = courseTeacherService.queryCourseTeaCouList(currentUserId);
        }
        if(result != null && result.getCode() == 200) {
            List<Long> studentIds = result.getData();
            if(studentIds == null) {
                studentIds = new ArrayList<>();
            }
            startPage();
            List<QuestionsVo> queryReteList = courseTroubleshootService.queryReteList(
                    status, userId, structId, studentIds, courseIds);
            return getDataTable(queryReteList);
        } else {
            throw new RuntimeException(result.getMsg());
        }
    }

    /**
     * 查询回答
     * @param
     * @return
     */
    @GetMapping("/queryAnswer")
    public AjaxResult queryAnswer(Long questionId){
        ReplyVo courseStudyReply = courseTroubleshootService.queryAnswer(questionId);
        return AjaxResult.success(courseStudyReply);
    }
}
