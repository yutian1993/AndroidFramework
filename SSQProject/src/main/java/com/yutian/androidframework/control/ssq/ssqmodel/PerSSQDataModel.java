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
    private String seqredball;

    public PerSSQDataModel() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSeqredball() {
        return seqredball;
    }

    public void setSeqredball(String seqredball) {
        this.seqredball = seqredball;
    }

    /**
     * Convert db SSQPERIODDATA to PeriodSSQDataModel
     * @param periodData
     * @return
     */
    public static PerSSQDataModel
    convertPeriodSSQDataToPeriodSSQDataModel(SSQPERIODDATA periodData) {
        PerSSQDataModel newObj = new PerSSQDataModel();
        newObj.period = periodData.getPERIOD();
        newObj.red1 = periodData.getRED1();
        newObj.red2 = periodData.getRED2();
        newObj.red3 = periodData.getRED3();
        newObj.red4 = periodData.getRED4();
        newObj.red5 = periodData.getRED5();
        newObj.red6 = periodData.getRED6();
        newObj.blue = periodData.getBLUE();
        newObj.redballs = periodData.getREDBALL();
        newObj.seqredball = periodData.getSEQREDBALL();
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
