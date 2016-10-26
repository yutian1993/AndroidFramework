package com.yutian.DBFile.SSQControl.SSQModel;

import com.yutian.DBFile.SSQDBModel.PRICELEVEL;

/**
 * Created by wuwenchuan on 2016/9/13.
 */
public class SSQPriceLevelModel {
    Integer level;

    String name;
    String presetprice;

    public SSQPriceLevelModel() {}

    public SSQPriceLevelModel(PRICELEVEL pricelevel) {
        level = pricelevel.getLEVEL();
        name = pricelevel.getNAME();
        presetprice = pricelevel.getPRESETPRICE();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresetprice() {
        return presetprice;
    }

    public void setPresetprice(String presetprice) {
        this.presetprice = presetprice;
    }
}
