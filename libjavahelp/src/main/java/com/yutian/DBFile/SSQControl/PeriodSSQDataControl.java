package com.yutian.DBFile.SSQControl;

import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;
import com.yutian.DBFile.SSQDBModel.SSQNUMBERWINNER;
import com.yutian.DBFile.SSQDBModel.SSQPERIODDATA;
import com.yutian.DBFile.SSQDBModel.SSQPERIODSTATUS;
import com.yutian.DBFile.SSQDBModel.SSQTIMEINFOR;
import com.yutian.DBFile.SSQControl.SSQModel.KJZQModel;
import com.yutian.DBFile.SSQControl.SSQModel.PeriodSSQModel;
import com.yutian.common.configvalue.SSQSQL;
import com.yutian.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yutian on 2016/9/13.
 */
public class PeriodSSQDataControl {

    private SSQDBControl m_ssqdbcontrol = null;
    private static PeriodSSQDataControl g_periodssqdatacontrol = null;

    //No right to access
    private PeriodSSQDataControl() {
        m_ssqdbcontrol = SSQDBControl.getInstance();
    }

    public static PeriodSSQDataControl getInstance() {
        if (g_periodssqdatacontrol == null) {
            synchronized (PeriodSSQDataControl.class) {
                if (g_periodssqdatacontrol == null)
                    g_periodssqdatacontrol = new PeriodSSQDataControl();
            }
        }
        return g_periodssqdatacontrol;
    }

    /**
     * Insert PeriodSSQModel
     * @param ssqModel need storage ssqmodel
     * @return whether insert success or not
     */
    public boolean insertSSQPeriodInfo(PeriodSSQModel ssqModel) {
        if (m_ssqdbcontrol == null)
            return false;

        boolean insertSuccess = false;
        String sql = null;
        try {
            sql = SSQSQL.generatePeriodSQLStatement(ssqModel);
            m_ssqdbcontrol.excutestatement(sql, false);
            sql = SSQSQL.generatePeriodTimeInforSQLStatement(ssqModel);
            m_ssqdbcontrol.excutestatement(sql, false);
            for (KJZQModel model :
                    ssqModel.getKjxq()) {
                sql = SSQSQL.generatePeriodWinInforSQLStatement(ssqModel.getPeriod(), model);
                m_ssqdbcontrol.excutestatement(sql, false);
            }

            m_ssqdbcontrol.commitstatement();
            insertSuccess = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sql = SSQSQL.generatePeriodStatusSQLStatement(ssqModel.getPeriod(), insertSuccess);
            m_ssqdbcontrol.excutestatement(sql, true);
        }

        return false;
    }

    /**
     * Insert PeriodSSQModel
     * @param period   期数
     * @param ssqModel 对应期数的数据
     * @return 是否成功插入
     */
    public boolean insertSSQPeriodInfo(String period, PeriodSSQModel ssqModel) {
        if (m_ssqdbcontrol == null)
            return false;

        boolean insertSuccess = false;
        String sql = null;
        try {

            if (ssqModel != null) {
                SSQPERIODDATA ssqperioddata = new SSQPERIODDATA(ssqModel);
                sql = SQLStatement.generateInsertSQL(ssqperioddata);
                m_ssqdbcontrol.excutestatement(sql, false);                  //Insert period balls information
                SSQTIMEINFOR ssqtimeinfor = new SSQTIMEINFOR(ssqModel);
                sql = SQLStatement.generateInsertSQL(ssqtimeinfor);
                m_ssqdbcontrol.excutestatement(sql, false);                 //Insert period time information
                for (KJZQModel model :
                        ssqModel.getKjxq()) {
                    SSQNUMBERWINNER temp = new SSQNUMBERWINNER(ssqModel.getPeriod(), model);
                    sql = SQLStatement.generateInsertSQL(temp);
                    m_ssqdbcontrol.excutestatement(sql, false);             //Insert period price information
                }

                m_ssqdbcontrol.commitstatement();
                insertSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Insert period status information
            updateOrinsertStatus(period, insertSuccess);
        }

        return insertSuccess;
    }

    /**
     * @param period 期数
     * @param success 是否成功插入
     * @return 执行是否成功
     */
    public boolean updateOrinsertStatus(String period, boolean success) {
        SSQPERIODSTATUS status = new SSQPERIODSTATUS(period, success);
        String sql = SQLStatement.generateSelectSQL(status);
        List<Object> result = m_ssqdbcontrol.querystatement(sql, SSQPERIODSTATUS.class);
        if (result != null && result.size() > 0) {
            sql = SQLStatement.generateUpdateSQL(status);
            m_ssqdbcontrol.excutestatement(sql, true);
        } else {
            sql = SQLStatement.generateInsertSQL(status);
            m_ssqdbcontrol.excutestatement(sql, true);
        }

        return true;
    }

    /**
     * 获取指定period的信息（不包括奖金等级）
     * @param period 期数
     * @return 对应的信息
     */
    public PeriodSSQModel selectPeriodSSQDataWithPeriod(String period)
    {
        PeriodSSQModel result = null;
        String sql = null;

        //获取red和blue的信息
        {
            SSQPERIODDATA ssqperioddata = new SSQPERIODDATA();
            ssqperioddata.setPERIOD(period);
            sql = SQLStatement.generateSelectSQL(ssqperioddata);
            List<Object> queryresults = m_ssqdbcontrol.querystatement(sql, SSQPERIODDATA.class);
            if (queryresults != null && queryresults.size() ==1) {
                result = convertSSQPERIODDATAToPeriodSSQModel((SSQPERIODDATA)queryresults.get(0));
            } else {
                return null;
            }
        }

        //获取时间信息
        {
            SSQTIMEINFOR ssqtimeinfor = new SSQTIMEINFOR();
            ssqtimeinfor.setPERIOD(period);
            sql = SQLStatement.generateSelectSQL(ssqtimeinfor);
            List<Object> queryresults = m_ssqdbcontrol.querystatement(sql, SSQTIMEINFOR.class);
            if (queryresults != null && queryresults.size() ==1) {
                ssqtimeinfor = (SSQTIMEINFOR)queryresults.get(0);
                result.setKjTime(DateUtil.formatSimpleSqlDateFormat(ssqtimeinfor.getLOTTERYTIME()));
                result.setDjTime(DateUtil.formatSimpleSqlDateFormat(ssqtimeinfor.getENDTIME()));
                result.setBqSell(ssqtimeinfor.getPERIODSELL());
                result.setPricePool(ssqtimeinfor.getCURRENPOOL());
            } else {
                return null;
            }
        }

        return result;
    }

    /**
     * 获取指定period的详细信息
     * @param period 期数
     * @return 对应的信息
     */
    public PeriodSSQModel selectDetailPeriodSSQDataWithPeriod(String period)
    {
        PeriodSSQModel result = null;
        String sql = null;

        //获取red和blue的信息
        {
            SSQPERIODDATA ssqperioddata = new SSQPERIODDATA();
            ssqperioddata.setPERIOD(period);
            sql = SQLStatement.generateSelectSQL(ssqperioddata);
            List<Object> queryresults = m_ssqdbcontrol.querystatement(sql, SSQPERIODDATA.class);
            if (queryresults != null && queryresults.size() ==1) {
                result = convertSSQPERIODDATAToPeriodSSQModel((SSQPERIODDATA)queryresults.get(0));
            } else {
                return null;
            }
        }

        //获取时间信息
        {
            SSQTIMEINFOR ssqtimeinfor = new SSQTIMEINFOR();
            ssqtimeinfor.setPERIOD(period);
            sql = SQLStatement.generateSelectSQL(ssqtimeinfor);
            List<Object> queryresults = m_ssqdbcontrol.querystatement(sql, SSQTIMEINFOR.class);
            if (queryresults != null && queryresults.size() ==1) {
                ssqtimeinfor = (SSQTIMEINFOR)queryresults.get(0);
                result.setKjTime(DateUtil.formatSimpleSqlDateFormat(ssqtimeinfor.getLOTTERYTIME()));
                result.setDjTime(DateUtil.formatSimpleSqlDateFormat(ssqtimeinfor.getENDTIME()));
                result.setBqSell(ssqtimeinfor.getPERIODSELL());
                result.setPricePool(ssqtimeinfor.getCURRENPOOL());
            } else {
                return null;
            }
        }

        //奖金等级信息
        {
            SSQNUMBERWINNER ssqnumberwinner = new SSQNUMBERWINNER();
            ssqnumberwinner.setPERIOD(period);
            sql = SQLStatement.generateSelectSQL(ssqnumberwinner);
            List<Object> queryresults = m_ssqdbcontrol.querystatement(sql, SSQNUMBERWINNER.class);
            if (queryresults != null) {
                result.setKjxq(convertSSQNUMBERWINNERsToKJZQModels(queryresults));
            } else {
                return null;
            }
        }

        return result;
    }

    /**
     * 将SSQPERIODDATA转化成PeriodSSQModel
     * @param source SSQPERIODDATA
     * @return PeriodSSQModel对象
     */
    public PeriodSSQModel convertSSQPERIODDATAToPeriodSSQModel(SSQPERIODDATA source)
    {
        PeriodSSQModel ssqModel = new PeriodSSQModel();
        ssqModel.setPeriod(source.getPERIOD());
        ssqModel.setSsqDataModel(new SSQDataModel(source));

        return ssqModel;

    }

    /**
     * 将SSQNUMBERWINNERs转化成KJZQModels
     * @param source SSQNUMBERWINNERs
     * @return KJZQModels
     */
    public List<KJZQModel> convertSSQNUMBERWINNERsToKJZQModels(List<Object> source)
    {
        List<KJZQModel> kjzqmodes = new ArrayList<>();
        for (Object obj:
                source) {
            SSQNUMBERWINNER ssqnumberwinner = (SSQNUMBERWINNER)obj;
            kjzqmodes.add(new KJZQModel(ssqnumberwinner));
        }

        return kjzqmodes;
    }

}
