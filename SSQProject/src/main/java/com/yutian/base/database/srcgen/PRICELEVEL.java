package com.yutian.base.database.srcgen;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PRICELEVEL".
 */
public class PRICELEVEL {

    private Integer LEVEL;
    private String NAME;
    private String PRESETPRICE;

    public PRICELEVEL() {
    }

    public PRICELEVEL(Integer LEVEL) {
        this.LEVEL = LEVEL;
    }

    public PRICELEVEL(Integer LEVEL, String NAME, String PRESETPRICE) {
        this.LEVEL = LEVEL;
        this.NAME = NAME;
        this.PRESETPRICE = PRESETPRICE;
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
