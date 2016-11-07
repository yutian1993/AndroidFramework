package com.yutian.androidframework.control.ssq.ssqmodel;

import com.yutian.base.database.srcgen.SSQTIMEINFOR;
import com.yutian.base.util.DateUtil;

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

    public PerInfoModel() {
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

    /**
     * 将数据库中DO对象转成Model
     * @param ssqtimeinfor
     * @return
     */
    public static PerInfoModel convertSSQTIMEINFORToPerInfoModel(SSQTIMEINFOR ssqtimeinfor) {
        PerInfoModel newObj = new PerInfoModel();
        newObj.setKjTime(ssqtimeinfor.getLOTTERYTIME());
        newObj.setDjTime(ssqtimeinfor.getENDTIME());
        newObj.setKjTimeStr(DateUtil.formatChineseDate(newObj.getKjTime()));
        newObj.setDjTimeStr(DateUtil.formatChineseDate(newObj.getDjTime()));
        newObj.setPeriod(ssqtimeinfor.getPERIOD());
        newObj.setBqSell(ssqtimeinfor.getPERIODSELL());
        newObj.setPricePool(ssqtimeinfor.getCURRENPOOL());
        return newObj;
    }

    /**
     * 将数据库中的数据转换成Model
     * @param ssqtimeinfors
     * @return
     */
    public static List<PerInfoModel> convertSSQTIMEINFORsToPerInfoModels(List<SSQTIMEINFOR> ssqtimeinfors) {
        List<PerInfoModel> newObjs = new ArrayList<>();

        for (SSQTIMEINFOR timeInfor : ssqtimeinfors) {
            newObjs.add(convertSSQTIMEINFORToPerInfoModel(timeInfor));
        }

        return newObjs;
    }

}

