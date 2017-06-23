package com.yutian.exceptiontext;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wuwenchuan on 2017/3/13.
 */
public class BezierView extends View {
    private Paint mPainter;
    private Path mPath;
    private Point mFinger;
    private int mWidth;
    private int mHeight;
    private ValueAnimator mValueAnimator;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        System.out.println("OnLayout: left: " + left + " top: " + top + " right: " + right + " bottom: " + bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("OnMeasure" + " getWidth: " + getWidth()
                + " getHeight: " + getHeight());
        mWidth = getWidth();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        System.out.println("layout: " + " l: " + l + " t: " + t + " r: " + r + " b: " + b);
    }

    public void initView() {
        mPainter = new Paint();
        mPainter.setStrokeWidth(5);
        mPainter.setStyle(Paint.Style.STROKE);
        mPainter.setColor(0xFFfe626d);
        mPainter.setAntiAlias(true);
        mFinger = new Point(0, 300);
        mPath = new Path();
        setWillNotDraw(false);

//        mValueAnimator = ValueAnimator.ofObject(new FloatEvaluator(3000, 1.0), 1.0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(0, 300);
        mPath.cubicTo(0, 300, mFinger.x, mFinger.y, mWidth, 300);
        canvas.drawPath(mPath, mPainter);
//        mPath.moveTo(100,100);
//        mPath.lineTo(400,400);
//        canvas.drawPath(mPath, mPainter);
//        System.out.println("onDraw");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mFinger.x = (int) event.getX();
                mFinger.y = (int) event.getY();
                result = true;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                result = false;
                break;
        }
        System.out.println("OnTouchEvent");
        invalidate();
        return true;
    }

}
