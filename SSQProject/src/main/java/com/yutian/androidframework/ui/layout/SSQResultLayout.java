package com.yutian.androidframework.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.yutian.androidframework.R;
import com.yutian.androidframework.control.ssq.ssqmodel.SSQDataModel;
import com.yutian.androidframework.ui.widgets.CheckButton;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.base.util.DisplayUtil;
import com.yutian.base.util.ssq.SSQUtil;

import java.util.List;

/**
 * Created by wuwenchuan on 2016/10/15.
 */
public class SSQResultLayout extends RelativeLayout {

    private ViewTreeObserver mViewTreeObserver;
    private SSQDataModel mSSQDataModel = null;
    private boolean mInitialize = false;

    private int mWidth;
    private int mHeight;

    private Context mContext;

    //为List分割线添加的对象
    private boolean mNeedSave = false;
    //为UI添加限制Ball的最大大小
    private boolean mLimitSize = false;

    public SSQResultLayout(Context context) {
        this(context, null);
    }

    public SSQResultLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SSQResultLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs, defStyleAttr);
    }

    public void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        mViewTreeObserver = getViewTreeObserver();

        mViewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialize) {
                    mInitialize = true;
                    drawCustomize();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > 0)
            mWidth = getMeasuredWidth();
        if (getMeasuredHeight() > 0)
            mHeight = getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0)
            mWidth = w;
        if (h > 0)
            mHeight = h;
    }

    public void drawCustomize() {
        //Display nothing
        if (mSSQDataModel == null)
            return;

        //Clean UI
        removeAllViews();

        List<String> redBalls = mSSQDataModel.getRedBallList();
        List<String> blueBalls = mSSQDataModel.getBlueBallList();

        //draw objects
        int ballMargin = DisplayUtil.dipToPx(mContext,  Constants.SSQ_BALL_MARGIN);
        int ballsize = SSQUtil.cacluteBallSize(mContext, mWidth, mHeight, redBalls.size() + blueBalls.size()
                , ballMargin);

        if (mLimitSize)
            if (ballsize > Constants.SSQ_MAX_SIZE)
                ballsize = Constants.SSQ_MAX_SIZE;


        int topid = 0;
        int lastid = 0;
        int totalsize = mWidth;

        int redballSize = redBalls.size();
        int blueballSize = blueBalls.size();

        for (int cnt =0; cnt < redballSize; ++cnt) {
            CheckButton mNew = (CheckButton)LayoutInflater.from(mContext).inflate(R.layout.ssq_redball, this, false);
            mNew.setCheckstatus(CheckButton.CHECKED);
            mNew.setText(redBalls.get(cnt));
            mNew.setId(Constants.SSQ_RESULT_START+Integer.parseInt(redBalls.get(cnt)));
            mNew.setEnabled(false);
            mNew.setWidth(ballsize);
            mNew.setHeight(ballsize);

            LayoutParams checkButtonParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (totalsize >= mWidth) {
                checkButtonParams.addRule(RelativeLayout.BELOW, topid);
                checkButtonParams.topMargin = ballMargin;
                topid = mNew.getId();
                lastid = topid;
                totalsize = 0;
            } else {
                checkButtonParams.addRule(RelativeLayout.ALIGN_TOP, topid);
                if (mNew.getId() != topid) {
                    checkButtonParams.addRule(RelativeLayout.RIGHT_OF, lastid);
                    checkButtonParams.leftMargin = ballMargin;
                    lastid = mNew.getId();
                    totalsize += ballMargin;
                }
            }
            totalsize += ballsize;
            addView(mNew, checkButtonParams);
        }

        for (int cnt =0; cnt < blueballSize; ++cnt) {
            CheckButton mNew = (CheckButton)LayoutInflater.from(mContext).inflate(R.layout.ssq_blueball, this, false);
            mNew.setCheckstatus(CheckButton.CHECKED);
            mNew.setText(blueBalls.get(cnt));
            mNew.setId(Constants.SSQ_RESULT_START+Constants.SSQ_REDBALL_COUNT+Integer.parseInt(blueBalls.get(cnt)));
            mNew.setEnabled(false);
            mNew.setWidth(ballsize);
            mNew.setHeight(ballsize);

            LayoutParams checkButtonParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (totalsize >= mWidth) {
                checkButtonParams.addRule(RelativeLayout.BELOW, topid);
                checkButtonParams.topMargin = ballMargin;
                topid = mNew.getId();
                lastid = topid;
                totalsize = 0;
            } else {
                checkButtonParams.addRule(RelativeLayout.ALIGN_TOP, topid);
                if (mNew.getId() != topid) {
                    checkButtonParams.addRule(RelativeLayout.RIGHT_OF, lastid);
                    checkButtonParams.leftMargin = ballMargin;
                    lastid = mNew.getId();
                    totalsize += ballMargin;
                }
            }
            totalsize += ballsize;
            addView(mNew, checkButtonParams);
        }
    }

    public SSQDataModel getmSSQDataModel() {
        return mSSQDataModel;
    }

    public void setmSSQDataModel(SSQDataModel mSSQDataModel) {
        this.mSSQDataModel = mSSQDataModel;
        drawCustomize();
        invalidate();
    }

    public boolean isNeedSave() {
        return mNeedSave;
    }

    public void setNeedSave(boolean mNewAdd) {
        this.mNeedSave = mNewAdd;
    }

    public boolean isLimitSize() {
        return mLimitSize;
    }

    public void setLimitSize(boolean limitSize) {
        this.mLimitSize = limitSize;
    }
}
