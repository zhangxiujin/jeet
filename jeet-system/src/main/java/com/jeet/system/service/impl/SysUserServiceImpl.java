package com.jeet.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Validator;

import com.alibaba.fastjson2.JSON;
import com.jeet.common.core.constant.UserConstants;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.exception.ServiceException;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.core.utils.SpringUtils;
import com.jeet.common.core.utils.StringUtils;
import com.jeet.common.core.utils.bean.BeanUtils;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.system.api.constant.DeptConstant;
import com.jeet.system.api.domain.SysRole;
import com.jeet.system.api.domain.SysTeaStu;
import com.jeet.system.api.domain.SysUser;
import com.jeet.system.api.vo.AddUserVo;
import com.jeet.system.api.vo.TreeSelectVo;
import com.jeet.system.dao.*;
import com.jeet.task.api.RemoteCourseService;
import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.domain.CourseTeaCou;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.jeet.common.core.utils.bean.BeanValidators;
import com.jeet.common.datascope.annotation.DataScope;
import com.jeet.system.domain.SysPost;
import com.jeet.system.domain.SysUserPost;
import com.jeet.system.domain.SysUserRole;
import com.jeet.system.service.ISysConfigService;
import com.jeet.system.service.ISysUserService;

/**
 * 用户 业务层处理
 * 
 * @author jeet
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysTeaStuMapper teaStuMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    @Autowired
    private RemoteCourseService remoteCourseService;

    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user)
    {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user)
    {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     * 
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     * 
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list))
        {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     * 
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users))
            {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUser user)
    {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(AddUserVo addUserVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyBeanProp(sysUser, addUserVo);
        //如果选择的班级树不为空
        if(addUserVo.getTreeSelectList() != null
                && addUserVo.getTreeSelectList().size() > 0) {
            TreeSelectVo root = addUserVo.getTreeSelectList().get(0);
            recursionSelectedTree(root, addUserVo.getTreeSelectList());
            sysUser.setOrgScope(JSON.toJSONString(root));
        }
        // 新增用户信息
        int rows = userMapper.insertUser(sysUser);
        // 新增用户岗位关联
        insertUserPost(sysUser);
        // 新增用户与角色管理
        insertUserRole(sysUser);
        //新增老师和学生关系
        List<Long> stuList = addUserVo.getTeachStuList();
        if(stuList != null && stuList.size() > 0) {
            List<SysTeaStu> sysTeaStuList = new ArrayList<>();
            for (int i = 0; i < stuList.size(); i++) {
                SysTeaStu sysTeaStu = new SysTeaStu();
                sysTeaStu.setTeaStuId(IdUtil.nextId());
                sysTeaStu.setStudentId(stuList.get(i));
                sysTeaStu.setTeacherId(sysUser.getUserId());
                sysTeaStuList.add(sysTeaStu);
            }
            teaStuMapper.insertList(sysTeaStuList);
        }
        List<Long> courseIdList = addUserVo.getSelectedCourse();
        if(courseIdList != null && courseIdList.size() > 0) {
            if(addUserVo.getDeptType().equals(DeptConstant.DEPT_TYPE_CLASS)) {
                // 新增学生和课程关系
                List<CourseStuCou> courseStuCouList = new ArrayList<>();
                for (int i = 0; i < courseIdList.size(); i++) {
                    CourseStuCou courseStuCou = new CourseStuCou();
                    courseStuCou.setCourseId(courseIdList.get(i));
                    courseStuCou.setStuId(sysUser.getUserId());
                    courseStuCouList.add(courseStuCou);
                }
                R<Void> result = remoteCourseService.saveStudentCourse(courseStuCouList);
                if(result == null || result.getCode() != 200) {
                    throw new RuntimeException(result.getMsg());
                }
            } else if (addUserVo.getDeptType().equals(DeptConstant.DEPT_TYPE_TEACHING)) {
                // 新增老师和课程关系
                List<CourseTeaCou> courseTeaCouList = new ArrayList<>();
                for (int i = 0; i < courseIdList.size(); i++) {
                    CourseTeaCou courseTeaCou = new CourseTeaCou();
                    courseTeaCou.setCourseId(courseIdList.get(i));
                    courseTeaCou.setTeacherId(sysUser.getUserId());
                    courseTeaCouList.add(courseTeaCou);
                }
                R<Void> result = remoteCourseService.saveTeacherCourse(courseTeaCouList);
                if(result == null || result.getCode() != 200) {
                    throw new RuntimeException(result.getMsg());
                }
            }
        }
        return rows;
    }

    /**
     * 注册用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user)
    {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(AddUserVo user)
    {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        //如果选择的班级树不为空
        if(user.getTreeSelectList() != null
                && user.getTreeSelectList().size() > 0) {
            TreeSelectVo root = user.getTreeSelectList().get(0);
            recursionSelectedTree(root, user.getTreeSelectList());
            user.setOrgScope(JSON.toJSONString(root));
        } else {
            user.setOrgScope(null);
        }
        int rows = userMapper.updateUser(user);
        if(user.getDeptType().equals(DeptConstant.DEPT_TYPE_CLASS)) {
            // 删除学生和课程关系
            R<Void> res = remoteCourseService.removeStudentCourse(userId);
            if(res != null && res.getCode() == 200) {
                // 新增学生和课程关系
                List<Long> courseIdList = user.getSelectedCourse();
                if(courseIdList != null && courseIdList.size() > 0) {
                    List<CourseStuCou> courseStuCouList = new ArrayList<>();
                    for (int i = 0; i < courseIdList.size(); i++) {
                        CourseStuCou courseStuCou = new CourseStuCou();
                        courseStuCou.setCourseId(courseIdList.get(i));
                        courseStuCou.setStuId(userId);
                        courseStuCouList.add(courseStuCou);
                    }
                    R<Void> result = remoteCourseService.saveStudentCourse(courseStuCouList);
                    if(result == null || result.getCode() != 200) {
                        throw new RuntimeException(result.getMsg());
                    }
                }
            } else {
                throw new RuntimeException(res.getMsg());
            }
        } else if(user.getDeptType().equals(DeptConstant.DEPT_TYPE_TEACHING)) {
            // 删除老师和课程关系
            R<Void> res = remoteCourseService.removeTeacherCourse(userId);
            if(res != null && res.getCode() == 200) {
                // 新增老师和课程关系
                List<Long> courseIdList = user.getSelectedCourse();
                if(courseIdList != null && courseIdList.size() > 0) {
                    List<CourseTeaCou> courseTeaCouList = new ArrayList<>();
                    for (int i = 0; i < courseIdList.size(); i++) {
                        CourseTeaCou courseTeaCou = new CourseTeaCou();
                        courseTeaCou.setCourseId(courseIdList.get(i));
                        courseTeaCou.setTeacherId(userId);
                        courseTeaCouList.add(courseTeaCou);
                    }
                    R<Void> result = remoteCourseService.saveTeacherCourse(courseTeaCouList);
                    if(result == null || result.getCode() != 200) {
                        throw new RuntimeException(result.getMsg());
                    }
                }
            } else {
                throw new RuntimeException(res.getMsg());
            }

            //删除老师和学生关系
            teaStuMapper.delete(userId);
            //新增老师和学生关系
            List<Long> stuList = user.getTeachStuList();
            if(stuList != null && stuList.size() > 0) {
                List<SysTeaStu> sysTeaStuList = new ArrayList<>();
                for (int i = 0; i < stuList.size(); i++) {
                    SysTeaStu sysTeaStu = new SysTeaStu();
                    sysTeaStu.setTeaStuId(IdUtil.nextId());
                    sysTeaStu.setStudentId(stuList.get(i));
                    sysTeaStu.setTeacherId(userId);
                    sysTeaStuList.add(sysTeaStu);
                }
                teaStuMapper.insertList(sysTeaStuList);
            }
        }
        return rows;
    }

    /**
     * 用户授权角色
     * 
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds)
    {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     * 
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    /**
     * 新增用户岗位信息
     * 
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 新增用户角色信息
     * 
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (StringUtils.isNotEmpty(roleIds))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(user);
                    checkUserDataScope(user.getUserId());
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public List<Long> queryTeachers(Long studentId) {
        return teaStuMapper.selectTeachers(studentId);
    }

    @Override
    public List<Long> queryStudents(Long teacherId) {
        return teaStuMapper.selectStudents(teacherId);
    }

    private void recursionSelectedTree(TreeSelectVo root, List<TreeSelectVo> treeSelectVoList) {
        List<TreeSelectVo> childrens = root.getChildren();
        if(childrens != null) {
            for (int i = 0; i < childrens.size(); i++) {
                boolean contains = false;
                for (int j = 1; j < treeSelectVoList.size(); j++) {
                    TreeSelectVo children = childrens.get(i);
                    TreeSelectVo treeSelectVo = treeSelectVoList.get(j);
                    if(children.getNodeType().equals(treeSelectVo.getNodeType())
                            && children.getId().equals(treeSelectVo.getId())) {
                        contains = true;
                        recursionSelectedTree(children, treeSelectVoList);
                        break;
                    }
                }
                if(!contains) {
                    childrens.remove(i);
                    --i;
                }
            }
        }
    }
}
