package com.yutian.mtkviewer.dbcontrol;

import android.content.Context;
import android.widget.Toast;

import com.yutian.mtkviewer.MtkHelperApplication;
import com.yutian.mtkviewer.dbcontrol.greendao.DaoSession;

/**
 * Created by wuwenchuan on 2017/1/9.
 */
public class DBConfigController {

    private static DBConfigController gDBConfigControl = null;
    private static DaoSession gDaoSession = null;

    public static DBConfigController getInstance(Context context) {
        if (gDBConfigControl == null) {
            synchronized (DBConfigController.class) {
                if (gDBConfigControl == null) {
                    gDBConfigControl = new DBConfigController();
                    gDaoSession = MtkHelperApplication.getDaoSession(context);
//                    gDBConfigControl.mSSQConfigDao = gDaoSession.getSSQCONFIGDao();
//                    gDBConfigControl.mPriceLevelDao = gDaoSession.getPRICELEVELDao();
                    if (gDaoSession == null)
                        Toast.makeText(context, "NO database file to show!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Database file to show!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return gDBConfigControl;
    }

}
