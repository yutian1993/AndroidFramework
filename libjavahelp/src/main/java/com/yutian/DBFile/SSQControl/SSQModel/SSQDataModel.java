package com.yutian.DBFile.SSQControl.SSQModel;

import com.yutian.DBFile.SSQDBModel.MYSSQDATA;
import com.yutian.DBFile.SSQDBModel.SSQPERIODDATA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuwenchuan on 2016/9/12.
 */
public class SSQDataModel {
    //DB information
    String id;
    int number;

    //red balls from small to big
    List<String> redBalls = new ArrayList<>();
    //red balls sequence
    List<String> redBallSequence = new ArrayList<>();

    //blue balls
    List<String> blueBalls = new ArrayList<>();

    //blue ball
    String blueBall;

    String red1;
    String red2;
    String red3;
    String red4;
    String red5;
    String red6;

    //购买的注数
    int pour;

    int pay = 2;

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public SSQDataModel() {
    }

    public SSQDataModel(MYSSQDATA myssqdata) {
        id = myssqdata.getID();
        number = myssqdata.getNUMBER() == null ? 0 : myssqdata.getNUMBER();
        red1 = myssqdata.getRED1();
        red2 = myssqdata.getRED2();
        red3 = myssqdata.getRED3();
        red4 = myssqdata.getRED4();
        red5 = myssqdata.getRED5();
        red6 = myssqdata.getRED6();
        blueBall = myssqdata.getBLUE();
        pour = myssqdata.getCOUNT() == null ? 0 : myssqdata.getCOUNT();
        generateRedBallList();
    }

    public SSQDataModel(SSQPERIODDATA ssqperioddata) {
        red1 = ssqperioddata.getRED1();
        red2 = ssqperioddata.getRED2();
        red3 = ssqperioddata.getRED3();
        red4 = ssqperioddata.getRED4();
        red5 = ssqperioddata.getRED5();
        red6 = ssqperioddata.getRED6();
        blueBall = ssqperioddata.getBLUE();
        generateRedBallList();
        seqredballs = ssqperioddata.getSEQREDBALL();
    }

    protected void generateRedBallList() {
        redBalls.add(red1);
        redBalls.add(red2);
        redBalls.add(red3);
        redBalls.add(red4);
        redBalls.add(red5);
        redBalls.add(red6);
    }

    public void generateRedBalls() {
        if (redBalls.size() < 6)
            return;
        red1 = redBalls.get(0);
        red2 = redBalls.get(1);
        red3 = redBalls.get(2);
        red4 = redBalls.get(3);
        red5 = redBalls.get(4);
        red6 = redBalls.get(5);
    }

    public String getBlueBall() {
        return blueBall;
    }

    public void setBlueBall(String blueBall) {
        if (!blueBalls.contains(blueBall))
            blueBalls.add(blueBall);
        this.blueBall = blueBall;
    }

    public int getPour() {
        return pour;
    }

    public void setPour(int pour) {
        this.pour = pour;
    }

    public String getRed1() {
        return red1;
    }

    public String getRed2() {
        return red2;
    }

    public String getRed3() {
        return red3;
    }

    public String getRed4() {
        return red4;
    }

    public String getRed5() {
        return red5;
    }

    public String getRed6() {
        return red6;
    }

    public List<String> getRedBalls() {
        return redBalls;
    }

    public void setRedBalls(List<String> redBalls) {
        this.redBalls = redBalls;
        generateRedBalls();
    }

    public List<String> getRedBallSequence() {
        return redBallSequence;
    }

    public void setRedBallSequence(List<String> redBallSequence) {
        this.redBallSequence = redBallSequence;
    }

    public String getAllRedBalls() {
        String allredballs = "";
        for (String redball:
                redBalls) {
            allredballs += redball + " ";
        }
        allredballs.substring(0, allredballs.length()-1);
        return allredballs;
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

    //Add for database
    private int pricelevel;
    private String priceval;

    private String seqredballs;

    public String getSeqredballs() {
        return seqredballs;
    }

    public void setSeqredballs(String seqredballs) {
        this.seqredballs = seqredballs;
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

    public List<String> getBlueBalls() {
        return blueBalls;
    }

    public String getBlueBallsStr() {
        String content = "";
        for (String str:
             blueBalls) {
            content += str + " ";
        }
        return content;
    }

    public void setBlueBalls(List<String> blueBalls) {
        this.blueBalls = blueBalls;
    }

    public SSQDataModel CloneSelf() {
        SSQDataModel temp = new SSQDataModel();
        temp.pour = this.pour;
        temp.pay = this.pay;
        temp.needSave = this.needSave;
        temp.id = this.id;
        temp.number = this.number;
        temp.setRedBalls(getRedBalls());
        temp.setBlueBalls(getBlueBalls());
        return temp;
    }

    //Add for UI status(用来判断当前的Model是否需要保存到数据库中)
    private boolean needSave = false;

    public boolean isNeedSave() {
        return needSave;
    }

    public void setNeedSave(boolean needSave) {
        this.needSave = needSave;
    }

    //记录的添加时间
    private Date addDate;

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
