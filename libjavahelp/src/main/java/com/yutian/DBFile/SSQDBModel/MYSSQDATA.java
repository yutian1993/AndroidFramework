/**
 * CREATE TABLE [MYSSQDATA] (
 * [ID] CHAR(14) NOT NULL ON CONFLICT ABORT,
 * [NUMBER] INT NOT NULL ON CONFLICT ABORT,
 * [RED1] VARCHAR(2) NOT NULL DEFAULT FF,
 * [RED2] VARCHAR(2) NOT NULL DEFAULT FF,
 * [RED3] VARCHAR(2) NOT NULL DEFAULT FF,
 * [RED4] VARCHAR(2) NOT NULL DEFAULT FF,
 * [RED5] VARCHAR(2) NOT NULL DEFAULT FF,
 * [RED6] VARCHAR(2) NOT NULL DEFAULT FF,
 * [BLUE] VARCHAR(2) NOT NULL DEFAULT FF,
 * [REDBALL] TEXT(20));
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;
import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;

/**
 * 我的购买--双色球数据
 * Created by wuwenchuan on 2016/9/10.
 */
public class MYSSQDATA {
    @ID
    String ID;           //该注购买的ID
    @ID
    Integer NUMBER;          //该期第几注购买

    String RED1;
    String RED2;
    String RED3;
    String RED4;
    String RED5;
    String RED6;
    String BLUE;
    String REDBALL;      //红球的所有内容
    String BLUEBALL;     //蓝球的所有内容

    Integer COUNT;           //该注买的数目

    public MYSSQDATA() {

    }

    public MYSSQDATA(SSQDataModel ssqDataModel) {
        ID = ssqDataModel.getId();
        NUMBER = ssqDataModel.getNumber();

        RED1 = ssqDataModel.getRed1();
        RED2 = ssqDataModel.getRed2();
        RED3 = ssqDataModel.getRed3();
        RED4 = ssqDataModel.getRed4();
        RED5 = ssqDataModel.getRed5();
        RED6 = ssqDataModel.getRed6();
        REDBALL = ssqDataModel.getAllRedBalls();
        BLUE = ssqDataModel.getBlueBall();

        COUNT = ssqDataModel.getPour();

    }

    public String getBLUE() {
        return BLUE;
    }

    public void setBLUE(String BLUE) {
        this.BLUE = BLUE;
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

    public Integer getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(Integer COUNT) {
        this.COUNT = COUNT;
    }

    public String getBLUEBALL() {
        return BLUEBALL;
    }
    public void setBLUEBALL(String BLUEBALL) {
        this.BLUEBALL = BLUEBALL;
    }

}
