package com.yutian.services;

import com.yutian.DBFile.SSQControl.PeriodSSQDataControl;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.util.WebUtil;

/**
 * Created by wuwenchuan on 2016/9/13.
 */
public class PeriodSSQDataService {

    public void beginGrapDataFromWeb()
    {
        String[] allyears = {"03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16"};

        String baseurl = "http://kaijiang.500.com/shtml/ssq/%period%.shtml";

        PeriodSSQDataControl periodSSQDataControl = PeriodSSQDataControl.getInstance();

        int cnt = 1;
        String queryUrl = "";
        String newurl = "";
        for (String periodYear:
                allyears) {
            for (; cnt > 0; ++cnt)
            {
                queryUrl = periodYear + String.format("%03d",cnt);

                newurl = baseurl.replace("%period%", queryUrl);

                if (WebUtil.isWebExit(newurl))
                {
                    PeriodSSQModel tempModel = SSQAnalysisService.grapSSQWebContent(newurl);

                    if (periodSSQDataControl.insertSSQPeriodInfo(queryUrl, tempModel)) {
                        tempModel.outputinformatin();

                        System.out.println("DB Success!");
                    } else {
                        System.out.println(queryUrl + "failed!");
                    }
                    System.out.println("");

                } else {
                    cnt = 1;
                    break;
                }
            }
        }
    }

    public static void insertDataTest(String filepath)
    {
        PeriodSSQModel periodSSQModel = SSQAnalysisService.grapSSQWebContentFilePath(filepath);
        if (periodSSQModel != null)
        {
            System.out.println("PeriodSSQModel is not null");
            PeriodSSQDataControl temp = PeriodSSQDataControl.getInstance();
            temp.insertSSQPeriodInfo(periodSSQModel.getPeriod(), periodSSQModel);
        } else {
            System.out.println("PeriodSSQModel is null");
        }
    }

}
