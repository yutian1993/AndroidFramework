package com.yutian.DBFile.SSQControl.SSQModel;

import com.yutian.DBFile.SSQDBModel.MYBUY;
import com.yutian.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的购买记录
 * Created by wuwenchuan on 2016/9/12.
 */
public class MySSQModel {
    private Date time;
    private List<SSQDataModel> ssqDataModels = new ArrayList<>();
    private String period;

    public MySSQModel() {
        time = new Date();
        period = null;
    }

    public MySSQModel(MYBUY mybuy) {
        period = mybuy.getPERIOD();
        time = mybuy.getADDTIME();
        id = mybuy.getID();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<SSQDataModel> getSsqDataModels() {
        return ssqDataModels;
    }

    public void setSsqDataModels(List<SSQDataModel> ssqDataModels) {
        for (SSQDataModel model:
                ssqDataModels) {
            addSsqDataModel(model);
        }
    }

    public void addSsqDataModel(SSQDataModel ssqDataModel) {
        ssqDataModel.setId(DateUtil.foratIDUtilDateFormat(time));
        ssqDataModel.setNumber(ssqDataModels.size()+1);
        ssqDataModels.add(ssqDataModel);
    }

    //use for database
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
