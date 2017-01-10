package com.yutian.utillib.CommonUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by wuwenchuan on 16-7-30.
 */
public class ImageUtil {

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

}
