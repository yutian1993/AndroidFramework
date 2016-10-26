package com.yutian.base.database.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by wuwenchuan on 16-7-18.
 */
public class DBImageValueUtil {

    private DBImageValueUtil()
    {
        throw new AssertionError("No Instance.");
    }

    /**
     * Get image bitmap content from image byte array
     * @param imageContent image byte array
     * @return Bitmap object
     */
    public static Bitmap getBitmapFromByte(byte[] imageContent)
    {
        if (imageContent != null && imageContent.length > 0)
            return BitmapFactory.decodeByteArray(imageContent, 0, imageContent.length);
        return null;
    }

    /**
     * Convert bitmap data to byte array
     * @param bitmap image
     * @return byte array
     */
    public static byte[] getBitmapByte(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}
