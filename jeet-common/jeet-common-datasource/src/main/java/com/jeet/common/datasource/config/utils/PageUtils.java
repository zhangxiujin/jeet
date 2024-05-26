package com.jeet.common.datasource.config.utils;

import com.github.pagehelper.PageHelper;
import com.jeet.common.core.utils.sql.SqlUtil;
import com.jeet.common.datasource.config.web.page.PageDomain;
import com.jeet.common.datasource.config.web.page.TableSupport;

/**
 * 分页工具类
 * 
 * @author jeet
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
