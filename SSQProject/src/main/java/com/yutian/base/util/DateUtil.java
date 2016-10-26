package com.yutian.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * yyyy（年）MM（月）dd（日）hh（时）mm（分）ss（秒）SSS（毫秒）
 * Created by wuwenchuan on 2016/10/26.
 */
public class DateUtil {
    private static final SimpleDateFormat CHINESE_SIMPLE_DATE =
            new SimpleDateFormat("yyyy年MM月dd日");

    private static final SimpleDateFormat ID_SIMPLE_DATE =
            new SimpleDateFormat("yyyMMdd");

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
}
