package com.yutian.androidframework.control.ssq.dbcontrol;

import android.content.Context;

import com.yutian.androidframework.SSQApplication;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.androidframework.control.ssq.ssqmodel.PerSSQDataModel;
import com.yutian.base.database.srcgen.DaoSession;
import com.yutian.base.database.srcgen.SSQNUMBERWINNERDao;
import com.yutian.base.database.srcgen.SSQPERIODDATA;
import com.yutian.base.database.srcgen.SSQPERIODDATADao;
import com.yutian.base.database.srcgen.SSQPERIODSTATUSDao;
import com.yutian.base.database.srcgen.SSQTIMEINFORDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wuwenchuan on 2016/10/25.
 */
public class PeriodDataControl {
    private static String TAG = PeriodDataControl.class.getSimpleName();

    private static PeriodDataControl gPeriodDataControl = null;
    private static Context gAppContext;
    private static DaoSession gDaoSession;

    private SSQPERIODDATADao mSSQPeriodDataDao = null;
    private SSQPERIODSTATUSDao mSSQPeriodStatusDao = null;
    private SSQTIMEINFORDao mSSQTimeInforDao = null;
    private SSQNUMBERWINNERDao mSSQNumWinnerDao = null;


    public static PeriodDataControl getInstance(Context context) {
        if (gPeriodDataControl == null) {
            synchronized (PeriodDataControl.class) {
                if (gPeriodDataControl == null) {
                    gPeriodDataControl = new PeriodDataControl();

                    gAppContext = context.getApplicationContext();
                    gDaoSession = SSQApplication.getDaoSession(gAppContext);
                    gPeriodDataControl.mSSQPeriodDataDao = gDaoSession.getSSQPERIODDATADao();
                    gPeriodDataControl.mSSQPeriodStatusDao = gDaoSession.getSSQPERIODSTATUSDao();
                    gPeriodDataControl.mSSQNumWinnerDao = gDaoSession.getSSQNUMBERWINNERDao();
                    gPeriodDataControl.mSSQTimeInforDao = gDaoSession.getSSQTIMEINFORDao();
                }
            }
        }

        return gPeriodDataControl;
    }

    /**
     * 每次获取前是个数据，可以指定降序或者升序的方式排列
     * @param period
     * @param desc
     * @return
     */
    public List<PerSSQDataModel> getTenDataFromPeriod(String period, boolean desc) {
        QueryBuilder queryBuilder = mSSQPeriodDataDao.queryBuilder();
        if (period != null) {
            if (desc)
                queryBuilder.where(SSQPERIODDATADao.Properties.PERIOD.lt(period));
            else
                queryBuilder.where(SSQPERIODDATADao.Properties.PERIOD.gt(period));
        }

        queryBuilder.limit(Constants.SPLIT_COUNT);
        queryBuilder.orderDesc(SSQPERIODDATADao.Properties.PERIOD);
        List<SSQPERIODDATA> mListVals = queryBuilder.list();
        return PerSSQDataModel.convertPeriodSSQDatasToPeriodSSQDataModels(mListVals);
    }
}
