/**
 CREATE TABLE [SSQPERIODDATA] (
 [PERIOD] VARCHAR(7) NOT NULL ON CONFLICT ABORT,
 [RED1] VARCHAR(2) NOT NULL DEFAULT FF,
 [RED2] VARCHAR(2) NOT NULL DEFAULT FF,
 [RED3] VARCHAR(2) NOT NULL DEFAULT FF,
 [RED4] VARCHAR(2) NOT NULL DEFAULT FF,
 [RED5] VARCHAR(2) NOT NULL DEFAULT FF,
 [RED6] VARCHAR(2) NOT NULL DEFAULT FF,
 [BLUE] VARCHAR(2) NOT NULL DEFAULT FF,
 [REDBALL] TEXT(20),
 [SEQREDBALL] CHAR(20),
 CONSTRAINT [sqlite_autoindex_SSQPERIODDATA_1] PRIMARY KEY ([PERIOD] ASC) ON CONFLICT ABORT);
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;

/**
 * 每期双气球红球和蓝球
 * Created by wuwenchuan on 2016/9/10.
 */
public class SSQPERIODDATA {
    @ID
    String PERIOD;

    String RED1;
    String RED2;
    String RED3;
    String RED4;
    String RED5;
    String RED6;
    String BLUE;
    String REDBALL;
    String SEQREDBALL;

    public SSQPERIODDATA() {

    }

    public SSQPERIODDATA(PeriodSSQModel ssqModel)
    {
        PERIOD = ssqModel.getPeriod();

        RED1 = ssqModel.getSsqDataModel().getRed1();
        RED2 = ssqModel.getSsqDataModel().getRed2();
        RED3 = ssqModel.getSsqDataModel().getRed3();
        RED4 = ssqModel.getSsqDataModel().getRed4();
        RED5 = ssqModel.getSsqDataModel().getRed5();
        RED6 = ssqModel.getSsqDataModel().getRed6();

        BLUE = ssqModel.getSsqDataModel().getBlueBall();
        REDBALL = ssqModel.getRedBalls();
        SEQREDBALL = ssqModel.getRedBallsSeq();
    }

    public String getBLUE() {
        return BLUE;
    }

    public void setBLUE(String BLUE) {
        this.BLUE = BLUE;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getRED1() {
        return RED1;
    }

    public void setRED1(String RED1) {
        this.RED1 = RED1;
    }

    public String getRED2() {
        return RED2;
    }

    public void setRED2(String RED2) {
        this.RED2 = RED2;
    }

    public String getRED3() {
        return RED3;
    }

    public void setRED3(String RED3) {
        this.RED3 = RED3;
    }

    public String getRED4() {
        return RED4;
    }

    public void setRED4(String RED4) {
        this.RED4 = RED4;
    }

    public String getRED5() {
        return RED5;
    }

    public void setRED5(String RED5) {
        this.RED5 = RED5;
    }

    public String getRED6() {
        return RED6;
    }

    public void setRED6(String RED6) {
        this.RED6 = RED6;
    }

    public String getREDBALL() {
        return REDBALL;
    }

    public void setREDBALL(String REDBALL) {
        this.REDBALL = REDBALL;
    }

    public String getSEQREDBALL() {
        return SEQREDBALL;
    }

    public void setSEQREDBALL(String SEQREDBALL) {
        this.SEQREDBALL = SEQREDBALL;
    }
}
