package com.jeet.course.troubleshoot.service.impl;

import com.alibaba.fastjson2.JSON;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.file.entity.UploadFile;
import com.jeet.common.file.service.IFileService;
import com.jeet.common.message.constant.MessageConstant;
import com.jeet.common.message.service.IMessageService;
import com.jeet.common.redis.service.RedisService;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.study.vo.QuestionMessage;
import com.jeet.course.troubleshoot.mapper.PracticeAnsMapper;
import com.jeet.course.troubleshoot.service.IPracticeAnsService;
import com.jeet.course.troubleshoot.vo.QuestionsVo;
import com.jeet.course.troubleshoot.vo.ReplyVo;
import com.jeet.course.troubleshoot.vo.SaveAnswerVo;
import com.jeet.system.api.RemoteUserService;
import com.jeet.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PracticeAnsServiceImpl implements IPracticeAnsService {

    @Autowired
    private PracticeAnsMapper practiceAnsMapper;
    @Autowired
    private IFileService fileService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAnswer(SaveAnswerVo courseReply) {
        long id = IdUtil.nextId();
        courseReply.setId(id);
        courseReply.setCreateTime(new Date(System.currentTimeMillis()));
        courseReply.setReplyId(SecurityUtils.getUserId());
        Long fileId = IdUtil.nextId();
        if(courseReply.getFile() != null && courseReply.getFile().getSize() > 0) {
            UploadFile uploadFile = fileService.uploadAudioFile(courseReply.getFile(), fileId);
            courseReply.setAudioPath(uploadFile.getUrl() + "/" + fileId + "." + uploadFile.getExtName());
        }
        practiceAnsMapper.insertAnswer(courseReply);
        practiceAnsMapper.updateState(courseReply.getQuestionId());
        SysUser user = SecurityUtils.getLoginUser().getSysUser();
        Long teacherId = SecurityUtils.getUserId();
        //发送提问的回复通知消息
        QuestionMessage questionMessage = new QuestionMessage();
        questionMessage.setQuestionId(courseReply.getQuestionId());
        if(courseReply.getReplyContent() == null) {
            courseReply.setReplyContent("```语音消息```");
        }
        questionMessage.setContent(courseReply.getReplyContent());
        questionMessage.setType(MessageConstant.TYPE_ADVICE);
        questionMessage.setDept(user.getDept().getDeptName());
        questionMessage.setNickname(user.getNickName());
        questionMessage.setSender(teacherId);
        messageService.send(courseReply.getQuestionUserId()+"", JSON.toJSONString(questionMessage));

        List<QuestionMessage> messages = redisService.getCacheList("ques-message-queue-" + teacherId);
        for (QuestionMessage message : messages) {
            if(message.getQuestionId().equals(courseReply.getQuestionId())) {
                messages.remove(message);
                redisService.deleteForList("ques-message-queue-" + teacherId, message);
                break;
            }
        }
        //给这个老师和这个老师带的所有学生以及管理员发送队列消息
        R<List<Long>> r = remoteUserService.queryStudents(teacherId);
        if(r != null && r.getCode() == 200) {
            List<Long> studentIds = r.getData();
            messageService.send(teacherId+"", JSON.toJSONString(messages));
            for (Long studentId : studentIds) {
                messageService.send(studentId+"", JSON.toJSONString(messages));
            }
            messageService.send(1L+"", JSON.toJSONString(messages));
        }
    }

    @Override
    public List<QuestionsVo> queryReteList(
            String status, Long userId, Long structId, List<Long> studentIdList, List<Long> courseIds) {
        List<QuestionsVo> personQuestions =
                practiceAnsMapper.selectAnsweredList(status, studentIdList, structId, userId, courseIds);
        return personQuestions;
    }

    @Override
    public ReplyVo queryAnswer(Long questionId) {
        ReplyVo courseStudyReplies = practiceAnsMapper.selectAnswer(questionId);
        return courseStudyReplies;
    }

}
