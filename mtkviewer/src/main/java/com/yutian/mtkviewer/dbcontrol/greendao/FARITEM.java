package com.yutian.mtkviewer.dbcontrol.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "FARITEM".
 */
public class FARITEM {

    private String FAQID;
    private String TITLE;
    private String WHOLECONTENT;
    private String MAINCONTENT;
    private String MAINTEXT;
    private byte[] WHOLECONTENTIMG;

    public FARITEM() {
    }

    public FARITEM(String FAQID, String TITLE, String WHOLECONTENT, String MAINCONTENT, String MAINTEXT, byte[] WHOLECONTENTIMG) {
        this.FAQID = FAQID;
        this.TITLE = TITLE;
        this.WHOLECONTENT = WHOLECONTENT;
        this.MAINCONTENT = MAINCONTENT;
        this.MAINTEXT = MAINTEXT;
        this.WHOLECONTENTIMG = WHOLECONTENTIMG;
    }

    public String getFAQID() {
        return FAQID;
    }

    public void setFAQID(String FAQID) {
        this.FAQID = FAQID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getWHOLECONTENT() {
        return WHOLECONTENT;
    }

    public void setWHOLECONTENT(String WHOLECONTENT) {
        this.WHOLECONTENT = WHOLECONTENT;
    }

    public String getMAINCONTENT() {
        return MAINCONTENT;
    }

    public void setMAINCONTENT(String MAINCONTENT) {
        this.MAINCONTENT = MAINCONTENT;
    }

    public String getMAINTEXT() {
        return MAINTEXT;
    }

    public void setMAINTEXT(String MAINTEXT) {
        this.MAINTEXT = MAINTEXT;
    }

    public byte[] getWHOLECONTENTIMG() {
        return WHOLECONTENTIMG;
    }

    public void setWHOLECONTENTIMG(byte[] WHOLECONTENTIMG) {
        this.WHOLECONTENTIMG = WHOLECONTENTIMG;
    }

}