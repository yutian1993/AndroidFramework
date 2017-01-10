package com.yutian.utillib.CommonUtil;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 *
 * Created by wuwenchuan on 16-7-29.
 */
public class PermissionUtil {

    private static PackageManager g_packagemanager = null;
    private static String g_packagename="";

    protected static void setPackageManager(Context context)
    {
        if (g_packagemanager == null) {
            g_packagemanager = context.getPackageManager();
            g_packagename = context.getPackageName();
        }
    }

    public static void initcontext(Context context)
    {
        setPackageManager(context);
    }

    /**
     * 检查当前应用程序是否拥有读取SDCard数据的权限
     * @return false/true
     */
    public static boolean checkReadSDCard()
    {
        return checkPermission("android.permission.READ_EXTERNAL_STORAGE");
    }

    /**
     * 检查当前应用程序是否拥有写入SDCard数据的权限
     * @return false/true
     */
    public static boolean checkWriteSDCard()
    {
        return checkPermission("android.permission.WRITE_EXTERNAL_STORAG");
    }

    /**
     * 检查当前应用程序是否拥有指定的permission
     * @param permission 字符串，同AndroidManifest中配置字符串相同
     * @return false/true
     */
    public static boolean checkPermission(String permission)
    {
        if (g_packagemanager == null)
            return false;
        return (PackageManager.PERMISSION_GRANTED == g_packagemanager.checkPermission(permission, g_packagename));
    }
}
