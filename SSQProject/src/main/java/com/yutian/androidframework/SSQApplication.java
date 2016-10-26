package com.yutian.androidframework;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yutian.base.database.def.SSQDBHelper;
import com.yutian.base.database.srcgen.DaoMaster;
import com.yutian.base.database.srcgen.DaoSession;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by yutian on 2016/10/25.
 */

public class SSQApplication extends Application {
    private static DaoMaster gDaoMaster;
    private static DaoSession gDaoSession;
    private static SQLiteDatabase db;

    private static String DB_PATH = null;


    public static DaoMaster getDaoMaster(Context context) {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        if (DB_PATH == null)
            DB_PATH = SSQDBHelper.getInstance().getDBPath(context);

        if (gDaoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,DB_PATH, null);
            gDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }

        return gDaoMaster;
    }


    public static DaoSession getDaoSession(Context context) {
        if (gDaoSession == null) {
            if (gDaoMaster == null) {
                gDaoMaster = getDaoMaster(context);
            }
            gDaoSession = gDaoMaster.newSession();
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
