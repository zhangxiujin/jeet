package com.jeet.system.api;

import com.jeet.common.core.constant.SecurityConstants;
import com.jeet.common.core.constant.ServiceNameConstants;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.system.api.domain.SysUser;
import com.jeet.system.api.factory.RemoteUserFallbackFactory;
import com.jeet.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 * 
 * @author jeet
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    public R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 通过学员id查询关联的老师id
     * @param studentId
     * @return
     */
    @GetMapping("/user/queryTeachers/{studentId}")
    public R<List<Long>> queryTeachers(@PathVariable("studentId") Long studentId);

    /**
     * 通过老师id查询关联的学生id
     */
    @GetMapping("/user/queryStudents/{teacherId}")
    public R<List<Long>> queryStudents(@PathVariable("teacherId") Long teacherId);
}
