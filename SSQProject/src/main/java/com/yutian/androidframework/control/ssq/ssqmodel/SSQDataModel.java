package com.yutian.androidframework.control.ssq.ssqmodel;

import com.yutian.androidframework.control.ssq.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2016/10/25.
 */
public class SSQDataModel {
    protected String red1;
    protected String red2;
    protected String red3;
    protected String red4;
    protected String red5;
    protected String red6;
    protected String blue;

    protected String redballs;
    protected String blueballs;
    protected String redseqballs;
    protected List<String> redBallList = new ArrayList<>();
    protected List<String> redBallSeqList = new ArrayList<>();
    protected List<String> blueBallList = new ArrayList<>();

    public SSQDataModel() {
        init();
    }

    public SSQDataModel(SSQDataModel clone) {
        init();
        if (clone == null)
            return;
        this.setRed1(clone.getRed1());
        this.setRed2(clone.getRed2());
        this.setRed3(clone.getRed3());
        this.setRed4(clone.getRed4());
        this.setRed5(clone.getRed5());
        this.setRed6(clone.getRed6());
        this.setBlue(clone.getBlue());
        this.setRedballs(clone.getRedballs());
        this.setRedseqballs(clone.getRedseqballs());
        this.setBlueballs(clone.getBlueballs());
    }

    private void init() {
        for (int index = 0; index < 6; ++index) {
            redBallList.add("0");
            redBallSeqList.add("0");
        }
        blueBallList.add("0");
        redballs = "";
        blueballs = "";
        redseqballs = "";
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        blueballs = blue;
        blueBallList.set(0, blue);
        this.blue = blue;
    }

    public String getBlueballs() {
        blueballs = "";
        for (String ball :
                blueBallList) {
            blueballs += ball + " ";
        }
        return blueballs;
    }

    public void setBlueballs(String blueballs) {
        this.blueballs = blueballs;

        String[] balls = blueballs.split(" ");
        blueBallList.clear();
        for (String ball :
                balls) {
            blueBallList.add(ball);
        }
    }

    public String getRed1() {
        return red1;
    }

    public void setRed1(String red1) {
        this.red1 = red1;
        redBallList.set(0, red1);
    }

    public String getRed2() {
        return red2;
    }

    public void setRed2(String red2) {
        this.red2 = red2;
        redBallList.set(1, red2);
    }

    public String getRed3() {
        return red3;
    }

    public void setRed3(String red3) {
        this.red3 = red3;
        redBallList.set(2, red3);
    }

    public String getRed4() {
        return red4;
    }

    public void setRed4(String red4) {
        this.red4 = red4;
        redBallList.set(3, red4);
    }

    public String getRed5() {
        return red5;
    }

    public void setRed5(String red5) {
        this.red5 = red5;
        redBallList.set(4, red5);
    }

    public String getRed6() {
        return red6;
    }

    public void setRed6(String red6) {
        this.red6 = red6;
        redBallList.set(5, red6);
    }

    public String getRedballs() {
        redballs = "";
        for (String ball :
                redBallList) {
            redballs += ball + " ";
        }
        return redballs;
    }

    public void setRedballs(String redballs) {
        this.redballs = redballs;

        String[] balls = redballs.split(" ");
        redBallList.clear();
        for (String ball :
                balls) {
            redBallList.add(ball);
        }
    }

    public String getRedseqballs() {
        redseqballs = "";
        for (String ball :
                redBallSeqList) {
            redseqballs += ball + " ";
        }
        return redseqballs;
    }

    public void setRedseqballs(String redseqballs) {
        this.redseqballs = redseqballs;

        String[] balls = redseqballs.split(" ");
        redBallSeqList.clear();
        for (String ball :
                balls) {
            redBallSeqList.add(ball);
        }
    }

    public List<String> getBlueBallList() {
        return blueBallList;
    }

    public void setBlueBallList(List<String> blueBallList) {
        blueballs = "";
        for (String ball : blueBallList) {
            blueballs += ball + " ";
        }
        if (blueBallList.size() > 0)
            blue = blueBallList.get(0);
        else
            blue = "";
        this.blueBallList = blueBallList;
    }

    public List<String> getRedBallList() {
        return redBallList;
    }

    public void setRedBallList(List<String> redBallList) {
        this.redBallList = redBallList;
        generateRedBalls();
    }

    public List<String> getRedBallSeqList() {
        return redBallSeqList;
    }

    public void setRedBallSeqList(List<String> redBallSeqList) {
        this.redBallSeqList = redBallSeqList;
    }

    protected void generateRedBalls() {
        if (redBallList.size() < 6)
            return;
        red1 = redBallList.get(0);
        red2 = redBallList.get(1);
        red3 = redBallList.get(2);
        red4 = redBallList.get(3);
        red5 = redBallList.get(4);
        red6 = redBallList.get(5);

        redballs = "";
        for (String ball: redBallList) {
            redballs += ball + " ";
        }
    }
}
