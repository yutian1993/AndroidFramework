package com.yutian.services;

import com.yutian.DBFile.SSQControl.SSQModel.KJZQModel;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.util.FileUtil;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuwenchuan on 2016/9/13.
 */
public class SSQAnalysisService {

    private static Pattern TIMEPATTEN = Pattern.compile("[0-9]{4}[年|\\-|/][0-9]{1,2}[月|\\-|/][0-9]{1,2}[日]");
//    private static Pattern PRICEPATTEN = Pattern.compile("\\d+[元|\\-|/]");

    /**
     * Use to grap ssq web content information
     * @param htmlDoc html document
     * @return
     */
    public static PeriodSSQModel ssqcontentAnalysis(Document htmlDoc) {
        PeriodSSQModel currentInfo = null;
        try {
            Document document = htmlDoc;

            Elements els = document.getElementsByClass("kj_tablelist02");

            if (els != null && els.size() > 0) {
                currentInfo = new PeriodSSQModel();
            } else {
                return currentInfo;
            }

            //set url
            currentInfo.setUrl(htmlDoc.baseUri());

            for (Element element : els) {
                Elements trElements = null;
                Element tdElement = null;

                //Period
                String period = null;
                trElements = element.select("tr span.span_left strong");
                if (trElements.size() == 1) {
                    period = trElements.get(0).text();
                    currentInfo.setPeriod(period);

                }

                //Time
                String timeStart = null;
                String timeEnd = null;
                trElements = element.select("tr span.span_right");
                for (Element element2 : trElements) {
                    Matcher matcher = TIMEPATTEN.matcher(element2.html());
                    while (matcher.find()) {
                        if (timeStart == null)
                            timeStart = matcher.group();
                        else
                            timeEnd = matcher.group();
                    }
                    currentInfo.setKjTime(timeStart);
                    currentInfo.setDjTime(timeEnd);
                }

                //Red balls
                List<String> redballStrings = new ArrayList();
                trElements = element.select("div.ball_box01 ul li.ball_red");
                if (trElements.size() == 6) {
                    for (int cnt = 0; cnt < 6; cnt++) {
                        redballStrings.add(trElements.get(cnt).text());
                    }
                    currentInfo.getSsqDataModel().setRedBalls(redballStrings);
                } else {
                    redballStrings = null;
                }
                trElements = element.select("div.ball_box01 ul li.ball_blue");
                String blueballString = "";
                if (trElements.size() == 1) {
                    blueballString = trElements.get(0).text();
                    currentInfo.getSsqDataModel().setBlueBall(blueballString);
                } else {
                    blueballString = null;
                }

                //Sequence
                List<String> redBallQuenceList = new ArrayList<String>();
                try {
                    tdElement = element.select("table table tr").last().select("td").last();
                    String[] requStrings = tdElement.html().split(" ");
                    if (requStrings.length == 6) {
                        for (String string : requStrings) {
                            redBallQuenceList.add(string);
                        }
                    } else {
                        redBallQuenceList = null;
                    }
                } catch (NullPointerException e) {
                    System.out.println("Get Sequence error.");
                    redBallQuenceList = null;
                    tdElement = null;
                } finally {
                    if (redBallQuenceList!= null && redBallQuenceList.size() != 0)
                        currentInfo.getSsqDataModel().setRedBallSequence(redBallQuenceList);
                }

                //Pool price
                String currentBuyStr = null;
                String currentPoolStr = null;
                try {
                    trElements = element.select("table tr td span.cfont1");
                    if (trElements.size() == 2) {
                        currentBuyStr = trElements.get(0).html();
                        currentPoolStr = trElements.get(1).html();
                    }
                } catch (NullPointerException e) {
                    System.out.println("Grap price Error");
                } finally {
                    if (currentBuyStr != null) {
                        currentInfo.setBqSell(currentBuyStr);
                        currentInfo.setPricePool(currentPoolStr);
                    }
                }

                //Grap prices
                if (redBallQuenceList == null) {
                    List<KJZQModel> kjzqModels = new ArrayList<>();
                    trElements = element.getElementsByAttributeValue("align", "center");
                    for (Element tdElement2 : trElements) {
                        if (tdElement2.getElementsByClass("td_title02").size() == 0) {
                            Elements everPrice = tdElement2.getElementsByTag("td");
                            if (everPrice.size() == 3) {
                                KJZQModel model = new KJZQModel();
                                model.setPriceLevel(everPrice.get(0).text());
                                model.setPriceNumber(everPrice.get(1).text());
                                model.setPriceValue(everPrice.get(2).text());
                                kjzqModels.add(model);
                            }
                        }
                    }
                    currentInfo.setKjxq(kjzqModels);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentInfo;
    }

    /**
     * SSQ query url
     * @param url ssq web content url
     * @return
     */
    public static PeriodSSQModel grapSSQWebContent(String url) {
        PeriodSSQModel currentInfo = null;
        try {
            Document document = null;

            try {
                document = Jsoup.connect(url).get();
            } catch (HttpStatusException exception) {
                System.out.println("Get " + url + " Failed!");
                return currentInfo;
            }

            if (document == null)
                return currentInfo;

            currentInfo = ssqcontentAnalysis(document);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentInfo;
    }

    /**
     * Grap ssq web content from disk file
     * @param webcontent File object
     * @param encodeing file encoding
     * @return this file content ssq information
     */
    public static PeriodSSQModel grapSSQWebContent(File webcontent, String encodeing) {

        PeriodSSQModel currentInfo = null;

        try {
            Document document = Jsoup.parse(webcontent, encodeing, webcontent.getPath());

            currentInfo = ssqcontentAnalysis(document);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentInfo;
    }

    /**
     * Grap ssq web content from disk file path
     * @param filepath file path
     * @return this file content ssq information
     */
    public static PeriodSSQModel grapSSQWebContentFilePath(String filepath) {

        PeriodSSQModel currentInfo = null;

        File fileobj = new File(filepath);
        if (fileobj != null && fileobj.exists()) {
            String encoding = FileUtil.getFileEncodeing(filepath);
            currentInfo = grapSSQWebContent(fileobj, encoding);
        }

        return currentInfo;
    }

}
