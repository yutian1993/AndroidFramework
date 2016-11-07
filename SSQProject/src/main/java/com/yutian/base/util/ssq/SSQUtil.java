package com.yutian.base.util.ssq;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;

import com.yutian.DBFile.SSQControl.SSQModel.SSQDataModel;
import com.yutian.androidframework.R;
import com.yutian.androidframework.control.ssq.ssqmodel.UISSQDataModel;
import com.yutian.androidframework.ui.widgets.CheckButton;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.base.util.DisplayUtil;

/**
 * Created by wuwenchuan on 2016/10/15.
 */
public class SSQUtil {
    private static String TAG = SSQUtil.class.getSimpleName();

    /**
     * 计算ball的size大小
     *
     * @param width      控件的宽度
     * @param height     控件的高度
     * @param ballcnt    需要显示的数目
     * @param ballMargin 控件的间隔
     * @return ball size
     */
    public static int cacluteBallSize(Context context, int width, int height, int ballcnt, int ballMargin) {
        //最新大小对比
        CheckButton checkButton = (CheckButton) LayoutInflater.from(context).inflate(R.layout.ssq_redball, null, false);
        checkButton.setText("00");
        int minBallSize = (int) DisplayUtil.getTextPxSize(checkButton, checkButton.getText().toString()) + DisplayUtil.dipToPx(context, Constants.SSQ_BALL_MARGIN);
        int minBallMargin = ballMargin <= 0 ? DisplayUtil.dipToPx(context, Constants.SSQ_BALL_MARGIN) : ballMargin;

        int minBallWidth = minBallSize + minBallMargin;
        int minBallTop = minBallWidth;

        int withTemp = width / ballcnt;

        if (withTemp > minBallWidth)
            minBallWidth = withTemp;

        if (minBallTop < height) {
            if (minBallTop < (int) (height * 0.5f))
                minBallTop = (int) (height * 0.5f);
            else if (minBallTop < (int) (height * 0.75))
                minBallTop = (int) (height * 0.75);
            else
                minBallTop = height;
        }

        minBallWidth = minBallWidth - ballMargin;
        minBallTop = minBallTop - ballMargin;

        minBallSize = minBallWidth < minBallTop ? minBallWidth : minBallTop;

        //重新构造ball size

//        ballcnt = width/(minBallSize + ballMargin);

        return minBallSize;
    }

    /**
     * 设置spannable size
     *
     * @param viewtext      文本
     * @param splitindex    分隔位
     * @param leftFontSize  前面文字大小
     * @param rightFontSize 后面文字大小
     * @return
     */
    public static Spannable getSSQNotesSpannableSize(CharSequence viewtext, int splitindex,
                                                     int leftFontSize, int rightFontSize) {
        Spannable WordtoSpan = new SpannableString(viewtext);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(leftFontSize), 0, splitindex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(rightFontSize), splitindex, viewtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        WordtoSpan.setSpan(new ForegroundColorSpan(0xC70C00), 0, splitindex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        WordtoSpan.setSpan(new ForegroundColorSpan(0xFF4081), splitindex, viewtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return WordtoSpan;
    }

    /**
     * 修改字符串中的强调颜色
     *
     * @param itemtext     待处理的字符串
     * @param defaultcolor 默认的字体颜色
     * @param bold         字体是否需要加粗
     * @param contents     包括字符串以及字符串对应的颜色
     *                     contents[2n] = "string"
     *                     contents[2n+1] = "color"
     * @return
     */
    public static Spannable setSSQMainItemColorSpannable(String itemtext, Integer defaultcolor,
                                                         Boolean bold, Object... contents) {
        if (contents == null || contents.length % 2 != 0) {
            Log.e(TAG, "String and Color not match each other!");
        }

        //格式化字符串
        if (contents.length > 2) {
            Object args[] = new Object[contents.length / 2];
            for (int i = 0; i < contents.length; i = i + 2) {
                if (contents[i] == null) {
                    args[i / 2] = "--";
                    contents[i] = "--";
                }
                else
                    args[i / 2] = contents[i];
            }
            itemtext = String.format(itemtext, args);
        } else {
            for (int i = 0; i < contents.length; i = i + 2) {
                if (contents[i] == null) {
                    contents[i] = "--";
                }
                itemtext = String.format(itemtext, contents[i]);
            }
        }

        //没有默认颜色就不进行设置
        Spannable content = new SpannableString(itemtext);
        if (defaultcolor == null)
            content.setSpan(new ForegroundColorSpan(defaultcolor), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置替换后的颜色
        int indexStart = 0;
        for (int i = 0; i < contents.length; i += 2) {
            indexStart = itemtext.indexOf((String) contents[i], indexStart);
            if (contents[i + 1] != null)
                content.setSpan(new ForegroundColorSpan((int) contents[i + 1]), indexStart, indexStart + ((String) contents[i]).length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (bold == true) {
                content.setSpan(new StyleSpan(Typeface.BOLD), indexStart, indexStart + ((String) contents[i]).length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return content;
    }

    /**
     * 更新SSQDataModel
     *
     * @param originalObj
     * @param newObj
     * @return
     */
    public static boolean updateSSQDataModel(UISSQDataModel originalObj,
                                             UISSQDataModel newObj) {
        originalObj.setCount(newObj.getCount());
        originalObj.setPay(newObj.getPay());
        originalObj.setNeedUpdate(true);
        //更新数据Model时不会更新是否需要保存，只设置更新属性
//        originalObj.setNeedSave(newObj.isNeedSave());
        originalObj.setRedBallList(newObj.getRedBallList());
        originalObj.setBlueBallList(newObj.getBlueBallList());
        return true;
    }
}
