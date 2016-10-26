package com.yutian.androidframework.control.ssq.ssqmodel;

import com.yutian.base.database.srcgen.SSQNUMBERWINNER;

/**
 * Created by wuwenchuan on 2016/10/26.
 */
public class PerPriceNumModel {
    int db_priceLevel;
    String priceLevel;
    String priceNumber;
    String priceValue;

    public PerPriceNumModel() {

    }

    public PerPriceNumModel(SSQNUMBERWINNER ssqnumberwinner)
    {
        this.db_priceLevel = ssqnumberwinner.getPRICELEVEL();
        this.priceNumber = ssqnumberwinner.getPRICENUM();
        this.priceValue = ssqnumberwinner.getPRICEVAL();

        //Need to change logic
//        SSQConfigControl temp = SSQConfigControl.getInstance();
//        this.priceLevel = temp.getPriceLevel().get(this.db_priceLevel).getName();
    }

    public boolean updateDBPriceLevel()
    {
        if (priceLevel == null)
            priceLevel = "";

        if (priceLevel.contains("一"))
            db_priceLevel = 1;
        else if (priceLevel.contains("二"))
            db_priceLevel = 2;
        else if (priceLevel.contains("三"))
            db_priceLevel = 3;
        else if (priceLevel.contains("四"))
            db_priceLevel = 4;
        else if (priceLevel.contains("五"))
            db_priceLevel = 5;
        else if (priceLevel.contains("六"))
            db_priceLevel = 6;
        else
            db_priceLevel = 7;

        return true;
    }

    public int getDb_priceLevel() {
        updateDBPriceLevel();
        return db_priceLevel;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getPriceNumber() {
        return priceNumber;
    }

    public void setPriceNumber(String priceNumber) {
        this.priceNumber = priceNumber;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

}
