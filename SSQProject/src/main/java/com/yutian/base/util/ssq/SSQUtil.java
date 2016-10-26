package com.yutian.base.util.ssq;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
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

    public static int COMMON_BALLSIZE = 0;
    public static Spannable SSQNOTESANDVALUE = null;

    /**
     * 计算ball的size大小
     * @param width 控件的宽度
     * @param height 控件的高度
     * @param ballcnt 需要显示的数目
     * @param ballMargin 控件的间隔
     * @return ball size
     */
    public static int cacluteBallSize(Context context, int width, int height, int ballcnt, int ballMargin) {
        if (COMMON_BALLSIZE != 0)
            return COMMON_BALLSIZE;
        //最新大小对比
        CheckButton checkButton = (CheckButton) LayoutInflater.from(context).inflate(R.layout.ssq_redball, null, false);
        checkButton.setText("00");
        int minBallSize = (int)DisplayUtil.getTextPxSize(checkButton, checkButton.getText().toString()) + DisplayUtil.dipToPx(context, Constants.SSQ_BALL_MARGIN);
        int minBallMargin = ballMargin <= 0 ? DisplayUtil.dipToPx(context, Constants.SSQ_BALL_MARGIN) : ballMargin;

        int minBallWidth = minBallSize + minBallMargin;
        int minBallTop = minBallWidth;

        int withTemp = width/ballcnt;

        if (withTemp > minBallWidth)
            minBallWidth = withTemp;

        if (minBallTop < height) {
            if (minBallTop < (int)(height*0.5f))
                minBallTop = (int)(height*0.5f);
            else if (minBallTop < (int)(height*0.75))
                minBallTop = (int)(height*0.75);
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
     * @param viewtext 文本
     * @param splitindex 分隔位
     * @param leftFontSize 前面文字大小
     * @param rightFontSize 后面文字大小
     * @return
     */
    public static Spannable getSSQNotesSpannableSize(CharSequence viewtext, int splitindex,
                                                     int leftFontSize, int rightFontSize)
    {
        Spannable WordtoSpan = new SpannableString(viewtext);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(leftFontSize), 0, splitindex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        WordtoSpan.setSpan(new AbsoluteSizeSpan(rightFontSize), splitindex, viewtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        WordtoSpan.setSpan(new ForegroundColorSpan(0xC70C00), 0, splitindex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        WordtoSpan.setSpan(new ForegroundColorSpan(0xFF4081), splitindex, viewtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return WordtoSpan;
    }


    /**
     * 更新SSQDataModel
     * @param originalObj
     * @param newObj
     * @return
     */
    public static boolean updateSSQDataModel(UISSQDataModel originalObj,
                                             UISSQDataModel newObj)
    {
        originalObj.setCount(newObj.getCount());
        originalObj.setPay(newObj.getPay());
        originalObj.setNeedSave(true);
        originalObj.setRedBallList(newObj.getRedBallList());
        originalObj.setBlueBallList(newObj.getBlueBallList());
        return true;
    }
}
