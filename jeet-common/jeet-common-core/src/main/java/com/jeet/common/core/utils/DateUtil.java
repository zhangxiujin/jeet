package com.jeet.common.core.utils;

import com.jeet.common.core.base.Base;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kernel
 */
public class DateUtil extends Base {

    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /***
     *
     * @param date 时间
     * @return cron类型的日期
     */
    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }


    /**
     * 获取系统当前时间Date类型
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 获取当前年份
     */
    public static Integer getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 获取当前月份
     */
    public static Integer getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date date = new Date();
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 获取当前日
     */
    public static Integer getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date date = new Date();
        return Integer.valueOf(sdf.format(date));
    }

    /**
     * 返回当前时间以yyyy-MM-dd HH:mm:ss格式的字符串类型值返回
     */
    public static String getCurrentDateStrByYmdHms() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(new Date(System.currentTimeMillis()));
    }

    /**
     * 将当前时间以"yyyy-MM-dd"格式的字符串返回
     *
     * @return
     */
    public static String getCurrentDateStrByYmd() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    /**
     * 格式化日期为字符串
     */
    public static String format(Date date, String pattern) {
        if (!isEmpty(date)) {
            return new SimpleDateFormat(pattern).format(date);
        }
        return NULLSTR;
    }

    /**
     * 字符串类型时间格式转为Date类型时间对象
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期对象
     */
    public static Date convert(String date, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }

    /**
     * 获取某年某月的最后一天，默认日期至格式为 yyyy-MM-dd
     *
     * @param year
     * @param month
     * @return 返回的格式为 yyyy-MM-dd
     */
    public static String getLastDayOfMonth(int year, int month) {
        return getLastDayOfMonth(year, month, "yyyy-MM-dd");
    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     * @param month
     * @return 返回格式为 参数datePattern的格式
     */
    public static String getLastDayOfMonth(int year, int month, String datePattern) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 设置时间
     *
     * @param date        要设置时间的日期
     * @param hour        小时 ,可不传
     * @param minute      分，可不传
     * @param second      秒，可不传
     * @param millisecond 毫秒，可不传
     * @return
     */
    public static Date setTime(Date date, Integer hour, Integer minute, Integer second, Integer millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (hour != null) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != null) {
            cal.set(Calendar.MINUTE, minute);
        }
        if (second != null) {
            cal.set(Calendar.SECOND, second);
        }
        if (millisecond != null) {
            cal.set(Calendar.MILLISECOND, millisecond);
        }
        return cal.getTime();
    }

    /**
     * 分钟 转 天，小时，分钟
     *
     * @param min
     * @return
     */
    public static String minConvertDayHourMin(Double min) {
        String html = "0分";
        if (min != null) {
            Double m = (Double) min;
            String format;
            Object[] array;
            Integer days = (int) (m / (60 * 24));
            Integer hours = (int) (m / (60) - days * 24);
            Integer minutes = (int) (m - hours * 60 - days * 24 * 60);
            if (days > 0) {
                format = "%1$,d天%2$,d时%3$,d分";
                array = new Object[]{days, hours, minutes};
            } else if (hours > 0) {
                format = "%1$,d时%2$,d分";
                array = new Object[]{hours, minutes};
            } else {
                format = "%1$,d分";
                array = new Object[]{minutes};
            }
            html = String.format(format, array);
        }
        return html;
    }

    /**
     * 天 转 毫秒时间
     */
    public static Long dayToMillis(BigDecimal day) {
        BigDecimal dayMillis = day.multiply(new BigDecimal("24"))
                .multiply(new BigDecimal("60"))
                .multiply(new BigDecimal("60"))
                .multiply(new BigDecimal("1000"));
        return dayMillis.longValue();
    }


    /**
     * 天、小时、分钟 转 分钟
     *
     * @param day  天
     * @param hour 小时
     * @param min  分钟
     */
    public static int dayHourMinConvertMin(int day, int hour, int min) {
        int days = day * 24 * 60;
        int hours = hour * 60;
        return days + hours + min;
    }

    /**
     * 在给定日期加减时间
     * @param date
     * @param days
     * @return
     */
    public static Date dayAfterDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static void main(String[] args) {
//		System.out.println(getCurrentDate());
//		System.out.println(getCurrentDay());
//		System.out.println(getCurrentMonth());
//		System.out.println(getCurrentYear());
//		System.out.println(format(new Date(), "yyyy年MM月dd日"));
        System.out.println(getLastDayOfMonth(2020, 12, "yyyy/MM/dd 23:59:59"));
    }
}
