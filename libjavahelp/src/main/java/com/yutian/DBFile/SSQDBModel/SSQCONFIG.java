/**
 CREATE TABLE [SSQCONFIG] (
 [ID] INT,
 [NAME] VARCHAR(64),
 [VALUE] VARCHAR(2048));
 */

package com.yutian.DBFile.SSQDBModel;

import com.yutian.DBFile.DBAnnotation.ID;

/**
 * 双色球属性配置
 * Created by wuwenchuan on 2016/9/10.
 */
public class SSQCONFIG {
    @ID
    Integer ID;                //ID

    String NAME;           //属性名称
    String VALUE;          //属性值

    public SSQCONFIG() {

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }
}
