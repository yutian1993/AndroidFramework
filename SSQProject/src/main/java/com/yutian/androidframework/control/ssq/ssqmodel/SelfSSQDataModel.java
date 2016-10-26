package com.yutian.androidframework.control.ssq.ssqmodel;

import com.yutian.base.database.srcgen.MYSSQDATA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人单注信息
 * Created by wuwenchuan on 2016/10/25.
 */
public class SelfSSQDataModel extends SSQDataModel {
    private String id;
    private int number;
    private int count;
    private int pricelevel;
    private String priceval;
    private Date addtime;

    public SelfSSQDataModel() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPricelevel() {
        return pricelevel;
    }

    public void setPricelevel(int pricelevel) {
        this.pricelevel = pricelevel;
    }

    public String getPriceval() {
        return priceval;
    }

    public void setPriceval(String priceval) {
        this.priceval = priceval;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * Convert MYSSQDATA to SelfSSQDataModel
     *
     * @param ssqdata Database DO
     * @return Model
     */
    public static SelfSSQDataModel
    convertMySSQDataToSelfSSQDataModel(MYSSQDATA ssqdata) {
        SelfSSQDataModel newObj = new SelfSSQDataModel();
        newObj.id = ssqdata.getID();
        newObj.number = ssqdata.getNUMBER();
        newObj.red1 = ssqdata.getRED1();
        newObj.red2 = ssqdata.getRED2();
        newObj.red3 = ssqdata.getRED3();
        newObj.red4 = ssqdata.getRED4();
        newObj.red5 = ssqdata.getRED5();
        newObj.red6 = ssqdata.getRED6();
        newObj.blue = ssqdata.getBLUE();
        newObj.redballs = ssqdata.getREDBALL();
        newObj.blueballs = ssqdata.getBLUEBALL();
        newObj.count = ssqdata.getCOUNT();
        return newObj;
    }

    /**
     * Convert MYSSQDATAs to SelfSSQDataModels
     *
     * @param ssqdatas
     * @return
     */
    public static List<SelfSSQDataModel>
    convertMySSQDatasToSelfSSQDataModels(List<MYSSQDATA> ssqdatas) {
        List<SelfSSQDataModel> results = new ArrayList<>();
        for (MYSSQDATA ssqdata : ssqdatas
                ) {
            results.add(convertMySSQDataToSelfSSQDataModel(ssqdata));
        }
        return results;
    }

    /**
     * Convert SelfSSQDataModel to MYSSQDATA
     *
     * @param selfSSQDataModel Model
     * @return Database DO
     */
    public static MYSSQDATA
    convertSelfSSQDataModelToMySSQData(SelfSSQDataModel selfSSQDataModel) {
        MYSSQDATA newObj = new MYSSQDATA();
        newObj.setID(selfSSQDataModel.id);
        newObj.setRED1(selfSSQDataModel.red1);
        newObj.setRED2(selfSSQDataModel.red2);
        newObj.setRED3(selfSSQDataModel.red3);
        newObj.setRED4(selfSSQDataModel.red4);
        newObj.setRED5(selfSSQDataModel.red5);
        newObj.setRED6(selfSSQDataModel.red6);
        newObj.setBLUE(selfSSQDataModel.blue);
        newObj.setREDBALL(selfSSQDataModel.redballs);
        newObj.setBLUEBALL(selfSSQDataModel.blueballs);
        newObj.setCOUNT(selfSSQDataModel.count);
        return newObj;
    }

    /**
     * Convert SelfSSQDataModels to MYSSQDATAs
     *
     * @param selfSSQDataModels
     * @return
     */
    public static List<MYSSQDATA>
    convertSelfSSQDataModelsToMySSQDatas(List<SelfSSQDataModel> selfSSQDataModels) {
        List<MYSSQDATA> results = new ArrayList<>();
        for (SelfSSQDataModel ssqdata : selfSSQDataModels
                ) {
            results.add(convertSelfSSQDataModelToMySSQData(ssqdata));
        }
        return results;
    }
}
