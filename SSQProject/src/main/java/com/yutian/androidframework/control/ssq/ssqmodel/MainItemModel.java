package com.yutian.androidframework.control.ssq.ssqmodel;

import com.yutian.base.util.DateUtil;

import java.util.Calendar;

/**
 * 主页Item列表Model
 * Created by wuwenchuan on 2016/11/3.
 */
public class MainItemModel extends SSQDataModel{
    private String period;
    private String periodTime;
    private String periodWeek;
    private String firstPricenNum;
    private String firstPriceVal;
    private String myPriceNum;
    private String myPriceVal;

    public MainItemModel(PerSSQDataModel perSSQDataModel) {
        super(perSSQDataModel);
        if (perSSQDataModel == null)
            return;
        this.period =perSSQDataModel.getPeriod();
    }

    public void updateSSQDataModel(PerSSQDataModel perSSQDataModel) {
        if (perSSQDataModel == null)
            return;
        this.setRed1(perSSQDataModel.getRed1());
        this.setRed2(perSSQDataModel.getRed2());
        this.setRed3(perSSQDataModel.getRed3());
        this.setRed4(perSSQDataModel.getRed4());
        this.setRed5(perSSQDataModel.getRed5());
        this.setRed6(perSSQDataModel.getRed6());
        this.setBlue(perSSQDataModel.getBlue());
        this.setRedballs(perSSQDataModel.getRedballs());
        this.setRedseqballs(perSSQDataModel.getRedseqballs());
        this.setBlueballs(perSSQDataModel.getBlueballs());
    }

    public void updateSSQTimeInfor(PerInfoModel perInfoModel) {
        this.periodTime = DateUtil.formatChineseDate(perInfoModel.getKjTime());
        this.periodWeek = DateUtil.getDayOfWeek(perInfoModel.getKjTime());
        //后续根据需求添加
//        this.periodTime = new Calendar(perInfoModel.getKjTime());
    }

    public void updateSSQPriceInfor(PerPriceNumModel priceNumModel) {
        this.firstPricenNum = priceNumModel.getPriceNumber();
        this.firstPriceVal = priceNumModel.getPriceValue();
    }

    public String getFirstPricenNum() {
        return firstPricenNum;
    }

    public void setFirstPricenNum(String firstPricenNum) {
        this.firstPricenNum = firstPricenNum;
    }

    public String getFirstPriceVal() {
        return firstPriceVal;
    }

    public void setFirstPriceVal(String firstPriceVal) {
        this.firstPriceVal = firstPriceVal;
    }

    public String getMyPriceNum() {
        return myPriceNum;
    }

    public void setMyPriceNum(String myPriceNum) {
        this.myPriceNum = myPriceNum;
    }

    public String getMyPriceVal() {
        return myPriceVal;
    }

    public void setMyPriceVal(String myPriceVal) {
        this.myPriceVal = myPriceVal;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public String getPeriodWeek() {
        return periodWeek;
    }

    public void setPeriodWeek(String periodWeek) {
        this.periodWeek = periodWeek;
    }
}
