package com.yutian.androidframework.control.ssq.impl;

import android.content.Context;

import com.yutian.androidframework.control.ssq.SelfDataManager;
import com.yutian.androidframework.control.ssq.dbcontrol.SelfDataControl;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfDataModel;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfSSQDataModel;
import com.yutian.base.database.srcgen.MYBUY;
import com.yutian.base.util.DateUtil;
import com.yutian.util.SSQLibUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by wuwenchuan on 2016/10/26.
 */
public class SelfDataManagerImpl implements SelfDataManager {

    private static SelfDataControl gSelfDataContorl = null;

    public SelfDataManagerImpl(Context context) {
        gSelfDataContorl = SelfDataControl.getInstance(context);
    }

    @Override
    public SelfDataModel getBuyInformation(String period) {
        return null;
    }

    @Override
    public SelfDataModel getSelfSSQDataModels(String id) {
        return null;
    }

    @Override
    public SelfSSQDataModel getSelfSSQDataModel(String id, String number) {
        return null;
    }

    @Override
    public boolean insertSelfSSQDataModel(SelfDataModel selfDataModel) {
        if (selfDataModel.getPeriod() == null) {
            selfDataModel.setPeriod(SSQLibUtil.currentPeriod());
        }

        if (selfDataModel.getmSSQDataModel().size() > 0)
        {
            List<SelfSSQDataModel> allObjects
                    = selfDataModel.getmSSQDataModel();

            Date currentDate = new Date();
            String id = DateUtil.formatDBSelfSSQID(currentDate);
            for (SelfSSQDataModel newObj:
                    allObjects) {
                newObj.setId(id);
                gSelfDataContorl.insertSelfSSQDataModel(newObj);
            }

            //插入
            MYBUY newObj = new MYBUY();
            newObj.setPERIOD(selfDataModel.getPeriod());
            newObj.setID(id);
            newObj.setNUMBER(allObjects.size());
            newObj.setADDTIME(currentDate);
            gSelfDataContorl.insertSelfBuyInfor(newObj);
        }
        return true;
    }

    @Override
    public boolean insertSelfSSQDataModel(String period, String id, SelfSSQDataModel ssqDataModel) {
        return false;
    }
}
