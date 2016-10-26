package com.yutian.androidframework.control.ssq.ssqmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人选择信息
 * Created by wuwenchuan on 2016/10/25.
 */
public class SelfDataModel {
    private String period;
    private List<SelfSSQDataModel> mSSQDataModel;

    public SelfDataModel() {
        period = null;
        mSSQDataModel = new ArrayList<>();
    }

    public List<SelfSSQDataModel> getmSSQDataModel() {
        return mSSQDataModel;
    }

    public void setmSSQDataModel(List<SelfSSQDataModel> mSSQDataModel) {
        this.mSSQDataModel = mSSQDataModel;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public boolean addNewSSQDataModel(SelfSSQDataModel selfSSQDataModel) {
        return mSSQDataModel.add(selfSSQDataModel);
    }
}
