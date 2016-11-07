package com.yutian.androidframework.control.ssq.ssqmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 官方开奖的所有信息
 * Created by wuwenchuan on 2016/10/26.
 */
public class PerDataModel {
    private PerSSQDataModel perSSQDataModel;
    private PerInfoModel perInfoModel;
    private List<PerPriceNumModel> perPriceNumModels;

    public PerDataModel() {
        perPriceNumModels = new ArrayList<>();
    }

    public PerDataModel(PerInfoModel perInfoModel) {
        this.perInfoModel = perInfoModel;
    }

    public PerInfoModel getPerInfoModel() {
        return perInfoModel;
    }

    public void setPerInfoModel(PerInfoModel perInfoModel) {
        this.perInfoModel = perInfoModel;
    }

    public List<PerPriceNumModel> getPerPriceNumModels() {
        return perPriceNumModels;
    }

    public void setPerPriceNumModels(List<PerPriceNumModel> perPriceNumModels) {
        this.perPriceNumModels = perPriceNumModels;
    }

    public PerSSQDataModel getPerSSQDataModel() {
        return perSSQDataModel;
    }

    public void setPerSSQDataModel(PerSSQDataModel perSSQDataModel) {
        this.perSSQDataModel = perSSQDataModel;
    }

    public void addPriceNumberModel(PerPriceNumModel priceNumModel) {
        perPriceNumModels.add(priceNumModel);
    }
}
