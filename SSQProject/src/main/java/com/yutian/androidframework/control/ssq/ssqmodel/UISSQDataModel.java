package com.yutian.androidframework.control.ssq.ssqmodel;

import java.util.Date;

/**
 * Created by wuwenchuan on 2016/10/26.
 */
public class UISSQDataModel extends SelfSSQDataModel {

    //记录的添加时间
    private Date uiAddDate;
    //Add for UI status(用来判断当前的Model是否需要保存到数据库中)
    private boolean needSave = false;

    private int pay = 2;

    public UISSQDataModel() {
        super();
    }

    public UISSQDataModel(UISSQDataModel parent) {
        setCount(parent.getCount());
        setPay(parent.getPay());
        setNeedSave(parent.isNeedSave());
        setId(parent.getId());
        setNumber(parent.getNumber());
        setRedBallList(parent.getRedBallList());
        setBlueBallList(parent.getBlueBallList());
    }

    public UISSQDataModel(SSQDataModel parent) {
        setRedBallList(parent.getRedBallList());
        setBlueBallList(parent.getBlueBallList());
    }

    public boolean isNeedSave() {
        return needSave;
    }

    public void setNeedSave(boolean needSave) {
        this.needSave = needSave;
    }

    public Date getUiAddDate() {
        return uiAddDate;
    }

    public void setUiAddDate(Date uiAddDate) {
        this.uiAddDate = uiAddDate;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }
}
