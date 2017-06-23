package com.yutian.exceptiontext.BezierCircle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wuwenchuan on 2017/3/13.
 */
public class CustomView extends View {
    BezierCircle mBezierview;
    Paint mPaint;
    Path mPath;
    int extra;

    private Canvas mBufferCanvas;
    private Bitmap mBufferBitmap;
    private boolean mBufferReady;

    //
    ValueAnimator mValueAnimator;
    float mLastOffset;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Handler mViewShow = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            count = count + 5f;
            if (msg.what == 1) {
                mViewShow.postDelayed(mRunnable, 500);
                invalidate();
            }
        }
    };

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            mBufferBitmap.eraseColor(0x00000000);
//            mBufferCanvas.save();
//            if (getWidth()/2 < count || getHeight()/2 < count)
//                count = 0;
//            mBufferCanvas.translate(getWidth()/2+count, getHeight()/2+count);
//            mBufferCanvas.drawPath(mBezierview.regenerateNewPath(), mPaint);
//            mBufferCanvas.restore();
//            mBufferReady = true;
//            invalidate();
//            mViewShow.sendEmptyMessage(1);
        }
    };

    public void init() {
        mBezierview = new BezierCircle();
        mBezierview.radius = 50;
//        mBezierview.radius = 300;
//        extra = (int)mBezierview.radius*2/5;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(0xFFFF0000);
        mPaint.setAntiAlias(true);
        mBezierview.regeneratePoint();
//        mPath = mBezierview.regenerateNewPath();
        extra = (int)(mBezierview.radius * 2 / 5);
        mLastOffset = 0;

        initBuffer();

        mValueAnimator = ValueAnimator.ofObject(new FloatValEvaluator(2500, 0.8f, 0), 0.8f,0);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                System.out.println((float)animation.getAnimatedValue());
                {
                    Object obj = animation.getAnimatedValue();
                    mLastOffset =  extra * (float)obj;
                    mBezierview.mCorePoints[2].updatePoint(mBezierview.radius + mLastOffset,0);
                    mBezierview.mCorePoints[1].updatePoint(0, mBezierview.radius - mLastOffset);
                    mBezierview.mCorePoints[3].updatePoint(0, -mBezierview.radius + mLastOffset);
                }
                {
                    mBufferBitmap.eraseColor(0x00000000);
                    mBufferCanvas.save();
                    mBufferCanvas.translate(getWidth()/2, getHeight()/2);
                    Path temp = mBezierview.regenerateNewPath();
                    mBufferCanvas.drawPath(temp, mPaint);
                    mBufferCanvas.restore();
                    mBufferReady = true;
                    temp.close();
                }
                invalidate();
//                try {
//                    Thread.sleep(400);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        mValueAnimator.setDuration(2500);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(100);
    }

    public void startAnimator() {
        mValueAnimator.start();
    }

    public void initBuffer() {
        mBufferCanvas = new Canvas();
        mBufferReady = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mBufferBitmap != null)
            mBufferBitmap.recycle();
        mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mBufferCanvas.setBitmap(mBufferBitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBufferReady)
            canvas.drawBitmap(mBufferBitmap, 0, 0, null);
//        canvas.translate(getWidth() / 2, getHeight() / 2);
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawPoints(mBezierview.regeneratePoints(), mPaint);
    }
}
