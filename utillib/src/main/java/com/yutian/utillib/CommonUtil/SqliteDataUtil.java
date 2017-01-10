package com.yutian.utillib.CommonUtil;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wuwenchuan on 2017/1/9.
 */
public class SqliteDataUtil {

    /**
     * Judgement a file is a sqlite file or not
     * @param filepath file path
     * @return true or false
     */
    public static boolean isSqliteDB(String filepath) {

        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openDatabase(filepath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ep) {
            db = null;
        }

        if (db == null || !db.isOpen()) {
            return  false;
        }
        return true;
    }
}
