package com.yutian.base.database.def;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;
import android.util.Log;

import com.yutian.androidframework.R;
import com.yutian.androidframework.SSQApplication;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.base.database.srcgen.DaoSession;
import com.yutian.base.util.DirUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wuwenchuan on 2016/10/24.
 */
public class SSQDBHelper {
    private static final String TAG = SSQDBHelper.class.getSimpleName();

    private static SSQDBHelper gSSQDBHelper = null;

    public static SSQDBHelper getInstance() {
        if (gSSQDBHelper == null)
            synchronized (SSQDBHelper.class) {
                if (gSSQDBHelper == null) {
                    gSSQDBHelper = new SSQDBHelper();
                }
            }
        return gSSQDBHelper;
    }

    /**
     * 获取App数据库的位置
     *
     * @param context
     * @return
     */
    public String getDBPath(Context context) {
        String path = DirUtil.getDataBaseDir(context) + "/" + Constants.DB_NAME;

        if (!checkDataBase(path)) {
            try {
                copyAssertToSystem(context, path);
            } catch (IOException e) {
                Log.e(TAG, context.getString(R.string.ssq_db_error));
            }
        }

        return path;
    }

    /**
     * 检查数据库是否存在
     * @param path 数据库所在的位置
     * @return
     */
    protected boolean checkDataBase(String path) {
        SQLiteDatabase tempDB = null;
        try {
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException dbException) {
            //Do nothing
            Log.e(TAG, "Open error");
        }

        if (tempDB != null)
            tempDB.close();

        return tempDB == null ? false : true;
    }

    /**
     * 拷贝内置数据（互联网上的的代码）
     * @param context
     * @param path
     * @return
     * @throws IOException
     */
    protected boolean copyAssertToSystem(Context context, String path) throws IOException {
        Log.v(Constants.DB_NAME, "Begin copy SSQ Database");
        //Open your local db as the input stream
//        String[] fileName =  context.getAssets().list("");
//        for (String filename: fileName
//             ) {
//            Log.e(TAG, filename);
//        }
        InputStream myInput = context.getResources().openRawResource(R.raw.ssq);
//        InputStream myInput = context.getAssets().open(Constants.DB_NAME);
        // Path to the just created empty db
        String outFileName = path;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

        return true;
    }
}
