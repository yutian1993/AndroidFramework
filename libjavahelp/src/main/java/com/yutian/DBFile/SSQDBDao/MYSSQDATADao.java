package com.yutian.DBFile.SSQDBDao;

/**
 * Created by wuwenchuan on 2016/9/10.
 */
public class MYSSQDATADao {

    /**
     * 通过ID来批量删除数据
     * @param id 记录的ID
     * @return 对应SQL Statement
     */
    public static String deleteSqlStatementWithID(String id) {
        return "DELETE FROM MYSSQDATA WHERE ID = '" + id + "'";
    }

    /**
     * 通过ID和Number删除一条记录
     * @param id ID
     * @param number 记录对应number
     * @return 对应的SQL statement
     */
    public static String deleteSqlStatementWithIDAndNum(String id, Integer number) {
        if (number == null)
            return deleteSqlStatementWithID(id);
        else
            return "DELETE FROM MYSSQDATA WHERE ID = '" + id + "' AND NUMBER = '" + number + "'";
    }

    /**
     * 根据ID来获取购买的记录
     * @param id ID
     * @return 对应的SQL statement
     */
    public static String selectSqlStatementWithID(String id) {
        return "SELECT * FROM MYSSQDATA WHERE ID = '" + id + "'";
    }

    /**
     * 获取所有的购买纪录
     * @return 对应的SQL statement
     */
    public static String selectSqlStatement() {
        return "SELECT * FROM MYSSQDATA";
    }

}
