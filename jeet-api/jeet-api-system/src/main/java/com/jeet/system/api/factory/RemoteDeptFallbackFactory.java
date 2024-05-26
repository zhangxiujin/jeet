package com.jeet.system.api.factory;

import com.jeet.common.core.domain.R;
import com.jeet.system.api.RemoteDeptService;
import com.jeet.system.api.vo.TreeSelectVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户服务降级处理
 * 
 * @author jeet
 */
@Component
public class RemoteDeptFallbackFactory implements FallbackFactory<RemoteDeptService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDeptFallbackFactory.class);

    @Override
    public RemoteDeptService create(Throwable throwable)
    {
        log.error("部门服务调用失败:{}", throwable.getMessage());
        return new RemoteDeptService()
        {
            @Override
            public R<List<TreeSelectVo>> deptTreeUser() {
                return R.fail("查询失败");
            }
        };
    }
}
