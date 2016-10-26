/**
 CREATE TABLE [MYWIN] (
 [PERIOD] VARCHAR(7) NOT NULL ON CONFLICT ABORT CONSTRAINT [pirce period] REFERENCES [SSQPERIODDATA]([PERIOD]) ON DELETE RESTRICT ON UPDATE RESTRICT,
 [ID] CHAR(14) NOT NULL,
 [NUMBER] INT NOT NULL ON CONFLICT ABORT,
 [PRICELEVEL] INT NOT NULL CONSTRAINT [price level] REFERENCES [PRICELEVEL]([LEVEL]) ON DELETE RESTRICT ON UPDATE RESTRICT,
 [PRICEVAL] VARCHAR(64));
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;

/**
 * 我的中奖
 * Created by wuwenchuan on 2016/9/10.
 */
public class MYWIN {
    @ID
    String ID;           //该注购买的ID
    @ID
    Integer NUMBER;          //该期第几注购买

    Integer PRICELEVEL;      //中奖等级
    String PRICEVAL;     //奖金数目
    String PERIOD;       //双色球的期数

    public MYWIN() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(Integer NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public Integer getPRICELEVEL() {
        return PRICELEVEL;
    }

    public void setPRICELEVEL(Integer PRICELEVEL) {
        this.PRICELEVEL = PRICELEVEL;
    }

    public String getPRICEVAL() {
        return PRICEVAL;
    }

    public void setPRICEVAL(String PRICEVAL) {
        this.PRICEVAL = PRICEVAL;
    }
}
