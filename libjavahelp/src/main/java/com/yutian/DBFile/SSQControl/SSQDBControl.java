package com.yutian.DBFile.SSQControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * Created by wuwenchuan on 2016/9/7.
 */
public class SSQDBControl {

    private Connection m_connection = null;
    private Statement m_statement = null;

    private static SSQDBControl g_ssqdbcontrol = null;

    //No right to access
    private SSQDBControl() {
        //This is a single object
    }

    /**
     * Init db
     *
     * @param dbpath database path
     * @return whether init success
     */
    public boolean initDBInformation(String dbpath) {
        if (dbpath == null) {
            return false;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            if (m_connection != null) {
                if (m_statement != null)
                    m_statement.close();
                m_connection.close();
            }
            Properties pro = new Properties();
//            pro.put("date_string_format", "yyyy-MM-dd hh:mm:ss");  //默认是yyyy-MM-dd HH:mm:ss.SSS，覆盖为yyyy-MM-dd HH:mm:ss；
            m_connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath); //, pro);
            m_statement = m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            m_connection.setAutoCommit(false);
            return true;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        m_connection = null;
        m_statement = null;

        return false;

    }

    /**
     * Disconnect db
     *
     * @return
     */
    public boolean disconnectDBInformation() {
        try {
            if (m_statement != null)
                m_statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (m_connection != null)
                try {
                    m_connection.close();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return false;
    }

    /**
     * Insert a new record
     *
     * @param sqlstatement sql statement
     * @param commitnow    Immediately commit
     * @return success or not
     */
    public boolean excutestatement(String sqlstatement, boolean commitnow) {
        boolean result = true;
        try {
            if (!commitnow) {
                m_statement.addBatch(sqlstatement);
            } else {
                m_statement.addBatch(sqlstatement);
                m_statement.executeBatch();
                m_connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /**
     * Use to query data
     * @param sqlstatemt sql statement
     * @param clz object class
     * @return
     */
    public List<Object> querystatement(String sqlstatemt, Class clz) {
        try {
            ResultSet result = m_statement.executeQuery(sqlstatemt);
            return SQLStatement.convertRSToObject(clz, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行查询语句，同时返回结果，需要手动关闭结果集
     * @param sqlstatemt 查询语句
     * @return 结果集
     */
    public ResultSet querystatement(String sqlstatemt)  {
        ResultSet result = null;
        try {
            result = m_statement.executeQuery(sqlstatemt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get Single db control
     *
     * @return
     */
    public static SSQDBControl getInstance() {
        if (g_ssqdbcontrol == null)
            synchronized (SSQDBControl.class) {
                if (g_ssqdbcontrol == null)
                    g_ssqdbcontrol = new SSQDBControl();
            }
        return g_ssqdbcontrol;
    }

    /**
     * Commit sql statement
     */
    public void commitstatement() {
        try {
            m_statement.executeBatch();
            m_connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
