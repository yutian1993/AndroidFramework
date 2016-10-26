package com.yutian.androidframework.control.ssq;

import com.yutian.androidframework.control.ssq.ssqmodel.SelfDataModel;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfSSQDataModel;

import java.util.List;

/**
 * Created by wuwenchuan on 2016/10/26.
 */
public interface SelfDataManager {

    /**
     * 获得一期所有的购买信息，以及状态
     * @param period 期数
     * @return 详细数据
     */
    public SelfDataModel getBuyInformation(String period);

    public SelfDataModel getSelfSSQDataModels(String id);

    public SelfSSQDataModel getSelfSSQDataModel(String id, String number);

    public boolean insertSelfSSQDataModel(SelfDataModel selfDataModel);

    /**
     *
     * @param period 期数（可为空，系统会将其自动追加到最新）
     * @param id ID号（可为空，自动采用最新的ID号）
     * @param ssqDataModel 选择的数据
     * @return 是否成功添加
     */
    public boolean insertSelfSSQDataModel(String period,
                                      String id,
                                      SelfSSQDataModel ssqDataModel);
}
