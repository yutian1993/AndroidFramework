package com.yutian.DBFile.SSQControl;

import com.yutian.DBFile.SSQDBDao.MYBUYDao;
import com.yutian.DBFile.SSQDBDao.MYSSQDATADao;
import com.yutian.DBFile.SSQDBModel.MYBUY;
import com.yutian.DBFile.SSQDBModel.MYSSQDATA;
import com.yutian.DBFile.SSQControl.SSQModel.MySSQModel;
import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MYBUY table control
 * Created by wuwenchuan on 2016/9/12.
 */
public class MySSQDataControl {

    private SSQDBControl ssqdbControl = null;
    private static MySSQDataControl g_ssqdbcontrol = null;

    //No right to access
    private MySSQDataControl() {
        ssqdbControl = SSQDBControl.getInstance();
    }

    public static MySSQDataControl getInstance()
    {
        if (g_ssqdbcontrol == null) {
            synchronized (MySSQDataControl.class) {
                if (g_ssqdbcontrol == null) {
                    g_ssqdbcontrol = new MySSQDataControl();
                }
            }
        }

        return g_ssqdbcontrol;
    }

    /**
     * 插入一次购买记录
     * @param mySSQModel
     * @return
     */
    public boolean insertMySSQData(MySSQModel mySSQModel)
    {
        String sql = "";

        for (SSQDataModel ssqDateModel :
                mySSQModel.getSsqDataModels()) {
            MYSSQDATA temp = new MYSSQDATA(ssqDateModel);

            if ((sql = SQLStatement.generateInsertSQL(temp)) != null) {
                if (!ssqdbControl.excutestatement(sql, false))
                    return false;
            }
        }

        //Add Main record
        MYBUY temp = new MYBUY(mySSQModel);
        sql = SQLStatement.generateInsertSQL(temp);
        if (!ssqdbControl.excutestatement(sql, false))
            return false;

        ssqdbControl.commitstatement();
        return true;
    }

    /**
     * 插入一条我的购买记录
     * @param ssqDataModel 一条购买记录
     * @return 是否成功添加
     */
    public boolean insertMySSQData(SSQDataModel ssqDataModel)
    {
        if (ssqDataModel == null)
            return false;

        String sql = "";
        MYSSQDATA temp = new MYSSQDATA(ssqDataModel);

        if ((sql = SQLStatement.generateInsertSQL(temp)) != null) {
            if (!ssqdbControl.excutestatement(sql, false))
                return false;
        }

        ssqdbControl.commitstatement();
        return true;
    }

    /**
     * 删除我的购买记录
     * @param mySSQModel 我的购买记录
     * @return 是否成功删除
     */
    public boolean deleteMySSQData(MySSQModel mySSQModel)
    {
        String sql = "";

        //Delete balls
        sql = MYSSQDATADao.deleteSqlStatementWithID(mySSQModel.getId());
        if (!ssqdbControl.excutestatement(sql, false))
            return false;

        if (mySSQModel.getSsqDataModels().size() > 0) {        //删除指定条购买记录
            for (SSQDataModel ssqDataModel :
                    mySSQModel.getSsqDataModels()) {
                sql = MYSSQDATADao.deleteSqlStatementWithIDAndNum(ssqDataModel.getId(), ssqDataModel.getNumber());
                if(!ssqdbControl.excutestatement(sql, false))
                    return false;
            }
        } else {                                                //删除该ID下所有的ID记录
            MYBUY mybuy = new MYBUY();
            mybuy.setID(mySSQModel.getId());
            sql = SQLStatement.generateDeleteSQL(mybuy);
            if(!ssqdbControl.excutestatement(sql, false))
                return false;
        }

        ssqdbControl.commitstatement();
        return true;
    }

    /**
     * 删除一条我的购买记录：指定的ID，和Index
     * @param ssqDataModel 一条购买记录
     * @return 是否成功删除
     */
    public boolean deleteMySSQData(SSQDataModel ssqDataModel) {
        String sql;
        if (ssqDataModel != null)
            sql = MYSSQDATADao.deleteSqlStatementWithIDAndNum(ssqDataModel.getId(), ssqDataModel.getNumber());
        else
            return false;

        if(!ssqdbControl.excutestatement(sql, false))
            return false;

        ssqdbControl.commitstatement();
        return true;
    }

    /**
     * 根据ID获取我的一次购买纪录
     * @param id ID号
     * @return 我的一次购买纪录
     */
    public MySSQModel selectMySSQData(String id) {
        MySSQModel mySSQModel = new MySSQModel();

        MYBUY mybuy = new MYBUY();
        mybuy.setID(id);
        String sql = SQLStatement.generateSelectSQL(mybuy);
        List<Object> queryresult = ssqdbControl.querystatement(sql, MYBUY.class);
        if (queryresult != null && queryresult.size() == 1) {
            mySSQModel = convertMYBUYToSSQDataModel((MYBUY)queryresult.get(0));
        } else {
            return null;
        }

        sql = MYSSQDATADao.selectSqlStatementWithID(id);

        List<SSQDataModel>  results = null;
        queryresult = ssqdbControl.querystatement(sql, MYSSQDATA.class);
        results = convertMYSSQDATAsToSSQDataModels(queryresult);

        if (results != null) {
            mySSQModel.setSsqDataModels(results);
        } else {
            return null;
        }

        return mySSQModel;
    }

    /**
     * 根据购买的ID号序列号获取对应的购买信息
     * @param id 购买的ID
     * @param number 对应的序列号
     * @return SSQDataModel
     */
    public SSQDataModel selectMySSQData(String id, int number) {
        MYSSQDATA temp = new MYSSQDATA();
        temp.setID(id);
        temp.setNUMBER(number);

        String sql = SQLStatement.generateSelectSQL(temp);
        List<Object> queryresult = ssqdbControl.querystatement(sql, MYSSQDATA.class);

        if (queryresult != null && queryresult.size() == 1) {
            return convertMYSSQDATAToSSQDataModel((MYSSQDATA)queryresult.get(0));
        } else {
            return null;
        }
    }

    /**
     * 更新我的一次购买纪录中的一条信息
     * @param ssqDataModel 指定的信息
     * @return 是否成功修改
     */
    public boolean updateMySSQData(SSQDataModel ssqDataModel) {
        MYSSQDATA temp = new MYSSQDATA(ssqDataModel);

        String sql = "";
        sql = SQLStatement.generateUpdateSQL(temp);
//        System.out.println(sql);

        ssqdbControl.excutestatement(sql, true);

        return false;
    }

    /**
     * 获取我的所有购买纪录，不包括详细的信息
     * @return
     */
    public List<MySSQModel> selectMySSQData() {
        List<MySSQModel> selectresults = new ArrayList<>();

        String sql = MYBUYDao.selectSqlStatement();
        List<Object> queryresult = ssqdbControl.querystatement(sql, MYBUY.class);
        selectresults = convertMYBUYsToMySSQModels(queryresult);

        return selectresults;
    }

    /**
     * 获取我的所有购买纪录，包括详细的信息
     * @return 所有的购买信息
     */
    public List<MySSQModel> selectAllMySSQData() {
        //获取购买的时间信息
        List<MySSQModel> selectresults = selectMySSQData();

        if (selectresults != null) {
            //format data for easy operator
            Map<String, Integer> ssqmap = new HashMap<>();
            for(int len = selectresults.size() - 1; len >= 0; --len) {
                ssqmap.put(selectresults.get(len).getId(), len);
            }

            //获取所有的记录
            String sql = MYSSQDATADao.selectSqlStatement();
            List<Object> queryresult = ssqdbControl.querystatement(sql, MYSSQDATA.class);
            List<SSQDataModel> allrecords = convertMYSSQDATAsToSSQDataModels(queryresult);

            for (SSQDataModel record :
                    allrecords) {
                Integer index = ssqmap.get(record.getId());
                if (index != null) {
                    selectresults.get(index).addSsqDataModel(record);
                } else {
                    System.out.println("Record error!");
                }
            }
        } else {
            return null;
        }

        return selectresults;
    }

    /**
     * Convert MYSSQDATAs to SSQDataModels
     * @param queryresult MYSSQDATAs
     * @return SSQDataModels
     */
    public static List<SSQDataModel> convertMYSSQDATAsToSSQDataModels(List<Object> queryresult) {
        List<SSQDataModel>  results = null;
        if (queryresult != null) {
            results = new ArrayList<>();
            for (Object result :
                    queryresult) {
                MYSSQDATA mydata = (MYSSQDATA) result;
                results.add(new SSQDataModel(mydata));
            }
        }
        return results;
    }

    /**
     * Convert MYBUYs to MySSQModels
     * @param queryresult MYBUYs
     * @return MySSQModels
     */
    public static List<MySSQModel> convertMYBUYsToMySSQModels(List<Object> queryresult) {
        List<MySSQModel> selectresults = null;
        if (queryresult != null) {
            selectresults = new ArrayList<>();
            for (Object result :
                    queryresult) {
                MYBUY mybuy = (MYBUY) result;
                selectresults.add(new MySSQModel(mybuy));
            }
        } else {
            return null;
        }
        return selectresults;
    }

    /**
     * Convert MYBUY to MySSQModel
     * @param mybuy mybuy
     * @return MySSQModel
     */
    public static MySSQModel convertMYBUYToSSQDataModel(MYBUY mybuy) {
        MySSQModel mySSQModel = null;

        if (mybuy != null) {
            mySSQModel = new MySSQModel(mybuy);
        }

        return mySSQModel;
    }


    /**
     * Convert MYSSQDATA to SSQDataModel
     * @param myssqdata myssqdata
     * @return SSQDataModel
     */
    public static SSQDataModel convertMYSSQDATAToSSQDataModel(MYSSQDATA myssqdata) {
        if (myssqdata != null) {
            return new SSQDataModel(myssqdata);
        }

        return null;
    }

}
