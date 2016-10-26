/**
 CREATE TABLE [PRICELEVEL] (
 [LEVEL] INT NOT NULL ON CONFLICT ABORT,
 [NAME] VARCHAR(16),
 [PRESETPRICE] VARCHAR(64),
 CONSTRAINT [sqlite_autoindex_PRICELEVEL_1] PRIMARY KEY ([LEVEL] ASC));
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;

/**
 * 我的中奖--奖金等级
 * Created by wuwenchuan on 2016/9/10.
 */
public class PRICELEVEL {
    @ID
    Integer LEVEL;              //奖金等级

    String NAME;            //奖金名称
    String PRESETPRICE;     //奖金数量

    public PRICELEVEL() {

    }

    public Integer getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(Integer LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPRESETPRICE() {
        return PRESETPRICE;
    }

    public void setPRESETPRICE(String PRESETPRICE) {
        this.PRESETPRICE = PRESETPRICE;
    }
}
