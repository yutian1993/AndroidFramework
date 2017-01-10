package com.yutian.utillib.CommonUtil;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wuwenchuan on 2017/1/9.
 */
public class SharedPreferenceUtil {

    private static String SHARE_PREFERENCE = "MTKDATA";
    private static String DATABASE_PATH = "DBPATH";
    private static String DATABASE_NAME = "DBNAME";

    /**
     * Get DB file path
     * @param context
     * @return
     */
    public static String getDBPath(Context context) {
        String dbpath = null;

        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);

            if (sp != null)
                dbpath = sp.getString(DATABASE_PATH, null);
        }

        return dbpath;
    }

    /**
     * Get Db Name
     * @param context
     * @return
     */
    public static String getDBName(Context context) {
        String dbname = null;

        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);

            if (sp != null)
                dbname = sp.getString(DATABASE_NAME, null);
        }

        return dbname;
    }

    /**
     * Save db file path
     * @param context
     * @param dbpath
     * @return
     */
    public static boolean saveDBPath(Context context, String dbpath) {
        boolean result = false;

        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(DATABASE_PATH, dbpath);
            editor.putString(DATABASE_NAME, FileUtil.getFileName(dbpath, null));
            editor.commit();
            result = true;
        }

        return result;
    }

    /**
     * Save db name
     * @param context
     * @param dbname
     * @return
     */
    public static boolean saveDBName(Context context, String dbname) {
        boolean result = false;

        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(DATABASE_NAME, dbname);
            editor.commit();
            result = true;
        }

        return result;
    }

}
