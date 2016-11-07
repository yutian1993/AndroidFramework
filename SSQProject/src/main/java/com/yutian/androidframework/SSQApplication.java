package com.yutian.androidframework;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;

import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.androidframework.control.ssq.dbcontrol.DBConfigControl;
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
        //初始化文本内容
        Constants.G_TXT_MAINPERIOD_INFOR = getResources().getString(R.string.ssq_mainperiod_infor);
        Constants.G_TXT_MAINPERIOD_TIME_INFOR = getResources().getString(R.string.ssq_mainperiod_time_infor);
        Constants.G_TXT_MAINPERIOD_PRICE_INFOR = getResources().getString(R.string.ssq_mainperiod_price_infor);
        Constants.G_TXT_MAINPERIOD_PRICE_VAL_INFOR = getResources().getString(R.string.ssq_mainperiod_value_infor);
        Constants.G_TXT_MAINPERIOD_MOTE_TEXT = getResources().getString(R.string.ssq_mainitem_more_text);
        Constants.G_TXT_MAINPERIOD_MYPRICE_INFOR = getResources().getString(R.string.ssq_mainperiod_myprice_infor);
        Constants.G_TXT_MAINPERIOD_MYPRICE = getResources().getString(R.string.ssq_mainperiod_myprice);

        //初始化文本颜色
        Constants.G_COLOR_MAINITEM_TXT = ContextCompat.getColor(this, R.color.mainitem_txt_color);
        Constants.G_COLOR_MAINITEM_TXT_RED = ContextCompat.getColor(this, R.color.mainitem_txt_red_color);
        Constants.G_COLOT_MAINITEM_TXT_FLASH_RED = ContextCompat.getColor(this, R.color.mainitem_txt_flashred_color);
        Constants.G_COLOR_MAINITEM_TXT_GREEN = ContextCompat.getColor(this, R.color.mainitem_txt_green_color);

        //星期信息
        Constants.G_SUNDAY = getResources().getString(R.string.ssq_week_one);
        Constants.G_MONDAY = getResources().getString(R.string.ssq_week_two);
        Constants.G_TUESDAY = getResources().getString(R.string.ssq_week_three);
        Constants.G_WEDNESDAY = getResources().getString(R.string.ssq_week_four);
        Constants.G_THURSDAY = getResources().getString(R.string.ssq_week_five);
        Constants.G_FRIDAY = getResources().getString(R.string.ssq_week_six);
        Constants.G_SATURDAY = getResources().getString(R.string.ssq_week_seven);

        //ConfigControl需要提前初始化
        DBConfigControl.getInstance(this);

        super.onCreate();
    }
}
