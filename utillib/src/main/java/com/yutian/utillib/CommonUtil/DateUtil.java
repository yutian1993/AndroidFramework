package com.yutian.utillib.CommonUtil;

import com.yutian.utillib.ConfigValue.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * yyyy（年）MM（月）dd（日）hh（时）mm（分）ss（秒）SSS（毫秒）
 * Created by wuwenchuan on 2016/10/26.
 */
public class DateUtil {
    private static final SimpleDateFormat CHINESE_SIMPLE_DATE =
            new SimpleDateFormat("yyyy年MM月dd日");
    private static Calendar G_CALENDAR = Calendar.getInstance();

    private static final SimpleDateFormat ID_SIMPLE_DATE =
            new SimpleDateFormat("yyyMMdd");

    private static final SimpleDateFormat DB_SIMPLE_DATE =
            new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将时间转化成2015年12月12日格式
     * @param date 时间
     * @return
     */
    public static String formatChineseDate(Date date) {
        if (date == null)
            return null;

        return CHINESE_SIMPLE_DATE.format(date);
    }

    /**
     * 构造数据库数据ID
     * @param date 时间
     * @return
     */
    public static String formatDBSelfSSQID(Date date) {
        if (date == null)
            date = new Date();

        return ID_SIMPLE_DATE.format(date);
    }

    /**
     * 将week中的某一天转成字符串
     * @param dayofweek
     * @return
     */
    public static String getDayOfWeek(int dayofweek) {
        if (dayofweek < 1 || dayofweek > 7)
            return "";
        switch (dayofweek) {
            case 1:
                return Constants.G_SUNDAY;
            case 2:
                return Constants.G_MONDAY;
            case 3:
                return Constants.G_TUESDAY;
            case 4:
                return Constants.G_WEDNESDAY;
            case 5:
                return Constants.G_THURSDAY;
            case 6:
                return Constants.G_FRIDAY;
            case 7:
                return Constants.G_SATURDAY;
        }
        return "";
    }

    /**
     * 将week中的某一天转成字符串
     * @param day
     * @return
     */
    public static String getDayOfWeek(Date day) {
        if (day == null)
            return null;
        G_CALENDAR.setTime(day);
        return getDayOfWeek(G_CALENDAR.get(Calendar.DAY_OF_WEEK));
    }

    /**
     *
     * @param date
     * @return
     */
    public static Date pareseDBDate(String date) {
        if (date == null || date.isEmpty())
            return null;

        try {
            return DB_SIMPLE_DATE.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatDBDate(Date date) {
        if (date == null)
            return null;

        return DB_SIMPLE_DATE.format(date);
    }
}
