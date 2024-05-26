package com.jeet.course.study.controller;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.core.utils.bean.BeanUtils;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.common.file.entity.UploadFile;
import com.jeet.common.file.service.IFileService;
import com.jeet.common.message.constant.MessageConstant;
import com.jeet.common.message.service.IMessageService;
import com.jeet.common.redis.service.RedisService;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.content.service.ICourseContentService;
import com.jeet.course.content.vo.ContentSimpleVo;
import com.jeet.course.content.vo.ContentVo;
import com.jeet.course.personal.service.IPersonalStudyRateService;
import com.jeet.course.personal.vo.RateVo;
import com.jeet.course.study.domain.CourseStudyQuestion;
import com.jeet.course.study.service.ICourseStudyService;
import com.jeet.course.study.service.ICourseTeacherService;
import com.jeet.course.study.vo.*;
import com.jeet.system.api.RemoteUserService;
import com.jeet.system.api.constant.UserConstant;
import com.jeet.system.api.domain.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/study")
public class CourseStudyController extends BaseController {
    @Autowired
    private IFileService fileService;
    @Autowired
    private ICourseStudyService courseStudyService;
    @Autowired
    private ICourseContentService courseContentService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IPersonalStudyRateService personalStudyRateService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private ICourseTeacherService courseTeacherService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult save(@RequestBody CourseStudyProjectVo courseStudyProjectVo) {
        courseStudyService.save(courseStudyProjectVo);
        return AjaxResult.success("保存成功");
    }

    @GetMapping("/queryStudy")
    public AjaxResult queryStudy(Long id){
        CourseContent contentVos = courseStudyService.queryLearn(id);
        return AjaxResult.success(contentVos);
    }

    @GetMapping("/queryStudyProject")
    public AjaxResult queryStudyProject(ProjectVo projectVo) {
        List<ProjectStructVo> projectStructVos = courseStudyService.queryStudyProject(projectVo);
        return AjaxResult.success(projectStructVos);
    }

    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file, HttpServletRequest request) {
        String bankId = request.getParameter("bankId");
        long fileId = IdUtil.nextId();
        UploadFile uploadFile = fileService.uploadFile(file, fileId);
        UploadFileVo uploadFileVo = new UploadFileVo(uploadFile, Long.valueOf(bankId));
        return AjaxResult.success("上传成功", uploadFileVo);
    }
    @PostMapping("/saveChoose")
    public AjaxResult saveChoose(@RequestBody ChooseStudy chooseStudy) throws JsonProcessingException {
        courseStudyService.saveChoose(chooseStudy);
        return AjaxResult.success();
    }

    @GetMapping("/queryChoose")
    public AjaxResult queryChoose(Long contentId)  {
        List<ChooseStructureVo> chooseStructureVos = courseStudyService.queryChoose(contentId);
        return AjaxResult.success(chooseStructureVos);
    }

    @PostMapping("/saveWord")
    public AjaxResult saveWord(@RequestBody WordContentVo wordVo) throws JsonProcessingException {
        courseStudyService.saveWord(wordVo);
        return AjaxResult.success("提交成功");
    }

    @PostMapping("/saveSimple")
    public AjaxResult saveSimple(@RequestBody SimpleContentVo simpleContentVo)
            throws JsonProcessingException {
        courseStudyService.saveSimple(simpleContentVo);
        return AjaxResult.success("提交成功");
    }

    @GetMapping("/queryContentInfo")
    public AjaxResult queryContentInfo(Long structId) {
        ContentVo contentVo = courseContentService.queryContentAndVideo(structId);
        return AjaxResult.success(contentVo);
    }

    @GetMapping("/queryWord")
    public AjaxResult queryWord(Long contentId) {
        List<CourseWordBank> courseWordBanks = courseContentService.queryWordListByContentId(contentId);
        return AjaxResult.success(courseWordBanks);
    }

    @GetMapping("/querySimple")
    public AjaxResult querySimple(Long contentId) {
        List<ContentSimpleVo> contentSimpleVos = courseStudyService.querySimpleList(contentId);
        return AjaxResult.success(contentSimpleVos);
    }

    @GetMapping("/selectStructTree")
    public AjaxResult selectStructTree() {
        List<RateVo> rateVos = personalStudyRateService.queryList(SecurityUtils.getUserId());
        return AjaxResult.success(rateVos);
    }

    @PostMapping("/saveQuestion")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult saveQuestion(SaveQuestionVo saveQuestionVo) {
        CourseStudyQuestion courseStudyQuestion = new CourseStudyQuestion();
        BeanUtils.copyBeanProp(courseStudyQuestion, saveQuestionVo);
        Long fileId = IdUtil.nextId();
        if(saveQuestionVo.getFile() != null && saveQuestionVo.getFile().getSize() > 0) {
            UploadFile uploadFile = fileService.uploadAudioFile(saveQuestionVo.getFile(), fileId);
            courseStudyQuestion.setAudioPath(uploadFile.getUrl() + "/" + fileId + "." + uploadFile.getExtName());
        }
        Long courseId = courseStudyService.queryCourseId(saveQuestionVo.getCurrentSelectedStructId());
        courseStudyQuestion.setCourseId(courseId);
        Long questionId = courseStudyService.saveQuestion(courseStudyQuestion);
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
            QuestionMessage questionMessage = new QuestionMessage();
            questionMessage.setQuestionId(questionId);
            if(courseStudyQuestion.getQuestionContent() == null) {
                courseStudyQuestion.setQuestionContent("```语音消息```");
            }
            questionMessage.setContent(courseStudyQuestion.getQuestionContent());
            questionMessage.setType(MessageConstant.TYPE_ADVICE);
            questionMessage.setDept(sysUser.getDept().getDeptName());
            questionMessage.setNickname(sysUser.getNickName());
            questionMessage.setSender(SecurityUtils.getUserId());
            messageService.send(teacher+"", JSON.toJSONString(questionMessage));
            //发送和设置提问待回复标记
            Long structId = courseStudyService.queryStructByQuesId(questionId);
            Map<String, Object> map = new HashMap<>();
            map.put("structId", structId);
            map.put("type", "badge");
            messageService.send(teacher+"", JSON.toJSONString(map));
            Set<Long> set = new HashSet<>();
            set.add(structId);
            redisService.setCacheSet("msg-badge-" + teacher, set);

            //保存当前提问到redis
            QuestionMessage quesMsg = new QuestionMessage();
            quesMsg.setQuestionId(questionId);
            quesMsg.setContent(courseStudyQuestion.getQuestionContent());
            quesMsg.setType(MessageConstant.TYPE_QUEUE);
            quesMsg.setDept(sysUser.getDept().getDeptName());
            quesMsg.setNickname(sysUser.getNickName());
            quesMsg.setSender(SecurityUtils.getUserId());
            List<QuestionMessage> list = new ArrayList<>();
            list.add(quesMsg);
            redisService.setCacheList("ques-message-queue-"+teacher, list);
            List<QuestionMessage> messages = redisService.getCacheList("ques-message-queue-"+teacher);
            //给这个老师和这个老师带的所有学生以及管理员发送队列消息
            R<List<Long>> r = remoteUserService.queryStudents(teacher);
            if(r != null && r.getCode() == 200) {
                List<Long> studentIds = r.getData();
                messageService.send(teacher+"", JSON.toJSONString(messages));
                for (Long studentId : studentIds) {
                    messageService.send(studentId+"", JSON.toJSONString(messages));
                }
                messageService.send(1L+"", JSON.toJSONString(messages));
            }
            return AjaxResult.success();
        } else {
            return AjaxResult.error("无法找到对应的老师!");
        }
    }

    /**
     * 取消消息标记(红点)
     * @param cancelMsgBadgeVo
     * @return
     */
    @PostMapping("/cancelMsgBadge")
    public AjaxResult cancelMsgBadge(@RequestBody CancelMsgBadgeVo cancelMsgBadgeVo) {
        courseStudyService.cancelMsgBadge(cancelMsgBadgeVo);
        return AjaxResult.success();
    }

    @GetMapping("/queryQuestionAnswers")
    public TableDataInfo queryQuestionAnswers(CourseStudyQuestion courseStudyQuestion) {
        startPage();
        List<QuestionReplyVo> courseStudyQuestions =
                courseStudyService.queryQuestionAnswers(courseStudyQuestion);
        return getDataTable(courseStudyQuestions);
    }

    @GetMapping("/queryOtherQuestionAnswers")
    public TableDataInfo queryOtherQuestionAnswers(CourseStudyQuestion courseStudyQuestion) {
        startPage();
        List<QuestionReplyVo> questionReplyVos
                = courseStudyService.queryOtherQuestionAnswers(courseStudyQuestion);
        return getDataTable(questionReplyVos);
    }

    @GetMapping("/queryAllQuestion")
    public AjaxResult queryAllQuestion() {
        SysUser user = SecurityUtils.getLoginUser().getSysUser();
        if (user.getUserType().equals(UserConstant.USER_TYPE_STUDENT)) {
            //获取学生所对应的老师
            R<List<Long>> r = remoteUserService.queryTeachers(user.getUserId());
            if(r != null && r.getCode() == 200) {
                Long teacherId = r.getData().get(0);
                List<QuestionMessage> messages = redisService.getCacheList(
                        "ques-message-queue-" + teacherId);
                return AjaxResult.success(messages);
            } else {
                return AjaxResult.error("答疑队列查询失败!");
            }
        } else if(user.getUserType().equals(UserConstant.USER_TYPE_TEACHER)) {
            List<QuestionMessage> messages = redisService.getCacheList(
                    "ques-message-queue-" + user.getUserId());
            return AjaxResult.success(messages);
        }
        return AjaxResult.error("当前用户无法接收答疑队列消息!");
    }

    @GetMapping("/queryAllMsgBadges")
    public AjaxResult queryAllMsgBadges() {
        Long teacherId = SecurityUtils.getUserId();
        Set<Object> structIds = redisService.getCacheSet("msg-badge-" + teacherId);
        return AjaxResult.success(structIds);
    }
}
