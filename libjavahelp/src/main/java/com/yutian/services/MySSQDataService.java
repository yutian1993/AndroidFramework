package com.yutian.services;

import com.yutian.DBFile.SSQControl.MySSQDataControl;
import com.yutian.DBFile.SSQControl.PeriodSSQDataControl;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;
import com.yutian.util.SSQLibUtil;

/**
 * Created by wuwenchuan on 2016/9/13.
 */
public class MySSQDataService {
    private MySSQDataControl m_myssqdatacontrol = null;
    private PeriodSSQDataControl m_periodssqdatacontrol = null;

    private static MySSQDataService g_myssqdataservice = null;

    //No right to access
    private MySSQDataService() {
        m_myssqdatacontrol = MySSQDataControl.getInstance();
        m_periodssqdatacontrol = PeriodSSQDataControl.getInstance();
    }

    public static MySSQDataService getInstance() {
        if (g_myssqdataservice == null) {
            synchronized (MySSQDataService.class) {
                if (g_myssqdataservice == null)
                    g_myssqdataservice = new MySSQDataService();
            }
        }

        return g_myssqdataservice;
    }

    /**
     * 判断指定的id和number去匹配指定的period
     * @param id 购买的ID
     * @param number  购买的序号
     * @param period 对应的期数
     * @return 中奖的级别
     */
    public int judgePriceLevel(String id, int number, String period) {
        SSQDataModel model = m_myssqdatacontrol.selectMySSQData(id, number);
        PeriodSSQModel perioddata =  m_periodssqdatacontrol.selectPeriodSSQDataWithPeriod(period);
        return SSQLibUtil.judgePrice(model, perioddata);
    }




}
