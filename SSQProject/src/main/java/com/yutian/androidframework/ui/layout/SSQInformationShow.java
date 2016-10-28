package com.yutian.androidframework.ui.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutian.androidframework.R;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.base.util.DisplayUtil;

/**
 * Created by wuwenchuan on 2016/10/28.
 */
public class SSQInformationShow extends RelativeLayout implements View.OnClickListener {

    private TextView mTitleShow;
    private TextView mTextShow;

    //System information
    private Context mContext;
    private ViewTreeObserver mViewTreeobserver;
    private int mWidth;
    private int mHeight;
    private float mSameWidth;
    private int mPadding;
    private boolean mInitialized = false;

    //Click event
    private OnClickEvent mClickEvent;

    public interface OnClickEvent {
        public void onClickEvent(SSQInformationShow view);
    }

    public SSQInformationShow(Context context) {
        this(context, null);
    }

    public SSQInformationShow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SSQInformationShow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        mTitleShow = new TextView(mContext);
        mTextShow = new TextView(mContext);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SSQInformationShow);
        mTitleShow.setBackgroundResource(typedArray.getResourceId(R.styleable.SSQInformationShow_titlestyle, R.drawable.infor_text_titile));
        mTitleShow.setText(typedArray.getString(R.styleable.SSQInformationShow_titleshow));
        mTitleShow.setTextSize(typedArray.getDimension(R.styleable.SSQInformationShow_textfontsize, DisplayUtil.spToPx(mContext, Constants.INFORMATION_TEXT_FONT)));
        mTitleShow.setTextColor(typedArray.getColor(R.styleable.SSQInformationShow_titlecolor, Color.parseColor("#FFFFFF")));
        mTitleShow.setGravity(Gravity.CENTER);

        mTextShow.setBackgroundResource(typedArray.getResourceId(R.styleable.SSQInformationShow_textstyle, R.drawable.infor_text_show));
        mTextShow.setText(typedArray.getString(R.styleable.SSQInformationShow_textshow));
        mTextShow.setTextSize(typedArray.getDimension(R.styleable.SSQInformationShow_textfontsize, DisplayUtil.spToPx(mContext, Constants.INFORMATION_TEXT_FONT)));
        mTextShow.setTextColor(typedArray.getColor(R.styleable.SSQInformationShow_textcolor, Color.parseColor("#000000")));
        mTextShow.setGravity(Gravity.CENTER);

        typedArray.recycle();

        mPadding = DisplayUtil.dipToPx(mContext, Constants.INFORMATION_PADDUBG_LEFT);

        mTitleShow.setOnClickListener(this);
        mTextShow.setOnClickListener(this);

        mViewTreeobserver = getViewTreeObserver();
        mViewTreeobserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    drawCustomize();
                    mInitialized = true;
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
        removeAllViews();

        mTitleShow.setId(Constants.INFORMATION_ID);

        mTitleShow.setPadding(mPadding, mPadding/2, mPadding, mPadding/2);
        LayoutParams titleShowParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleShowParams.leftMargin = DisplayUtil.dipToPx(mContext, Constants.SSQ_BALL_MARGIN);
        addView(mTitleShow, titleShowParams);
        mSameWidth = DisplayUtil.getTextPxSize(mTitleShow, mTitleShow.getText()) + 2 * mPadding;

        LayoutParams textShowParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textShowParams.addRule(RelativeLayout.END_OF, Constants.INFORMATION_ID);
//        textShowParams.leftMargin = DisplayUtil.dipToPx(mContext, Constants.SSQ_BALL_MARGIN);
        mTextShow.setPadding(mPadding, mPadding/2, mPadding, mPadding/2);
        addView(mTextShow, textShowParams);
        float temp = DisplayUtil.getTextPxSize(mTextShow, mTextShow.getText()) + 2 * mPadding;

        //格式化UI
        if (mSameWidth > temp) {
            if (mTextShow.getText().toString().isEmpty()) {
                mTextShow.setWidth((int) mSameWidth);
            } else {
                if (mSameWidth / 2 > temp)
                    mTextShow.setWidth((int) mSameWidth / 2);
                else
                    mTextShow.setWidth((int) mSameWidth);
            }
        } else {
            mTitleShow.setWidth((int) temp);
        }
    }

    @Override
    public void onClick(View v) {
        if (mClickEvent == null)
            return;
        mClickEvent.onClickEvent(this);
    }

    public String getTitleText() {
        return mTitleShow.getText().toString();
    }

    public String getTextShow() {
        return mTextShow.getText().toString();
    }

    public void setTitleText(String title) {
        mTitleShow.setText(title);
        drawCustomize();
    }

    public void setShowText(String text) {
        mTextShow.setText(text);
        drawCustomize();
    }

    public void setTitleSize(float size) {
        mTitleShow.setTextSize(size);
        drawCustomize();
    }

    public void setTextShowSize(float size) {
        mTitleShow.setTextSize(size);
        drawCustomize();
    }

}
