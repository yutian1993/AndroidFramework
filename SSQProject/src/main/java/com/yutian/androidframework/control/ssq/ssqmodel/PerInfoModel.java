package com.yutian.androidframework.control.ssq.ssqmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Time && Price Information
 * Created by wuwenchuan on 2016/10/26.
 */
public class PerInfoModel {
    String period;
    String kjTimeStr;
    Date kjTime;
    String djTimeStr;
    Date djTime;
    String bqSell;
    String pricePool;

    List<PerPriceNumModel> mPriceNumberModels;

    public PerInfoModel() {
        mPriceNumberModels = new ArrayList<>();
    }

    public String getBqSell() {
        return bqSell;
    }

    public void setBqSell(String bqSell) {
        this.bqSell = bqSell;
    }

    public Date getDjTime() {
        return djTime;
    }

    public void setDjTime(Date djTime) {
        this.djTime = djTime;
    }

    public String getDjTimeStr() {
        return djTimeStr;
    }

    public void setDjTimeStr(String djTimeStr) {
        this.djTimeStr = djTimeStr;
    }

    public Date getKjTime() {
        return kjTime;
    }

    public void setKjTime(Date kjTime) {
        this.kjTime = kjTime;
    }

    public String getKjTimeStr() {
        return kjTimeStr;
    }

    public void setKjTimeStr(String kjTimeStr) {
        this.kjTimeStr = kjTimeStr;
    }

    public List<PerPriceNumModel> getPriceNumberModels() {
        return mPriceNumberModels;
    }

    public void setPriceNumberModels(List<PerPriceNumModel> priceNumberModels) {
        this.mPriceNumberModels = priceNumberModels;
    }

    public void addPriceNumberModel(PerPriceNumModel priceNumModel) {
        mPriceNumberModels.add(priceNumModel);
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPricePool() {
        return pricePool;
    }

    public void setPricePool(String pricePool) {
        this.pricePool = pricePool;
    }
}
