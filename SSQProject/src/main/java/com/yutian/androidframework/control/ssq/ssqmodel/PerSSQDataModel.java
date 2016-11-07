package com.yutian.androidframework.control.ssq.ssqmodel;

import com.yutian.DBFile.SSQControl.SSQModel.*;
import com.yutian.base.database.srcgen.MYSSQDATA;
import com.yutian.base.database.srcgen.SSQPERIODDATA;

import java.util.ArrayList;
import java.util.List;

/**
 * 官网信息
 * Created by wuwenchuan on 2016/10/25.
 */
public class PerSSQDataModel extends SSQDataModel {
    private String period;

    public PerSSQDataModel() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * Convert db SSQPERIODDATA to PeriodSSQDataModel
     * @param periodData
     * @return
     */
    public static PerSSQDataModel
    convertPeriodSSQDataToPeriodSSQDataModel(SSQPERIODDATA periodData) {
        PerSSQDataModel newObj = new PerSSQDataModel();
        newObj.setPeriod(periodData.getPERIOD());
        newObj.setRed1(periodData.getRED1());
        newObj.setRed2(periodData.getRED2());
        newObj.setRed3(periodData.getRED3());
        newObj.setRed4(periodData.getRED4());
        newObj.setRed5(periodData.getRED5());
        newObj.setRed6(periodData.getRED6());
        newObj.setBlue(periodData.getBLUE());
        newObj.setRedballs(periodData.getREDBALL());
        newObj.setRedseqballs(periodData.getSEQREDBALL());
        return newObj;
    }

    /**
     * Convert db SSQPERIODDATAs to PeriodSSQDataModels
     * @param periodDatas
     * @return
     */
    public static List<PerSSQDataModel>
    convertPeriodSSQDatasToPeriodSSQDataModels(List<SSQPERIODDATA> periodDatas) {
        List<PerSSQDataModel> results = new ArrayList<>();
        for (SSQPERIODDATA obj:
                periodDatas) {
            results.add(convertPeriodSSQDataToPeriodSSQDataModel(obj));
        }
        return results;
    }
}
