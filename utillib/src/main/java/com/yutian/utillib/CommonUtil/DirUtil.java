package com.yutian.utillib.CommonUtil;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Directory工具
 * Created by wuwenchuan on 16-7-21.
 */
public class DirUtil {

    /**
     * 获取SDCard目录绝对路径
     * 需要permission
     */
    public static String getSDCardDir()
    {
        return Environment.getExternalStorageDirectory().toString();
    }

    /**
     * 获取应用程序cache目录绝对路径
     */
    public static String getCacheDir()
    {
        return Environment.getDownloadCacheDirectory().toString();
    }

    /**
     * 获取应用的数据库位置
     * @param context
     * @return
     */
    public static File getDataBaseDir(Context context)
    {
        return context.getDir("database", 0);
    }
}
