package com.yutian.androidframework.control.ssq;

import com.yutian.androidframework.control.ssq.ssqmodel.MainItemModel;

import java.util.List;

/**
 * Created by wuwenchuan on 2016/11/3.
 */
public interface PeriodDataManager {

    /**
     * 获取主列表中最新的前十个信息
     * @param period 期数
     *         如果期数为空，则选择最新的前十个信息
     * @return
     */
    public List<MainItemModel> getTopTenInfor(String period);

}
