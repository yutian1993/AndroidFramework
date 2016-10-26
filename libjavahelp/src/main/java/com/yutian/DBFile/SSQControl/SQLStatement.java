package com.yutian.DBFile.SSQControl;

import com.yutian.DBFile.DBAnnotation.ID;
import com.yutian.util.DateUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2016/9/10.
 */
public class SQLStatement {

    public static String generateInsertSQL(Object obj) {
        boolean result = false;
        String sqlresult = null;



        if (obj == null)
            return sqlresult;

        String sql = "INSERT INTO " + obj.getClass().getSimpleName() + "( ";

        Field[] objFields = obj.getClass().getDeclaredFields();
        for (Field objField :
                objFields) {
            sql = sql + objField.getName() + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES( ";

        try {
            Method needExcute = null;
            String methodName = "";
            String methodVal = "";
            for (Field objField :
                    objFields) {
                methodName = "get" + objField.getName();
                needExcute = obj.getClass().getMethod(methodName);
                Object methodResult = needExcute.invoke(obj);

                //Don't get the result
                if (methodResult == null) {
                    sql += "'',";
                    continue;
                }

                Class clz = methodResult.getClass();
                if (clz == Date.class) {                              //Date need to covert to String
                    methodVal = DateUtil.formatSimpleSqlDateFormat((Date) methodResult);
                } else if (clz == String.class) {                     //Just String
                    methodVal = (String) methodResult;
                } else {
                    methodVal = "" + methodResult;
                }
                sql += "'" + methodVal + "',";
            }

            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
            result = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (result)
            sqlresult = sql;
        else
            sqlresult = null;

        return sqlresult;
    }

    public static String generateUpdateSQL(Object obj) {
        boolean result = false;
        String sqlresult = null;

        if (obj == null)
            return sqlresult;

        String sql = "UPDATE " + obj.getClass().getSimpleName() + " SET ";
        String contionsql = " WHERE ";
        boolean iskey = false;

        Field[] objFields = obj.getClass().getDeclaredFields();
        try {
            Method needExcute = null;
            String methodName = "";
            String methodVal = "";
            for (Field objField :
                    objFields) {
                methodName = "get" + objField.getName();
                needExcute = obj.getClass().getMethod(methodName);
                Object methodResult = needExcute.invoke(obj);

                //Don't get the result
                if (methodResult == null) {
//                    sql += objField.getName() + " = '',";
                    continue;
                }

                Annotation[] annotatios = objField.getAnnotations();
                for (Annotation annotation :
                        annotatios) {
                    if (annotation.annotationType() == ID.class)
                        iskey = true;
                }

                Class clz = methodResult.getClass();
                if (clz == Date.class) {                              //Date need to covert to String
//                    Date date = (Date)methodResult;
                    methodVal = DateUtil.formatSimpleSqlDateFormat((Date) methodResult);
//                    sql += objField.getName() + " ='"+ DateUtil.formatSimpleSqlDateFormat(date) +"',";
//
//                    if (iskey)
//                        contionsql += objField.getName() + " = '" +  + "',";
                } else if (clz == String.class) {                     //Just String
                    methodVal = (String) methodResult;
//                    sql += objField.getName() + " ='"+ methodResult +"',";
                } else {
                    methodVal = "" + methodResult;
//                    sql += objField.getName() + " ='"+ methodResult +"',";
                }

                sql += objField.getName() + " ='" + methodVal + "',";
                if (iskey)
                    contionsql += objField.getName() + " = '" + methodVal + "' AND ";

                //Reset key
                iskey = false;
            }

            contionsql = contionsql.substring(0, contionsql.length() - 5);   //DELETE THE " AND"
            sql = sql.substring(0, sql.length() - 1);                //DELETE THE ","
            sql = sql + contionsql;

            result = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (result)
            sqlresult = sql;
        else
            sqlresult = null;

        return sqlresult;
    }

    public static String generateDeleteSQL(Object obj) {
        boolean result = false;
        String sqlresult = null;

        if (obj == null)
            return sqlresult;

        String sql = "DELETE FROM " + obj.getClass().getSimpleName();
        String contionsql = " WHERE ";
        boolean iskey = false;

        Field[] objFields = obj.getClass().getDeclaredFields();
        try {
            Method needExcute = null;
            String methodName = "";
            String methodVal = "";
            for (Field objField :
                    objFields) {
                methodName = "get" + objField.getName();
                needExcute = obj.getClass().getMethod(methodName);
                Object methodResult = needExcute.invoke(obj);

                //Don't get the result
                if (methodResult == null) {
                    continue;
                }

                Annotation[] annotatios = objField.getAnnotations();
                for (Annotation annotation :
                        annotatios) {
                    if (annotation.annotationType() == ID.class)
                        iskey = true;
                }

                Class clz = methodResult.getClass();

                if (clz == Date.class) {                              //Date need to covert to String
                    methodVal = DateUtil.formatSimpleSqlDateFormat((Date) methodResult);
                } else if (clz == String.class) {                     //Just String
                    methodVal = (String) methodResult;
                } else {
                    methodVal = "" + methodResult;
                }

                if (iskey)
                    contionsql += objField.getName() + " = '" + methodVal + "' AND ";

                //Reset key
                iskey = false;
            }

            contionsql = contionsql.substring(0, contionsql.length() - 4);   //DELETE THE ","
            sql += contionsql;

            result = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (result)
            sqlresult = sql;
        else
            sqlresult = sql;

        return sqlresult;
    }

    public static String generateSelectSQL(Object obj) {
        boolean result = false;
        String sqlresult = null;

        if (obj == null)
            return sqlresult;

        String sql = "SELECT * FROM " + obj.getClass().getSimpleName();
        String contionsql = " WHERE ";
        boolean iskey = false;

        Field[] objFields = obj.getClass().getDeclaredFields();
        try {
            Method needExcute = null;
            String methodName = "";
            String methodVal = "";
            for (Field objField :
                    objFields) {
                methodName = "get" + objField.getName();
                needExcute = obj.getClass().getMethod(methodName);
                Object methodResult = needExcute.invoke(obj);

                //Don't get the result
                if (methodResult == null) {
                    continue;
                }

                Annotation[] annotatios = objField.getAnnotations();
                for (Annotation annotation :
                        annotatios) {
                    if (annotation.annotationType() == ID.class)
                        iskey = true;
                }

                Class clz = methodResult.getClass();

                if (clz == Date.class) {                              //Date need to covert to String
                    methodVal = DateUtil.formatSimpleSqlDateFormat((Date) methodResult);
                } else if (clz == String.class) {                     //Just String
                    methodVal = (String) methodResult;
                } else {
                    methodVal = "" + methodResult;
                }

                if (iskey)
                    contionsql += objField.getName() + " = '" + methodVal + "' AND ";

                //Reset key
                iskey = false;
            }

            contionsql = contionsql.substring(0, contionsql.length() - 4);   //DELETE THE ","
            sql += contionsql;

            result = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (result)
            sqlresult = sql;
        else
            sqlresult = null;

        return sqlresult;
    }

    /**
     * 将数据库中的数据转换成对象列表
     * @param resultset 数据库中查询到的resultset
     * @return object list
     */
    public static List<Object> convertRSToObject(Class clz, ResultSet resultset) {
        List<Object> result = new ArrayList<Object>();

        try {
            Method needExcute = null;
            Field[] objFields = null;
            String methodName = "";
            while (resultset.next()) {
                Object obj = clz.newInstance();
                objFields = obj.getClass().getDeclaredFields();
                for (Field objField :
                        objFields) {
                    methodName = "set" + objField.getName();
                    try {
                        needExcute = obj.getClass().getMethod(methodName, objField.getType());
                    } catch (NoSuchMethodException e) {
                        continue;
                    }
                    if (objField.getType() == Date.class) {
                        needExcute.invoke(obj, DateUtil.parseSimpleSqlDate(resultset.getString(objField.getName())));
                    } else if (objField.getType() == Integer.class) {
                        needExcute.invoke(obj, resultset.getInt(objField.getName()));
                    } else if (objField.getType() == String.class) {
                        needExcute.invoke(obj, resultset.getString(objField.getName()));
                    } else {
                        needExcute.invoke(obj, resultset.getObject(objField.getName()));
                    }
                }

                result.add(obj);
            }
          }
//        catch (InstantiationException e) {
//            e.printStackTrace();}
//        catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
          catch (Exception e) {
                e.printStackTrace();
          } finally {
            try {
                resultset.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据class的名称对应到数据库的名称，获取对应表的所有内容
     * @param clz 表名对应到java的model对象
     * @return 数据库语句
     */
    public static String generateSelectAllSQL(Class clz) {
         return "SELECT * FROM " + clz.getSimpleName();
    }


}
