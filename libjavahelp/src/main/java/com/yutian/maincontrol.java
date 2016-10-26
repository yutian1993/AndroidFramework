package com.yutian;

//import com.google.gson.Gson;
import com.yutian.DBFile.SSQControl.MySSQDataControl;
import com.yutian.DBFile.SSQControl.PeriodSSQDataControl;
import com.yutian.DBFile.SSQControl.SSQConfigControl;
import com.yutian.DBFile.SSQControl.SSQDBControl;
import com.yutian.DBFile.SSQControl.SSQModel.KJZQModel;
import com.yutian.DBFile.SSQControl.SSQModel.MySSQModel;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;
import com.yutian.services.MySSQDataService;
import com.yutian.services.PeriodSSQDataService;

import java.util.ArrayList;
import java.util.List;

public class maincontrol {

    public static void addmyssqData() {
        MySSQModel tempdata = new MySSQModel();

        for (int cnt = 1; cnt < 6; ++cnt) {
            SSQDataModel ssqDataModel = new SSQDataModel();

            List<String> temp = new ArrayList<>();
            temp.add("11");
            temp.add("14");
            temp.add("15");
            temp.add("16");
            temp.add("17");
            temp.add("18");
            ssqDataModel.setRedBalls(temp);
            ssqDataModel.setBlueBall("0" + cnt);

            ssqDataModel.setPour(1);

            tempdata.addSsqDataModel(ssqDataModel);
        }

        MySSQDataControl temp = MySSQDataControl.getInstance();
        temp.insertMySSQData(tempdata);
    }

    public static void deletemyssqData() {
        MySSQModel temp = new MySSQModel();

        temp.setId("20160913033730");
//
//        SSQDataModel ssqDataModel = new SSQDataModel();
//        ssqDataModel.setId("20160912045142");
//        ssqDataModel.setNumber(3);

        MySSQDataControl temp1 = MySSQDataControl.getInstance();
//        temp1.deleteMySSQData(ssqDataModel);
        temp1.deleteMySSQData(temp);
    }

    public static void selectmyssqData() {
        MySSQDataControl temp1 = MySSQDataControl.getInstance();

        MySSQModel temp = temp1.selectMySSQData("20160913033547");
        List<SSQDataModel> results = temp.getSsqDataModels();

        System.out.print(temp.getId() + " ");
        System.out.print(temp.getPeriod() + " ");
        System.out.println(temp.getTime() + " ");

        for (SSQDataModel model:
                results) {
            System.out.print(model.getRed1() + " ");
            System.out.print(model.getRed2() + " ");
            System.out.print(model.getRed3() + " ");
            System.out.print(model.getRed4() + " ");
            System.out.print(model.getRed5() + " ");
            System.out.print(model.getRed6() + " ");
            System.out.println(model.getBlueBall());
        }
    }

    public static void updatemyssqData() {
        SSQDataModel ssqDataModel = new SSQDataModel();

        ssqDataModel.setId("20160913033547");
        ssqDataModel.setNumber(4);

        List<String> temp = new ArrayList<>();
        temp.add("11");
        temp.add("33");
        temp.add("15");
        temp.add("20");
        temp.add("17");
        temp.add("18");
        ssqDataModel.setRedBalls(temp);
        ssqDataModel.setBlueBall("0" + 8);

        ssqDataModel.setPour(3);

        MySSQDataControl temp1 = MySSQDataControl.getInstance();
        temp1.updateMySSQData(ssqDataModel);
    }

    public static void selectssqData() {
        PeriodSSQDataControl periodSSQDataControl = PeriodSSQDataControl.getInstance();

        PeriodSSQModel temp = periodSSQDataControl.selectDetailPeriodSSQDataWithPeriod("06135");

        if (temp != null) {
            for (KJZQModel model :
                    temp.getKjxq()) {
                System.out.print(model.getPriceLevel() + " ");
                System.out.print(model.getPriceNumber() + " ");
                System.out.println(model.getPriceValue());
            }

            temp.outputinformatin();
        }

    }

    public static void addssqData() {
        PeriodSSQDataService.insertDataTest("F:\\Task\\Aug\\0831\\cp.txt");
    }

    public static void judgeprice() {
        SSQConfigControl temp = SSQConfigControl.getInstance();

        MySSQDataService temp1 = MySSQDataService.getInstance();
        System.out.println(temp.getPriceLevel().get(temp1.judgePriceLevel("20160913033631", 4, "06135")).getName());
    }

    public static void main(String[] args) {

        SSQDBControl temp = SSQDBControl.getInstance();
        temp.initDBInformation("F:/AndroidStudioCode/AndroidFramework/libjavahelp/src/main/java/com/yutian/DBFile/ssq.db");

        judgeprice();

//        addmyssqData();
//        deletemyssqData();
//        selectmyssqData();
//        updatemyssqData();
//
//        addssqData();
//        selectssqData();

        temp.disconnectDBInformation();
        //String filepath = "F:\\Task\\Aug\\0831\\cp.txt";

        //SSQDBControl.initDBInformation("F:/AndroidStudio/AndroidFramework/libjavahelp/src/main/java/com/yutian/DBFile/ssq.db");

        //PeriodSSQModel model = WebUtil.grapSSQWebContentFilePath(filepath);

//        model.getDBKJTime();

//        model.outputinformatin();
//
        //SSQDBControl.insertSSQPeriodInfoDAO(model);

//        WebUtil.beginGrapDataFromWeb();

        //SSQDBControl.disconnectDBInformation();
//
//        System.out.println(SSQSQL.generatePeriodSQLStatement(model));
//
//        if (model != null) {
//            model.outputinformatin();
//            Gson gson = new Gson();
//
//            System.out.println(gson.toJson(model));
//        }

//        FileUtil.testrenamefunction();

//        System.get

//        try {
//            Class.forName("org.sqlite.JDBC");
//
//            Connection c = null;
//            Statement stmt = null;
//
//            c = DriverManager.getConnection("jdbc:sqlite:F:/AndroidStudioCode/AndroidFramework/libjavahelp/src/main/java/com/yutian/DBFile/ssq.db");
//            stmt = c.createStatement();
//            String sql = "select * from PRICELEVELDao";
//
//            ResultSet rs = stmt.executeQuery(sql);
//
//            while ( rs.next() ) {
//                int id = rs.getInt("LEVEL");
//                String  name = rs.getString("NAME");
////                int age  = rs.getInt("age");
//                String  address = rs.getString("PRESETPRICE");
//                System.out.println( "ID = " + id );
//                System.out.println( "NAME = " + name );
////                System.out.println( "AGE = " + age );
//                System.out.println( "ADDRESS = " + address );
//                System.out.println();
//            }
//
//            rs.close();
//            stmt.close();
//            c.close();
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//
//        }
//
//        File directory = new File("");
//        System.out.println(directory.getAbsolutePath());
    }
}
