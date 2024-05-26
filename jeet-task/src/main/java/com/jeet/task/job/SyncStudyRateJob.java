package com.jeet.task.job;

import com.jeet.common.core.domain.R;
import com.jeet.common.core.utils.StringUtils;
import com.jeet.common.redis.service.RedisService;
import com.jeet.task.api.RemoteCourseService;
import com.jeet.task.api.domain.CourseStudyRate;
import com.jeet.task.api.vo.CourseStudyRateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 同步学习进度任务
 */
@Component
public class SyncStudyRateJob {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RemoteCourseService remoteCourseService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String keyPattern)
    {
        Collection<String> keys = redisService.keys(keyPattern + ":*");
        Iterator<String> iterator = keys.iterator();
        ArrayList<CourseStudyRateVo> list = new ArrayList<>();
        while (iterator.hasNext()) {
            String next = iterator.next();
            CourseStudyRateVo courseStudyRateVo = redisService.getCacheObject(next);
//            String jsonStr = JSON.toJSONString(cacheObject);
//            CourseStudyRateVo courseStudyRateVo = JSON.parseObject(jsonStr, CourseStudyRateVo.class);
            if (courseStudyRateVo.getIsSync().equals("0")) {
                list.add(courseStudyRateVo);
            }
        }
        if (list.size() > 0) {
            R<CourseStudyRate> courseStudyRateR = remoteCourseService.saveList(list);
            if (courseStudyRateR.getCode() == 200) {
                for (int i = 0; i < list.size(); i++) {
                    CourseStudyRateVo courseStudyRateVo = list.get(i);
                    courseStudyRateVo.setIsSync("1");
                    courseStudyRateVo.setIsSave("1");
                    redisService.setCacheObject("studyRates:" + courseStudyRateVo.getContentId()+ "-" +courseStudyRateVo.getUserId(),courseStudyRateVo);
                }
            }
        }
//        System.out.println("执行有参方法：" + keyPattern);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
}
