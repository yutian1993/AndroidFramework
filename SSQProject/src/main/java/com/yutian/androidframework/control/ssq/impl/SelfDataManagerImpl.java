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

        if (selfDataModel.getmSSQDataModel().size() > 0) {
            List<SelfSSQDataModel> allObjects
                    = selfDataModel.getmSSQDataModel();

            Date currentDate = new Date();
            String id = DateUtil.formatDBSelfSSQID(currentDate);
            int insertNumber = 0;
            for (SelfSSQDataModel newObj :
                    allObjects) {
                newObj.setId(id);
                newObj.setNumber(gSelfDataContorl.insertSelfSSQDataModel(newObj));
                if (!newObj.isDbDumplicate())
                    insertNumber++;
                newObj.setAddtime(currentDate);
            }

            //插入
            MYBUY newObj = new MYBUY();
            newObj.setPERIOD(selfDataModel.getPeriod());
            newObj.setID(id);
            newObj.setNUMBER(insertNumber);
            newObj.setADDTIME(currentDate);
            return gSelfDataContorl.insertSelfBuyInfor(newObj);
        } else {
            return false;
        }
    }

    @Override
    public boolean updateSelfSSQDataModel(SelfSSQDataModel selfSSQDataModel) {
        return gSelfDataContorl.updateSelfSSQDataModel(selfSSQDataModel);
    }

    @Override
    public boolean deleteSelfSSQDataModel(String period, String id, int number) {
        if (period == null || id == null)
            return false;

        //如果number为-1，则删除该ID系列的所有选择
        if (gSelfDataContorl.deleteSelfSSQDataModel(id, number)) {
            return gSelfDataContorl.deleteBuyInfor(period, id, number);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteSelfSSQDateModel(SelfDataModel selfDataModel) {
        if (selfDataModel.getPeriod() == null) {
            //对于Period为空的提交不给删除，以免造成数据删除错误
            return false;
        }

        List<MYBUY> mybuys = gSelfDataContorl.getSelfBuyInfor(selfDataModel.getPeriod());

        for (MYBUY buy: mybuys) {
            gSelfDataContorl.deleteSelfSSQDataModels(buy.getID());
            gSelfDataContorl.deleteBuyInfor(buy.getPERIOD(), buy.getID(), -1);
        }

        return true;
    }

    @Override
    public boolean insertSelfSSQDataModel(String period, String id, SelfSSQDataModel ssqDataModel) {

        return false;
    }

}
