package com.yutian.androidframework.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ViewAnimator;

import com.yutian.androidframework.R;

/**
 * Created by wuwenchuan on 2016/10/13.
 */
public class NumberView extends View {

    private GradientDrawable mGradientDrawable;
    private String mTextShow;

    //图形信息
    Rect mBigBehind;
    Rect mSmallBehind;
    RectF mArcShow;
    Point mLineShow;

    public NumberView(Context context) {
        this(context, null);
    }

    public NumberView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDisplay();
    }

    public void initDisplay() {
        mGradientDrawable = new GradientDrawable();
//        mGradientDrawable.setColor(Color.TRANSPARENT);
        setBackground(mGradientDrawable);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        refactcontainer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBigBehind != null) {
            Paint paint=new Paint();
            paint.setColor(ContextCompat.getColor(getContext(), R.color.numberboardbig));
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            canvas.drawRect(mBigBehind, paint);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.numberboardsmall));
            canvas.drawRect(mSmallBehind, paint);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.numberborad));
            canvas.drawArc(mArcShow, 255, 50, true, paint);
        }
    }

    //图形绘制
    public void refactcontainer() {
        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();

        mBigBehind = new Rect(0, (int)(0.25 * mHeight), mWidth, mHeight);
        mSmallBehind = new Rect();
        mSmallBehind.left = (int)(0.07f * mWidth);
        mSmallBehind.top = (int)(0.3f * mHeight);
        mSmallBehind.right = mBigBehind.right - mSmallBehind.left;
        mSmallBehind.bottom = mBigBehind.bottom - (int)(0.07f * mHeight);

        int temp = (mBigBehind.right - mBigBehind.left)/2;
        int radius = mHeight / 12;
        mArcShow = new RectF();
        mArcShow.left = temp - radius;
        mArcShow.right = temp + radius;
        mArcShow.top = mBigBehind.top - (int)(radius * 2.5f);
        mArcShow.bottom = mBigBehind.top - radius/2;

    }
}
