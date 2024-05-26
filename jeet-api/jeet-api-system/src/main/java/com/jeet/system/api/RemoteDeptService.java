package com.jeet.system.api;

import com.jeet.common.core.constant.SecurityConstants;
import com.jeet.common.core.constant.ServiceNameConstants;
import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.system.api.factory.RemoteDeptFallbackFactory;
import com.jeet.system.api.factory.RemoteUserFallbackFactory;
import com.jeet.system.api.model.LoginUser;
import com.jeet.system.api.vo.TreeSelectVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 组织服务
 *
 * @author jeet
 */
@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService {

    /**
     * 查询机构和用户树
     * @return
     */
    @GetMapping("/dept/deptTreeUser")
    public R<List<TreeSelectVo>> deptTreeUser();
}
