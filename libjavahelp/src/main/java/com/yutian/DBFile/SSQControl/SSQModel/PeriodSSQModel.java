package com.yutian.DBFile.SSQControl.SSQModel;

import com.yutian.util.DateUtil;

import java.util.List;

/**
 * Created by yutian on 2016/9/4.
 */
public class PeriodSSQModel {

    //SSQ Period
    String period;

    //SSQ balls information
    SSQDataModel ssqDataModel = new SSQDataModel();

    //period time
    String kjTime;

    public String getDBKJTime() {
        String temp = kjTime.replaceAll("年|月", "-");
        temp = temp.replace("日", "");
        temp = DateUtil.formatSimpleUtilDateFormat(DateUtil.parseSimpleUtilDate(temp));
        ssqDataModel = new SSQDataModel();
        return temp;
    }

    //the end time to get price
    String djTime;

    public String getDBDJTime() {
        String temp = djTime.replaceAll("年|月", "-");
        temp = temp.replace("日", "");
        temp = DateUtil.formatSimpleUtilDateFormat(DateUtil.parseSimpleUtilDate(temp));
        return temp;
    }

    //sell value
    String bqSell;
    //pool value
    String pricePool;

    //price levels
    List<KJZQModel> kjxq;

    //web url
    String url;


    public void outputinformatin() {
        System.out.println(period + "\t\t");
        for (String redball : ssqDataModel.redBalls) {
            System.out.print(redball + "\t");
        }
        System.out.println(ssqDataModel.blueBall);
    }

    public String getRedBalls() {
        String redballstr = "";
        for (String redball :
                ssqDataModel.redBalls) {
            redballstr += redball + " ";
        }
        redballstr = redballstr.substring(0, redballstr.length() - 1);
        return redballstr;
    }

    public String getRedBallsSeq() {
        String redballstr = "";
        for (String redball :
                ssqDataModel.redBallSequence) {
            redballstr += redball + " ";
        }
        redballstr = redballstr.substring(0, redballstr.length() - 1);
        return redballstr;
    }

    public String getBqSell() {
        return bqSell;
    }

    public void setBqSell(String bqSell) {
        this.bqSell = bqSell;
    }

    public String getDjTime() {
        return djTime;
    }

    public void setDjTime(String djTime) {
        this.djTime = djTime;
    }

    public String getKjTime() {
        return kjTime;
    }

    public void setKjTime(String kjTime) {
        this.kjTime = kjTime;
    }

    public List<KJZQModel> getKjxq() {
        return kjxq;
    }

    public void setKjxq(List<KJZQModel> kjxq) {
        this.kjxq = kjxq;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPricePool() {
        return pricePool;
    }

    public void setPricePool(String pricePool) {
        this.pricePool = pricePool;
    }

    public SSQDataModel getSsqDataModel() {
        return ssqDataModel;
    }

    public void setSsqDataModel(SSQDataModel ssqDataModel) {
        this.ssqDataModel = ssqDataModel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
