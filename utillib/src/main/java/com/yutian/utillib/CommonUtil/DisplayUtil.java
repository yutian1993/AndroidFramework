package com.yutian.utillib.CommonUtil;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by yutian on 2016/9/25.
 */
public class DisplayUtil {

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().getLayoutDirection() == Configuration.ORIENTATION_LANDSCAPE ? true : false;
    }

    public static boolean isPortrait (Context context) {
        return context.getResources().getConfiguration().getLayoutDirection() == Configuration.ORIENTATION_PORTRAIT ? true : false;
    }

    public static int dipToPx(Context c,float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static int spToPx(Context context, float spValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }

    /**
     * 获取指定TextView中的文字长度
     * @param parent 指定的textview
     * @param text 待检测文字
     * @return 文字长度px
     */
    public static float getTextPxSize(TextView parent, String text) {
        return parent.getPaint().measureText(text);
    }

    public static float getTextPxSize(TextView parent, CharSequence text) {
        return getTextPxSize(parent, text.toString());
    }

}
