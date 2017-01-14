package com.yutian.mtkviewer.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yutian.mtkviewer.R;

//import com.yutian.androidframework.ui.layout.SSQResultLayout;
//import com.yutian.androidframework.ui.layout.SSQSwipLayout;

/**
 * 设置线条分割线
 * Created by wuwenchuan on 2016/10/27.
 */
public class LineDecoration extends RecyclerView.ItemDecoration {

    private static final int[] mAttrs = new int[]{android.R.attr.listDivider};
    private Drawable mDrawable;
    private Drawable mSaveDrawable;
    private Drawable mShowDrawable;
    private int mOrientation;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public LineDecoration(Context context, int orientation) {
//        TypedArray typedArray = context.obtainStyledAttributes(mAttrs);
//        mDrawable = typedArray.getDrawable(0);
//        typedArray.recycle();
        mDrawable = ContextCompat.getDrawable(context, R.drawable.listshow_devider);
//        mSaveDrawable = ContextCompat.getDrawable(context, R.drawable.ssq_listsave_devider);
//        mShowDrawable = ContextCompat.getDrawable(context, R.drawable.ssq_listshow_devider);
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c, parent);
    }

    /**
     * 画水平方向的的分割线（当orientation为 Vertical 时）
     *
     * @param c      canvas
     * @param parent recycleview
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        /*
            当orientation为 Vertical 时，Item的分割线为多条水平的条形
            所以，分割线的Left和Right就比较容易确定
            Left = parent.left = parent.paddingLeft
            right = parent.getWidth() - parent.getPaddingRight
            分割线的 Top 和 Bottom 则需要计算出有多少个Item
            根据Item的位置获取到child的位置坐标
            所以分割线的Top = child的下边的坐标 + child的外边距的距离
            top = child.getBottom() + parms.bottomMargin
            然后根据上边 + 分割线的高度 得到右边的坐标
            bottom = top + mDivider.getIntrinsicHeight()
            为了统一分割线的间隔，故共同使用Height的数值作为间隔的距离
         */
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams parms = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + parms.bottomMargin;
            final int bottom = top + mDrawable.getIntrinsicHeight();


//            if (child instanceof SSQSwipLayout) {
//                if (((SSQResultLayout)child.findViewById(R.id.m_ssq_item_result)).isNeedSave()) {
//                    mSaveDrawable.setBounds(left, top, right, bottom);
//                    mSaveDrawable.draw(c);
//                } else {
//                    mShowDrawable.setBounds(left, top, right, bottom);
//                    mShowDrawable.draw(c);
//                }
//            } else {
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
//            }
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    /*
     *  outRect.set(left, top, right, bottom);
     *  在Item的四周设定距离
     *  所以当Orientation为垂直时，我们只需要在每个Item的下方预留出分割线的高度就可以了
     *  同理当Orientation为水平时，我们只需要在每个Item的右方预留出分割线的宽度就可以了
     *  但通常我们使用分割线的style都是统一的，这样我们在attrs中只需要定义一个即可，即共同使用Height
     */
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDrawable.getIntrinsicHeight(), 0);
        }

    }

    private void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }
}
