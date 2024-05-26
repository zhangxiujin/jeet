package com.jeet.course.bank.controller;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.common.file.entity.UploadFile;
import com.jeet.common.file.service.IFileService;
import com.jeet.course.bank.domain.CourseBankAttachment;
import com.jeet.course.bank.domain.CourseProjectBank;
import com.jeet.course.bank.service.ICourseBankAttachmentService;
import com.jeet.course.bank.service.ICourseProjectBankService;
import com.jeet.course.bank.vo.ProjectBankVo;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @PackageName : com.jeet.course.bank.controller
 * @FileName : PorjectController
 * @Description :
 * @Author 李宇乐
 * @Date 2023/8/17 18:48
 * @Version : 1.0.0
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    private ICourseProjectBankService courseProjectBankService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private ICourseBankAttachmentService courseBankAttachmentService;

    @PostMapping("/removeProject")
    public AjaxResult removeProject(@RequestBody List<Long> ids){
        if (ids.size() == 1) {
            courseProjectBankService.removeProject(ids.get(0));
        }else {
            courseProjectBankService.removeProjectList(ids);
        }
        return AjaxResult.success("删除成功");
    }
    @PostMapping("/saveProject")
    public AjaxResult saveProject(@RequestBody CourseProjectBank courseProjectBank) {
        Long id = courseProjectBankService.saveProject(courseProjectBank);
        return AjaxResult.success("新增成功", id);
    }

    @GetMapping("/queryProject")
    public TableDataInfo queryProject(ProjectVo projectVo) {
        List<ProjectStructVo> projectStructVos = courseProjectBankService.queryProject(projectVo);
        return getDataTable(projectStructVos);
    }

    @GetMapping("/queryProjectId")
    public AjaxResult queryProjectId(Long id) {
        ProjectBankVo projectBankVo = courseProjectBankService.queryProjectId(id);
        return AjaxResult.success(projectBankVo);
    }

    @GetMapping("/queryProjectDetail")
    public AjaxResult queryProjectDetail(Long id) {
        ProjectBankVo projectBankVo = courseProjectBankService.queryProjectDetail(id);
        return AjaxResult.success(projectBankVo);
    }

    @PostMapping("/modifyProject")
    public AjaxResult modifyProject(@RequestBody CourseProjectBank courseProjectBank) {
        List<String> urls = (List<String>)courseProjectBank.getParams().get("removeFileUrls");
        if(urls != null && urls.size() > 0) {
            String[] arr = new String[urls.size()];
            for (int i = 0; i < urls.size(); i++) {
                arr[i] = urls.get(i);
            }
            //调用删除附件的方法
            courseBankAttachmentService.removeAttachments(arr);
        }
        courseProjectBankService.modifyProject(courseProjectBank);
        return AjaxResult.success("更新成功");
    }

    @PostMapping("/modifyProjectStatus")
    public AjaxResult modifyProjectStatus(@RequestBody CourseProjectBank courseProjectBank) {
        courseProjectBankService.modifyProject(courseProjectBank);
        return AjaxResult.success("状态更新成功");
    }

    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file, HttpServletRequest request) {
        String id = request.getParameter("id");
//        String removeFileUrls = request.getParameter("removeFileList");
//        if(removeFileUrls != null && !removeFileUrls.equals("")) {
//            //调用删除附件的方法
//            String[] urls = removeFileUrls.split(",");
//            courseBankAttachmentService.removeAttachments(urls);
//        }
        if (!file.isEmpty()) {
            Long fileId = IdUtil.nextId();
            UploadFile _file = fileService.uploadFile(file, fileId);
            CourseBankAttachment courseBankAttachment = new CourseBankAttachment();
            courseBankAttachment.setId(fileId);
            courseBankAttachment.setName(_file.getName());
            courseBankAttachment.setBankId(Long.valueOf(id));
            courseBankAttachment.setExtName(_file.getExtName());
            courseBankAttachment.setUrl(_file.getUrl());
            courseBankAttachmentService.insertCourseBankAttachment(courseBankAttachment);
        }
        return AjaxResult.success("上传成功");
    }

    @GetMapping("/queryFile")
    public AjaxResult queryFile(Long id) {
        List<CourseBankAttachment> courseBankAttachments =
                courseBankAttachmentService.selectAttachments(id);
        return AjaxResult.success(courseBankAttachments);
    }

    @GetMapping("listUnbindProject")
    public TableDataInfo listUnbindProject(ProjectVo projectVo) {
        startPage();
        List<ProjectStructVo> projectStructVos =
                courseProjectBankService.queryProjectByContentId(projectVo);
        return getDataTable(projectStructVos);
    }

//    @PostMapping("/remove")
//    public AjaxResult remove(@RequestBody List<Long> ids) {
//        courseBankAttachmentService.removeAttachments(ids);
//        return AjaxResult.success("删除成功");
//    }

}
