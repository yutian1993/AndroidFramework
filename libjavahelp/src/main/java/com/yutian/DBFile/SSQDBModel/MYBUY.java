/**
 CREATE TABLE [MYBUY] (
 [ID] CHAR(14) NOT NULL,
 [NUMBER] INT NOT NULL,
 [ADDTIME] DATETIME NOT NULL ON CONFLICT ABORT,
 [PERIOD] VARCHAR(7),
 [COUNT] INT);
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;
import com.yutian.DBFile.SSQControl.SSQModel.MySSQModel;
import com.yutian.util.DateUtil;

import java.sql.Date;

/**
 * 我的购买
 * Created by wuwenchuan on 2016/9/10.
 */
public class MYBUY {
    @ID
    String ID;           //该注购买的ID

    Date ADDTIME;        //添加的时间
    String PERIOD;       //对应的双色球期数

    public MYBUY() {
        //Do nothing
    }

    public MYBUY(MySSQModel mySSQModel) {
        ID = DateUtil.foratIDUtilDateFormat(mySSQModel.getTime());
        ADDTIME = new java.sql.Date(mySSQModel.getTime().getTime());
        PERIOD = mySSQModel.getPeriod();
    }

    public Date getADDTIME() {
        return ADDTIME;
    }

    public void setADDTIME(Date ADDTIME) {
        this.ADDTIME = ADDTIME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }
}
