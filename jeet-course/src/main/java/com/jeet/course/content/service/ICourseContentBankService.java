package com.jeet.course.content.service;

import com.jeet.course.content.domain.CourseContentBank;
import com.jeet.course.content.vo.*;

import java.util.List;

public interface ICourseContentBankService {

    /**
     * 新增
     * @param contentVo
     */
    void save(ContentVo contentVo);

    /**
     * 查询
     */
    CourseContentVo query(Long contentId);

    /**
     * 删除选中的单词数据
     */
    void removeWord(DeleteWordVo deleteWordVo);

    /**
     * 删除选中的选择数据
     */
    void removeChoose(DeleteChooseVo deleteChooseVo);

    /**
     * 删除选中的项目数据
     */
    void removeProject(DeleteProjectVo deleteProjectVo);

    /**
     * 删除选中的简答数据
     */
    void removeSimple(DeleteSimpleVo deleteSimpleVo);

}
