package com.yutian.base.database.def;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by wuwenchuan on 16-7-18.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context) {
        super(context, "todo.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
