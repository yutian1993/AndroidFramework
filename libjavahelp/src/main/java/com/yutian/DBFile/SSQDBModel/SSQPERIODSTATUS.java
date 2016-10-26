/**
 CREATE TABLE [SSQPERIODSTATUS] (
 [PERIOD] VARCHAR(7),
 [STATUS] CHAR(1) DEFAULT N);
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;

/**
 * 双色球的数据是否获取
 * Created by wuwenchuan on 2016/9/10.
 */
public class SSQPERIODSTATUS {
    @ID
    String PERIOD;            //期数

    String STATUS;            //双色球数据获取状态

    public SSQPERIODSTATUS() {

    }

    public SSQPERIODSTATUS(String period, boolean status)
    {
        PERIOD = period;
        STATUS = status ? "Y" : "N";
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
