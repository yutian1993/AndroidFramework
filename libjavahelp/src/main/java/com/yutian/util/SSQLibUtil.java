package com.yutian.util;

import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;

import org.jsoup.helper.DataUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wuwenchuan on 2016/9/8.
 */
public class SSQLibUtil {

    private static int REDBALLS = 33;
    private static int BLUEBALLS = 16;
    private static int NEEDDIVIDE = 720;
    private static int EVERY_WEEK_PERIODS = 3;
    private static int WEEK_DAYS = 7;

    public int judgePrice(PeriodSSQModel ssqModel, String period)
    {
        return 7;
    }

    /**
     * Judge ssq
     * @param ssqDataModel  self ssq information
     * @param periodSsqMode period ssq information
     * @return price level
     */
    public static int judgePrice(SSQDataModel ssqDataModel, PeriodSSQModel periodSsqMode)
    {
        String redballs = periodSsqMode.getRedBalls();
        int cntredball = 0;
        int cntblueball = 0;

        for (String redball:
                ssqDataModel.getRedBalls()) {
            if (redballs.contains(redball))
                ++cntredball;
        }

        if (periodSsqMode.getSsqDataModel().getBlueBall().contains(ssqDataModel.getBlueBall()))
            ++cntblueball;

        return analysisPriceLevel(cntredball, cntblueball);
    }

    /**
     *
     * @param cntred get red ball count
     * @param cntblue get blue ball count
     * @return
     */
    protected static int analysisPriceLevel(int cntred, int cntblue)
    {
        int pricelevel = 7;

        if (cntblue == 1) {
            if (cntred == 6)
                pricelevel = 1;
            else if (cntred == 5)
                pricelevel = 3;
            else if (cntred == 4)
                pricelevel = 4;
            else if (cntred == 3)
                pricelevel = 5;
            else
                pricelevel = 6;
        } else {
            if (cntred == 6)
                pricelevel = 2;
            else if (cntred == 5)
                pricelevel = 4;
            else if (cntred == 4)
                pricelevel = 5;
            else
                pricelevel = 7;
        }

        return pricelevel;
    }

    /**
     * Depend on the ball count to cacluate how much need to pay
     * @param cntred red ball number
     * @param cntblue blue ball number
     * @return how much money need to pay
     */
    public static int needToPay(int cntred, int cntblue)
    {
        if (cntred < 6 || cntblue < 1)
            return 0;

        if (cntred > REDBALLS || cntblue > BLUEBALLS)
            return 0;

        int needpay = 2;
        int pour = 1;

        try {
            for (int cnt = 0; cnt < 6; ++cnt)
                pour *= (cntred-cnt);
            pour /= NEEDDIVIDE;
            pour *= cntblue;
            needpay = pour * needpay;
        } finally {
            if (needpay < 0)
                needpay = -1;
        }
        return needpay;
    }

    /**
     * This function can get current period, this period may
     * different with actual period show, because actually may
     * stop or happening something else
     * @return Period string as rule
     */
    public static String currentPeriod() {
        Date currentTime = new Date();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(currentTime);
        int currentdayofweek = mCalendar.get(Calendar.DAY_OF_WEEK);
        int currentWeek = mCalendar.get(Calendar.WEEK_OF_YEAR);
        String temp = mCalendar.get(Calendar.YEAR) + "-01-01";
        currentTime = DateUtil.parseSimpleUtilDate(temp);
        if (currentTime == null) {
            return null;
        }
        mCalendar.setTime(currentTime);

        //Get period number in first week
        int period = 0;
        int firstdayofweek = mCalendar.get(Calendar.DAY_OF_WEEK);
        if (firstdayofweek > 1) {
            period = getWeekPeriod(firstdayofweek) - getWeekPeriod(firstdayofweek-1);
        }

        //Get current period number in current week
        if (currentWeek == 1) {
            period += getWeekPeriod(currentdayofweek) - getWeekPeriod(firstdayofweek);
            currentWeek = 0;
        } else {
            period += getWeekPeriod(currentdayofweek);
            currentWeek -= 2;
        }

        //Calculator all periods
        period += currentWeek*EVERY_WEEK_PERIODS;
        if (period == 0 || currentdayofweek % 2 == 0 || currentdayofweek % 7 == 0)
            period += 1;
//        System.out.println(mCalendar.get(Calendar.YEAR) + String.format("%03d", period));

        return mCalendar.get(Calendar.YEAR)%100 + String.format("%03d", period);

    }

    protected static int getWeekPeriod(int dayofweek)
    {
        dayofweek = dayofweek % WEEK_DAYS;
        if (dayofweek == 0)
            return EVERY_WEEK_PERIODS;
        return dayofweek / 2 + dayofweek % 2;
    }
}
