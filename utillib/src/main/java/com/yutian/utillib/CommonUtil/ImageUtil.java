package com.yutian.utillib.CommonUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wuwenchuan on 16-7-30.
 */
public class ImageUtil {

    private static String TAG = "IMAGEUTIL";

    /**
     * 根据文件路径获取文件内容
     * @param filepath 图片路径
     * @return bitmap
     */
    public static Bitmap getImageContent(String filepath)
    {
        return BitmapFactory.decodeFile(filepath);
    }

    /**
     * 根据文件的路径获取图片的内容，并转成byte数组
     * @param filepath 图片路径
     * @return 图片byte数组数据
     */
    public static byte[] getImageByteContent(String filepath)
    {
        Bitmap bitmap = getImageContent(filepath);
        if (bitmap == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将bitmap保存到文件中
     * @param bitmap bitmap对象
     * @param filepath 文件存储路径
     * @return 是否成功保存
     */
    public static boolean saveImageToStorage(Bitmap bitmap, String filepath)
    {
        if (bitmap == null || filepath == null)
            return false;

        Log.d(TAG, "Begin storage image: " + filepath);
        File file = new File(filepath);
        boolean result = file.exists() ? file.delete() : true;
        if (result) {
            try {
                FileOutputStream writeStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, writeStream);
                writeStream.flush();
                writeStream.close();
            } catch (FileNotFoundException e) {
                result = false;
                e.printStackTrace();
                Log.e(TAG, "File not found");
            } catch (IOException e) {
                result = false;
                Log.e(TAG, "IO Error!");
            }
        }

        return result;
    }

}
