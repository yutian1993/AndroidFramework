/**
 CREATE TABLE [SSQNUMBERWINNER] (
 [PERIOD] VARCHAR(7) NOT NULL ON CONFLICT ABORT CONSTRAINT [pirce period] REFERENCES [SSQPERIODDATA]([PERIOD]) ON DELETE RESTRICT ON UPDATE RESTRICT,
 [PRICELEVEL] INT NOT NULL CONSTRAINT [price level] REFERENCES [PRICELEVEL]([LEVEL]) ON DELETE RESTRICT ON UPDATE RESTRICT,
 [PRICEVAL] VARCHAR(64),
 [PRICENUM] VARCHAR(64));
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;
import com.yutian.DBFile.SSQControl.SSQModel.KJZQModel;

/**
 * 每期双色球的中奖数目
 * Created by wuwenchuan on 2016/9/10.
 */
public class SSQNUMBERWINNER {
    @ID
    String PERIOD;             //双色球期数

    Integer PRICELEVEL;            //奖金等级
    String PRICEVAL;           //奖金数目
    String PRICENUM;           //中奖人数

    public SSQNUMBERWINNER() {

    }

    public SSQNUMBERWINNER(String period, KJZQModel kjzqModel)
    {
        PERIOD = period;
        PRICELEVEL = kjzqModel.getDb_priceLevel();
        PRICEVAL = kjzqModel.getPriceValue();
        PRICENUM = kjzqModel.getPriceNumber();
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

    public String getPRICENUM() {
        return PRICENUM;
    }

    public void setPRICENUM(String PRICENUM) {
        this.PRICENUM = PRICENUM;
    }

    public String getPRICEVAL() {
        return PRICEVAL;
    }

    public void setPRICEVAL(String PRICEVAL) {
        this.PRICEVAL = PRICEVAL;
    }
}
