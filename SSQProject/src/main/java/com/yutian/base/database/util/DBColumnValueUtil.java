package com.yutian.base.database.util;

import android.database.Cursor;

/**
 * This util class use to
 * Created by wuwenchuan on 16-7-18.
 */
public class DBColumnValueUtil {

    public final static int BOOLEAN_FALSE = 0;
    public final static int BOOLEAN_TRUE = 1;

    public static String getString(Cursor cursor, String columnName)
    {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName)
    {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static float getFloat(Cursor cursor, String columnName)
    {
        return cursor.getFloat(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName)
    {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static byte[] getNativeData(Cursor cursor, String columnName)
    {
        return cursor.getBlob(cursor.getColumnIndexOrThrow(columnName));
    }

    public static Object getColumnValue(Cursor cursor, String columnName)
    {
        int columntype = cursor.getType(cursor.getColumnIndexOrThrow(columnName));
        switch (columntype) {
            case Cursor.FIELD_TYPE_NULL:
                return null;
//            break;
            case Cursor.FIELD_TYPE_INTEGER:
                return getInt(cursor,columnName);
//            break;
            case Cursor.FIELD_TYPE_FLOAT:
                return getFloat(cursor, columnName);
//            break;
            case Cursor.FIELD_TYPE_BLOB:
                return getNativeData(cursor,columnName);
//            break;
            case Cursor.FIELD_TYPE_STRING:
                return getString(cursor,columnName);
//            break;
            default:
                return null;
        }
    }

    private DBColumnValueUtil() {
        throw new AssertionError("No instance!");
    }
}
