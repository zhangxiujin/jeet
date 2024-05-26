package com.jeet.course.content.controller;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.common.file.entity.UploadFile;
import com.jeet.common.file.service.IFileService;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.domain.CourseContentVideo;
import com.jeet.course.content.service.ICourseContentBankService;
import com.jeet.course.content.service.ICourseContentService;
import com.jeet.course.content.service.ICourseContentVideoService;
import com.jeet.course.content.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.LittleEndianOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 课程内容管理控制器
 */
@Api(tags = {"课程内容管理控制器"})
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private IFileService fileService;

    @Autowired
    private ICourseContentService courseContentService;

    @Autowired
    private ICourseContentBankService courseContentBankService;

    @Autowired
    private ICourseContentVideoService courseContentVideoService;

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult save(@RequestBody ContentVo contentVo) {
        if (contentVo.getContentId() != null) {  //内容id在点击左侧树节点时已经存在了，所以这里判断不为空时才进行如下操作
            CourseContentVideo contentVideo = courseContentVideoService.query(contentVo.getContentId());
            if (contentVideo == null && contentVo.getVideoId() != null) {
                courseContentVideoService.save(contentVo);
            } else if(contentVideo != null && contentVo.getVideoId() == null) {
                courseContentVideoService.remove(contentVideo.getId());
            } else if(contentVideo != null && contentVo.getVideoId() != null) {
                courseContentVideoService.modify(contentVo);
            }
            courseContentService.modify(contentVo);
        }
        return AjaxResult.success("操作成功");
    }

    @PostMapping("/saveBank")
    public AjaxResult saveBank(@RequestBody ContentVo contentVo) {
        courseContentBankService.save(contentVo);
        return AjaxResult.success("新增成功");
    }

    @GetMapping("/query")
    public AjaxResult query(ContentVo contentVo) {
        long start = System.currentTimeMillis();
        CourseContent content = courseContentService.query(contentVo.getStructId());
        if(content != null) {
            CourseContentVo courseContentVo = new CourseContentVo();
            courseContentVo.setContentId(content.getId());
            courseContentVo.setContentName(content.getName());
            courseContentVo.setDesc(content.getDesc());
            CourseContentVo query = courseContentBankService.query(courseContentVo.getContentId());
            if (query != null) {
                courseContentVo.setWordBankList(query.getWordBankList());
                courseContentVo.setChooseBankList(query.getChooseBankList());
                courseContentVo.setProjectBankList(query.getProjectBankList());
                courseContentVo.setSimpleBankList(query.getSimpleBankList());
            }
            CourseContentVideo queryVideo =
                    courseContentVideoService.query(courseContentVo.getContentId());
            if (queryVideo != null) {
                courseContentVo.setExt(queryVideo.getExt());
                courseContentVo.setUrl(queryVideo.getUrl());
                courseContentVo.setVideoName(queryVideo.getName());
                courseContentVo.setVideoId(queryVideo.getId());
            }
            System.out.println(System.currentTimeMillis() - start + "ms");
            return AjaxResult.success(courseContentVo);
        }else {
            Long contentId = courseContentService.save(contentVo);
            return AjaxResult.success(contentId);
        }
    }

    /**
     * 上传课程内容视频
     */
    @ApiOperation(value = "课程内容的视频上传")
    @ApiImplicitParam(value = "视频文件", name = "file")
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file) {
        long fileId = IdUtil.nextId();
        UploadFile _file = fileService.uploadVideo(file, fileId);
        return AjaxResult.success("上传成功", _file);
    }

    /**
     * 删除选中的单词题的数据
     */
    @PostMapping("/removeWord")
    public AjaxResult removeWord(@RequestBody DeleteWordVo deleteWordVo) {
        courseContentBankService.removeWord(deleteWordVo);
        return AjaxResult.success("删除成功");
    }

    /**
     * 删除选中的选择题的数据
     */
    @PostMapping("/removeChoose")
    public AjaxResult removeChoose(@RequestBody DeleteChooseVo deleteChooseVo) {
        courseContentBankService.removeChoose(deleteChooseVo);
        return AjaxResult.success("删除成功");
    }

    /**
     * 删除选中的项目题的数据
     */
    @PostMapping("/removeProject")
    public AjaxResult removeProject(@RequestBody DeleteProjectVo deleteProjectVo) {
        courseContentBankService.removeProject(deleteProjectVo);
        return AjaxResult.success("删除成功");
    }

    /**
     * 删除选中的简答的数据
     */
    @PostMapping("/removeSimple")
    public AjaxResult removeSimple(@RequestBody DeleteSimpleVo deleteSimpleVo) {
        courseContentBankService.removeSimple(deleteSimpleVo);
        return AjaxResult.success("删除成功");
    }

}
