package com.yutian.androidframework.control.ssq.dbcontrol;

import android.content.Context;

import com.yutian.androidframework.SSQApplication;
import com.yutian.base.database.srcgen.DaoSession;
import com.yutian.base.database.srcgen.SSQCONFIG;
import com.yutian.base.database.srcgen.SSQCONFIGDao;

import java.util.List;

/**
 * Created by wuwenchuan on 2016/10/25.
 */
public class DBConfigControl {
    private static String TAG = DBConfigControl.class.getSimpleName();
    private static DBConfigControl gDBConfigControl = null;
    private static DaoSession gDaoSession = null;

    private SSQCONFIGDao mSSQConfigDao;

    public static DBConfigControl getInstance(Context context) {
        if (gDBConfigControl == null) {
            synchronized (DBConfigControl.class) {
                if (gDBConfigControl == null) {
                    gDBConfigControl = new DBConfigControl();
                    gDaoSession = SSQApplication.getDaoSession(context);
                    gDBConfigControl.mSSQConfigDao = gDaoSession.getSSQCONFIGDao();
                }
            }
        }
        return gDBConfigControl;
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

}
