package com.yutian.exceptiontext.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by wuwenchuan on 2017/3/17.
 */
public class CircleView extends View {
    private int mBigRadius;
    private int mSmallRadius;
    private int mFinishColor = 0xFF00FF00;
    private int mUnfinishColor = 0xFFDECFDE;

    private int mWidth;
    private int mHeight;

    private Paint mPaintOne;
    private Paint mPaintTwo;
    private Paint mPaintThree;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mBigRadius = 250;
        mSmallRadius = 150;

        mPaintOne = new Paint();
        mPaintOne.setColor(mFinishColor);
        mPaintOne.setStyle(Paint.Style.FILL);

        mPaintTwo = new Paint();
        mPaintTwo.setColor(mUnfinishColor);
        mPaintTwo.setStyle(Paint.Style.FILL);

        mPaintThree = new Paint();
        mPaintThree.setColor(0xFFFF0000);
        mPaintThree.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (widthMeasureSpec > mBigRadius*2)
            widthMeasureSpec = mBigRadius*2;
        if (heightMeasureSpec > mBigRadius*2)
            heightMeasureSpec = mBigRadius*2;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);

        mWidth = getWidth()/2;
        mHeight = getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        canvas.translate(mWidth, mHeight);
//        canvas.drawCircle(0,0,mBigRadius, mPaintOne);
//        canvas.drawCircle(0,0,mSmallRadius, mPaintTwo);
//        canvas.drawArc(new RectF(0,0,mBigRadius*2,mBigRadius*2),-90, 90, true, mPaintOne);
//        canvas.drawArc(new RectF(0,0,mBigRadius*2,mBigRadius*2),0, 270, true, mPaintThree);
//        canvas.save();
//        canvas.translate(mWidth, mHeight);
//        canvas.drawCircle(0,0,mSmallRadius, mPaintTwo);
//        canvas.restore();
        String text = "adfasdfwuwenchuan";
        Paint temp = new Paint();
        temp.setTextSize(30);
        temp.setColor(0xFFFF0000);

        canvas.save();
        canvas.rotate(-90.0F, getWidth()/2, getHeight()/2);
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30.0F);
        StaticLayout needdraw = new StaticLayout(text, textPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        if (needdraw.getLineCount() > 1) {
            System.out.println("wuwenchuan Yes, two line!");
        }
        temp.measureText(text);
        needdraw.draw(canvas);
        canvas.drawText(text, 0.0f, 30.0f, temp);
        canvas.restore();
    }

}
