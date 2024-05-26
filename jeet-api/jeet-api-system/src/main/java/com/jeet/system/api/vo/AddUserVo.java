package com.jeet.system.api.vo;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.system.api.constant.UserConstant;
import com.jeet.system.api.domain.SysTeaStu;
import com.jeet.system.api.domain.SysUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxiujin
 */
@Data
public class AddUserVo extends SysUser {
    /** 选择的课程 */
    private List<Long> selectedCourse;
    /** 部门类型 */
    private String deptType;
    /** 可见组织结构(包括用户) */
    private List<TreeSelectVo> treeSelectList;

    /** 老师负责的学生id列表 */
    public List<Long> getTeachStuList() {
        if(treeSelectList != null) {
            List<Long> stuList = new ArrayList<>();
            for (int i = 0; i < treeSelectList.size(); i++) {
                if (treeSelectList.get(i).getNodeType().equals("user")) {
                    stuList.add(treeSelectList.get(i).getId());
                }
            }
            return stuList;
        }
        return null;
    }
}
