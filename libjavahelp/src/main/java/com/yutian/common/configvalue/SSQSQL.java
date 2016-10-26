package com.yutian.common.configvalue;

import com.yutian.DBFile.SSQControl.SSQModel.KJZQModel;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;

/**
 * Created by wuwenchuan on 2016/9/7.
 */
public class SSQSQL {

    public static String replace(String origianl, String oldStr, String newStr) {
        return origianl.replace(oldStr, newStr);
    }

    //Generate SSQPERIODDATA insert SQL statement
    public static String generatePeriodSQLStatement(PeriodSSQModel ssqModel) {
        String sql = "";

        sql = " VALUES ('%1%', '%2%', '%3%', '%4%', '%5%', '%6%', '%7%', '%8%', '%9%', '%10%');";

        int cnt = 1;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getPeriod());

        ++cnt;
        for (String redball:
             ssqModel.getSsqDataModel().getRedBalls()) {
            sql = sql.replace("%"+ Integer.toString(cnt) + "%", redball);
            ++cnt;
        }

        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getSsqDataModel().getBlueBall());

        ++cnt;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getRedBalls());

        ++cnt;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getRedBallsSeq());

        sql = "INSERT INTO SSQPERIODDATA (PERIOD,RED1,RED2,RED3,RED4,RED5,RED6,BLUE,REDBALL,SEQREDBALL)"
                + sql;

        return sql;
    }

    //Generate SSQTIMEINFOR insert SQL statement
    public static String generatePeriodTimeInforSQLStatement(PeriodSSQModel ssqModel) {
        String sql = "";

        sql = " VALUES ('%1%', '%2%', '%3%', '%4%', '%5%');";

        int cnt = 1;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getPeriod());

        ++cnt;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getDBKJTime());
        ++cnt;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getDBDJTime());
        ++cnt;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getBqSell());
        ++cnt;
        sql = sql.replace("%"+ Integer.toString(cnt) + "%", ssqModel.getPricePool());

        sql = "INSERT INTO SSQTIMEINFOR(PERIOD, LOTTERYTIME, ENDTIME, PERIODSELL, CURRENPOOL)"
                + sql;

        return sql;
    }

    //Generate SSQNUMBERWINNER insert SQL statement
    public static String generatePeriodWinInforSQLStatement(String period, KJZQModel kjzqModel) {
        String sql = " VALUES('%1%', %2%, '%3%', '%4%');";

        int cnt = 1;
        sql = sql.replace("%" + Integer.toString(cnt) + "%", period);
        ++cnt;
        sql = sql.replace("%" + Integer.toString(cnt) + "%", Integer.toString(kjzqModel.getDb_priceLevel()));
        ++cnt;
        sql = sql.replace("%" + Integer.toString(cnt) + "%", kjzqModel.getPriceValue());
        ++cnt;
        sql = sql.replace("%" + Integer.toString(cnt) + "%", kjzqModel.getPriceNumber());

        sql = "INSERT INTO SSQNUMBERWINNER(PERIOD, PRICELEVEL, PRICEVAL, PRICENUM)" + sql;

        return sql;
    }

    //Generate SSQPERIODSTATUS insert SQL statement
    public static String generatePeriodStatusSQLStatement(String period, boolean insertSuccess)
    {
        String sql = " VALUES('%1%', '%2%')";

        int cnt=1;
        sql = sql.replace("%" + Integer.toString(cnt) + "%", period);
        ++cnt;
        sql = sql.replace("%" + Integer.toString(cnt) + "%", insertSuccess ? "Y" : "N");

        sql = "INSERT INTO SSQPERIODSTATUS(PERIOD, STATUS)" + sql;

        return sql;
    }

    //Generate SSQPERIODSTATUS update SQL statement
    public static String generateUpdatePeriodStatusSQLStatement(String period, boolean insertSuccess)
    {
        String sql = "UPDATE SSQPERIODSTATUS SET PERIOD='%1%', STATUS='%2%'" +
                " WHERE PERIOD='%1%'";

        int cnt = 1;
        sql.replaceAll("%1%", period);
        ++cnt;
        sql.replaceAll("%2%", insertSuccess ? "Y" : "N");

        return sql;
    }

    public static String generateSelectSimplePeriodDataFromPeriod(String period)
    {
        String sql = "SELECT * FROM SSQPERIODDATA WHERE PERIOD = '" + period + "'";

        return sql;
    }

}
