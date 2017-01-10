package com.yutian.mtkviewer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yutian.mtkviewer.dbcontrol.DBConfigController;
import com.yutian.mtkviewer.dbcontrol.greendao.DaoMaster;
import com.yutian.mtkviewer.dbcontrol.greendao.DaoSession;
import com.yutian.utillib.CommonUtil.SharedPreferenceUtil;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wuwenchuan on 2016/12/12.
 */
public class MtkHelperApplication extends Application {

    private static DaoMaster gDaoMaster;
    private static DaoSession gDaoSession;
    private static SQLiteDatabase db;

    private static String DB_PATH = null;


    public static DaoMaster getDaoMaster(Context context) {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        if (DB_PATH == null) {
            DB_PATH = SharedPreferenceUtil.getDBPath(context);
            System.out.println(DB_PATH);
            if (DB_PATH != null) {
                if (gDaoMaster == null) {
                    DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,DB_PATH, null);
                    gDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }

        return gDaoMaster;
    }


    public static DaoSession getDaoSession(Context context) {
        if (gDaoSession == null) {
            if (gDaoMaster == null) {
                gDaoMaster = getDaoMaster(context);
                if (gDaoMaster != null)
                    gDaoSession = gDaoMaster.newSession();
            }
        }
        return gDaoSession;
    }



    public static SQLiteDatabase getSQLDatebase(Context context) {
        if (gDaoSession == null) {
            if (gDaoMaster == null) {
                gDaoMaster = getDaoMaster(context);
            }
            db = gDaoMaster.getDatabase();
        }
        return db;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
