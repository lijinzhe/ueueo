package com.ueueo.time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 *
 * @author Lee
 * @date 2019-08-21 16:04
 */
public class TimeUtils {

    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取时间对象的年，例如2019-12-30 18:30:30 则返回2019
     *
     * @param date
     *
     * @return 年份数字
     */
    public static int getYear(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取时间对象的月份，1-12
     *
     * 例如2019-12-30 18:30:30 则返回12
     *
     * @param date
     *
     * @return 月份数字
     */
    public static int getMonth(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取时间对象是当月的几号，1-31
     *
     * 例如2019-12-30 18:30:30 则返回30
     *
     * @param date
     *
     * @return 几号
     */
    public static int getDayOfMonth(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取是今天的第几分钟，0点0分返回0  1点20返回80
     *
     * @param date
     *
     * @return
     */
    public static int getMinuteOfDay(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour * 24 + minute;
    }

    /**
     * 时间对象格式化
     *
     * @param date
     * @param pattern 要格式化的表达式 例如 yyyy-MM-dd HH:mm:ss，输出结果为 2019-12-30 18:30:31
     *
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat dayFormat = new SimpleDateFormat(pattern);
        return dayFormat.format(date);
    }

    public static LocalDate parseToLocalDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static YearMonth parseToYearMonth(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }
}
