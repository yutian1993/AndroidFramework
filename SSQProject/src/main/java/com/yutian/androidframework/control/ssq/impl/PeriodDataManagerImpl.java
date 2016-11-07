package com.yutian.androidframework.control.ssq.impl;

import android.content.Context;

import com.yutian.androidframework.control.ssq.PeriodDataManager;
import com.yutian.androidframework.control.ssq.dbcontrol.DBConfigControl;
import com.yutian.androidframework.control.ssq.dbcontrol.PeriodDataControl;
import com.yutian.androidframework.control.ssq.dbcontrol.SelfDataControl;
import com.yutian.androidframework.control.ssq.ssqmodel.MainItemModel;
import com.yutian.androidframework.control.ssq.ssqmodel.PerSSQDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2016/11/3.
 */
public class PeriodDataManagerImpl implements PeriodDataManager {
    private DBConfigControl gDBConfigControl;
    private PeriodDataControl gPeriodDataControl;
    private SelfDataControl gSelfDataControl;

    public PeriodDataManagerImpl(Context context) {
        gDBConfigControl = DBConfigControl.getInstance(context);
        gPeriodDataControl = PeriodDataControl.getInstance(context);
        gSelfDataControl = SelfDataControl.getInstance(context);
    }

    @Override
    public List<MainItemModel> getTopTenInfor(String period) {
        List<MainItemModel> results = new ArrayList<>();
        List<PerSSQDataModel> ssqresults = gPeriodDataControl.getTenDataFromPeriod(period, true);
        for (PerSSQDataModel ssqdata: ssqresults) {
            MainItemModel newObj = new MainItemModel(ssqdata);
            newObj.updateSSQPriceInfor(gPeriodDataControl.getPeriodPriceNumFromPeriod(newObj.getPeriod()).get(0));
            newObj.updateSSQTimeInfor(gPeriodDataControl.getPeriodInformationFromPeriod(newObj.getPeriod()));
            results.add(newObj);
        }
        return results;
    }
}
