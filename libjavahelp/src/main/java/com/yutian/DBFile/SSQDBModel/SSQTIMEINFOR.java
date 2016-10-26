/**
 CREATE TABLE [SSQTIMEINFOR] (
 [PERIOD] VARCHAR(7) NOT NULL ON CONFLICT ABORT,
 [LOTTERYTIME] DATE NOT NULL ON CONFLICT ABORT,
 [ENDTIME] DATE,
 [PERIODSELL] VARCHAR(64),
 [CURRENPOOL] VARCHAR(64),
 CONSTRAINT [sqlite_autoindex_SSQTIMEINFOR_1] PRIMARY KEY ([PERIOD]));
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.util.DateUtil;

import java.sql.Date;

/**
 * 双色球的开奖时间和奖金池信息
 * Created by wuwenchuan on 2016/9/10.
 */
public class SSQTIMEINFOR {
    @ID
    String PERIOD;            //期数

    Date LOTTERYTIME;         //开奖时间
    Date ENDTIME;             //兑奖结束时间
    String PERIODSELL;        //本期卖出去的数目
    String CURRENPOOL;        //当前奖池的数目

    public SSQTIMEINFOR() {

    }

    public SSQTIMEINFOR(PeriodSSQModel ssqModel)
    {
        if (ssqModel != null) {
            PERIOD = ssqModel.getPeriod();
            PERIODSELL = ssqModel.getBqSell();
            CURRENPOOL = ssqModel.getPricePool();

            if (ssqModel.getKjTime() != null) {
                LOTTERYTIME = DateUtil.parseSimpleSqlDate(ssqModel.getDBKJTime());
            }

            if (ssqModel.getDjTime() != null) {
                ENDTIME = DateUtil.parseSimpleSqlDate(ssqModel.getDBDJTime());
            }
        }
    }

    public String getCURRENPOOL() {
        return CURRENPOOL;
    }

    public void setCURRENPOOL(String CURRENPOOL) {
        this.CURRENPOOL = CURRENPOOL;
    }

    public Date getENDTIME() {
        return ENDTIME;
    }

    public void setENDTIME(Date ENDTIME) {
        this.ENDTIME = ENDTIME;
    }

    public Date getLOTTERYTIME() {
        return LOTTERYTIME;
    }

    public void setLOTTERYTIME(Date LOTTERYTIME) {
        this.LOTTERYTIME = LOTTERYTIME;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getPERIODSELL() {
        return PERIODSELL;
    }

    public void setPERIODSELL(String PERIODSELL) {
        this.PERIODSELL = PERIODSELL;
    }
}
