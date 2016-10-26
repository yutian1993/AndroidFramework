package com.yutian.DBFile.SSQDBDao;

import java.sql.Date;

/**
 * 我的购买
 * Created by wuwenchuan on 2016/9/10.
 */
public class MYBUYDao {

    /**
     * 获取我的所有购买纪录（不包括每条信息）
     * @return
     */
    public static String selectSqlStatement() {
        return "SELECT * FROM MYBUY";
    }

}
