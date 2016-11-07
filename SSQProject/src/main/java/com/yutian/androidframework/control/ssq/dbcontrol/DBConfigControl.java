package com.yutian.androidframework.control.ssq.dbcontrol;

import android.content.Context;

import com.yutian.androidframework.SSQApplication;
import com.yutian.base.database.srcgen.DaoSession;
import com.yutian.base.database.srcgen.PRICELEVEL;
import com.yutian.base.database.srcgen.PRICELEVELDao;
import com.yutian.base.database.srcgen.SSQCONFIG;
import com.yutian.base.database.srcgen.SSQCONFIGDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuwenchuan on 2016/10/25.
 */
public class DBConfigControl {
    private static String TAG = DBConfigControl.class.getSimpleName();
    private static DBConfigControl gDBConfigControl = null;
    private static DaoSession gDaoSession = null;

    private SSQCONFIGDao mSSQConfigDao = null;
    private PRICELEVELDao mPriceLevelDao = null;
    private static Map<Integer, String> mPriveLevel = null;
    private static Map<Integer, String> mPrivelVal = null;

    public static DBConfigControl getInstance(Context context) {
        if (gDBConfigControl == null) {
            synchronized (DBConfigControl.class) {
                if (gDBConfigControl == null) {
                    gDBConfigControl = new DBConfigControl();
                    gDaoSession = SSQApplication.getDaoSession(context);
                    gDBConfigControl.mSSQConfigDao = gDaoSession.getSSQCONFIGDao();
                    gDBConfigControl.mPriceLevelDao = gDaoSession.getPRICELEVELDao();
                }
            }
        }
        return gDBConfigControl;
    }

    /**
     * 获取数据库中内置的奖金等级
     * @param level
     * @return
     */
    public static String getLevelName(int level) {
        if (mPriveLevel==null)
            return null;
        return mPriveLevel.get(level);
    }

    /**
     * 获取数据库中内置的奖金值
     * @param level
     * @return
     */
    public static String getLevelVal(int level) {
        if (mPrivelVal == null)
            return null;
        return mPrivelVal.get(level);
    }

    /**
     * 获取指定的Val
     * @param name
     * @return
     */
    public String getConfigVal(String name) {
        List<SSQCONFIG> allVals = mSSQConfigDao.queryBuilder().
                where(SSQCONFIGDao.Properties.NAME.like(name)).list();
        if (allVals.size() == 0) {
            return null;
        } else {
            return allVals.get(0).getVALUE();
        }
    }

    /**
     * 添加Config value
     * @param name 名称
     * @param val 值
     * @return
     */
    public boolean addNewConfigVal(String name, String val) {
        SSQCONFIG newObj = new SSQCONFIG();
        newObj.setNAME(name);
        newObj.setVALUE(val);
        if (getConfigVal(name) != null) {
            mSSQConfigDao.update(newObj);
        } else {
            mSSQConfigDao.insert(newObj);
        }
        return true;
    }

    /**
     * 删除指定选项
     * @param name
     * @return
     */
    public boolean deleteConfigVal(String name) {
        List<SSQCONFIG> allVals = mSSQConfigDao.queryBuilder().
                where(SSQCONFIGDao.Properties.NAME.eq(name)).list();
        for (SSQCONFIG ssqconfig:
             allVals) {
            mSSQConfigDao.delete(ssqconfig);
        }
        return true;
    }

    /**
     * 更新内存中的数据信息
     * @return
     */
    public boolean loadPriveLevel() {
        if (mPriveLevel == null) {
            mPriveLevel = new HashMap<>();
            mPrivelVal = new HashMap<>();
        }
        else {
            mPriveLevel.clear();
            mPrivelVal.clear();
        }

        List<PRICELEVEL> results = mPriceLevelDao.queryBuilder().list();
        for (PRICELEVEL pricelevel: results) {
            mPriveLevel.put(pricelevel.getLEVEL(), pricelevel.getNAME());
            mPrivelVal.put(pricelevel.getLEVEL(), pricelevel.getPRESETPRICE());
        }

        return true;
    }

}
