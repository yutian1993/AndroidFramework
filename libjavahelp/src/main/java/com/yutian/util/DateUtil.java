package com.yutian.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by wuwenchuan on 2016/9/10.
 */
public class DateUtil {
    public static SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat IDDATEFORMAT = new SimpleDateFormat("yyyyMMddhhmmss");

    public static java.util.Date covertSQLDateToUtilDate(java.sql.Date date)
    {
        return new java.util.Date(date.getTime());
    }

    public static java.sql.Date covertUtilDateToSQLDate(java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 将SQL Date转换成yyyy-MM-dd的格式
     * @param date Date对象
     * @return Date对象的Simple格式字符串
     */
    public static String formatSimpleSqlDateFormat(java.sql.Date date)
    {
        if (date == null)
            return null;

        return SIMPLEDATEFORMAT.format(covertSQLDateToUtilDate(date));
    }

    /**
     * 将UTIL Date转换成yyyy-MM-dd的格式
     * @param date Date对象
     * @return Date对象的Simple格式字符串
     */
    public static String formatSimpleUtilDateFormat(java.util.Date date)
    {
        if (date == null)
            return null;

        return SIMPLEDATEFORMAT.format(date);
    }

    /**
     *
     * @param date
     * @return
     */
    public static java.util.Date parseSimpleUtilDate(String date)
    {
        if (date == null)
            return null;

        try {
            return SIMPLEDATEFORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static java.sql.Date parseSimpleSqlDate(String date)
    {
        if (date == null)
            return null;
        try {
            return covertUtilDateToSQLDate(SIMPLEDATEFORMAT.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String foratIDUtilDateFormat(java.util.Date date)
    {
        if (date == null)
            return null;

        return IDDATEFORMAT.format(date);
    }

}
