package com.yutian.androidframework.ui.layout;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuwenchuan on 2016/9/30.
 */
public class SSQSwipLayout extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private int mDragDistance = 0;

    private View mMainView;
    private View mOperateView;
    private Map<View, Rect> mViewRecInfo = new HashMap<>();

    private OperateViewStatus mOperateViewStatus;

    public enum OperateViewStatus {
        CLOSE,
        OPEN,
        OPENING
    }

    public SSQSwipLayout(Context context) {
        this(context, null);
    }

    public SSQSwipLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SSQSwipLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mOperateViewStatus = OperateViewStatus.CLOSE;
        mViewDragHelper = ViewDragHelper.create(this, mDragCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //默认我们只要两个view，一个是展示的view，一个是操作view
        int childCount = getChildCount();
        if (childCount >= 2) {
            mMainView = getChildAt(childCount-1);
            mOperateView = getChildAt(childCount-2);
        } else if (childCount == 1) {

        } else {
            mMainView = null;
            mOperateView = null;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        updateViewStatus();
        updateViewRect();
        openorclose(false, false);
    }

    /**
     * 获取operate view的信息
     */
    public void updateOperateView() {
        if (mOperateView != null) {
            mDragDistance = mOperateView.getMeasuredWidth();
        }
//        if (mOperateViewStatus == OperateViewStatus.CLOSE) {
//            if (mOperateView.getVisibility() != INVISIBLE)
//                mOperateView.setVisibility(INVISIBLE);
//        } else {
//            if (mOperateView.getVisibility() != VISIBLE)
//                mOperateView.setVisibility(VISIBLE);
//        }
//
//        System.out.println("mOperateView.getVisibility---->" + mOperateView.getVisibility());
    }

    /**
     * 确定Mainview和Operateview的位置信息
     */
    public void updateViewRect() {
        Rect mainview = mViewRecInfo.get(mMainView);
        if (mainview != null) {
            if (mOperateViewStatus != OperateViewStatus.CLOSE) {
                mainview.left = getPaddingLeft() - mDragDistance;
            } else {
                mainview.left = getPaddingLeft();
            }
            mainview.top = getPaddingTop();
        } else if (mMainView != null ) {
            mainview = new Rect();
            mainview.left = getPaddingLeft();
            mainview.top = getPaddingTop();
            mViewRecInfo.put(mMainView, mainview);
        }
        if (mainview != null) {
            mainview.right = mainview.left + getMeasuredWidth();
            mainview.bottom = mainview.top + getMeasuredHeight();
        } else {
            return;
        }

        Rect operateview = mViewRecInfo.get(mOperateView);
        if (mOperateView != null) {
            operateview = new Rect();
            mViewRecInfo.put(mOperateView, operateview);
        }
        if (operateview != null) {
            operateview.left = mainview.right;
            operateview.top = mainview.top;
            operateview.bottom = mainview.bottom;
            operateview.right = operateview.left + mDragDistance;
        } else {
            return;
        }
    }

    /**
     * 更新operate view的打开状态
     */
    public void updateViewStatus() {
        if (mMainView == null)
            mOperateViewStatus = OperateViewStatus.CLOSE;

        int mainViewLeft = mMainView.getLeft();

        if (mainViewLeft == getPaddingLeft())
            mOperateViewStatus = OperateViewStatus.CLOSE;
        else if (mainViewLeft == (getPaddingLeft() - mDragDistance))
            mOperateViewStatus = OperateViewStatus.OPEN;
        else
            mOperateViewStatus = OperateViewStatus.OPENING;

        updateOperateView();
    }

    private boolean isDragging = false;
    private Rect mHitMainView = new Rect();

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mViewDragHelper.cancel();
                isDragging = false;
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                updateViewStatus();
                if (mOperateViewStatus != OperateViewStatus.CLOSE)
                    isDragging = true;
                break;
            case MotionEvent.ACTION_DOWN:
                mViewDragHelper.shouldInterceptTouchEvent(ev);
                isDragging = false;
                updateViewStatus();
                if (mOperateViewStatus != OperateViewStatus.CLOSE)
                    isDragging = true;
                break;
            default:
                mViewDragHelper.shouldInterceptTouchEvent(ev);
                break;
        }

        //判断点击的位置是否在mainview中，mainview中的所有事件默认全部拦截
        mMainView.getHitRect(mHitMainView);
        if (mHitMainView.contains((int)ev.getX(), (int)ev.getY()))
            return true;
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return getPaddingTop();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > getPaddingLeft()) return getPaddingLeft();
            if (left < getPaddingLeft() - mDragDistance)
                return getPaddingLeft() - mDragDistance;
            return left;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mMainView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDragDistance;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            processHandleRelease(xvel, yvel, true);
            //重绘界面
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mMainView) {
                mOperateView.offsetLeftAndRight(dx);
            }

            //重绘界面
            invalidate();
        }
    };

    private float mWillOpenPercentAfterOpen=0.75f;
    private float mWillOpenPercentAfterClose=0.25f;
    public void processHandleRelease(float xvel, float yvel, boolean isCloseBeforeDragged) {
        float minVelocity = mViewDragHelper.getMinVelocity();
        float willOpenPercent = (isCloseBeforeDragged ? mWillOpenPercentAfterClose : mWillOpenPercentAfterOpen);

        if (xvel > minVelocity)
            openorclose(true, false);
        else if (xvel < -minVelocity)
            openorclose(true, true);
        else {
            float openPercent = 1f * (-mMainView.getLeft()) / mDragDistance;
            if (openPercent > willOpenPercent)
                openorclose(true, true);
            else
                openorclose(true, false);
        }
    }

    /**
     * 是否打开operate view
     * @param smooth 是否平滑打开
     */
    public void openorclose(boolean smooth, Boolean open) {
        if (mMainView == null || mOperateView == null)
            return;

        if (open == null)
            updateViewStatus();
        else {
            if (open) {
                mOperateViewStatus = OperateViewStatus.OPEN;
            }
            else {
                mOperateViewStatus = OperateViewStatus.CLOSE;
            }
        }
        updateViewRect();
        Rect rect = mViewRecInfo.get(mMainView);
        if (smooth) {
            if (mViewDragHelper.smoothSlideViewTo(mMainView, rect.left, rect.top)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mMainView.layout(rect.left, rect.top, rect.right, rect.bottom);
            rect = mViewRecInfo.get(mOperateView);
            mOperateView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
        invalidate();
    }

}
