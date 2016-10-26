package com.yutian.androidframework.ui.layout;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.yutian.androidframework.R;
import com.yutian.androidframework.control.ssq.ssqmodel.SSQDataModel;
import com.yutian.androidframework.ui.widgets.CheckButton;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.base.util.DisplayUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Created by wuwenchuan on 2016/9/23.
 */
public class SSQLayout extends RelativeLayout {
    //Self data
    private SSQDataModel mSSQDataModel = null;

    //System information
    private Context mContext;
    private ViewTreeObserver mViewTreeobserver;
    private int mWidth;
    private int mHeight;
    private boolean mInitialized = false;

    //Save information
    private Map<Integer, CheckButton> mAllBalls = new TreeMap<>();

    public SSQLayout(Context context) {
        this(context, null);
    }

    public SSQLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SSQLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        mViewTreeobserver = getViewTreeObserver();

        //当布局可视化后开始绘制
        mViewTreeobserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mInitialized) {
                    mInitialized = true;
                    //Add redraw
                    drawCustomize();
                }
            }
        });

        //自定义控件属性
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
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
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    /**
     * 构建Layout内容
     */
    private void drawCustomize() {
        if (!mInitialized)
            return;

        //remove all children
        removeAllViews();

//        float total = getPaddingLeft() + getPaddingRight();

        int listIndex;    // List Index
        int index_bottom=0;   // The Tag to add below
        int index_header=0;   // The header tag of this line

        /**
         * custom layout param
         */
        BallLayoutInformation ballLayoutInformation = new BallLayoutInformation();
        ballLayoutInformation.cacluteBallLineInformation(mWidth, mHeight);

        int lineButton = 0;
        //Draw 33 red balls
        for (int cnt = 1; cnt <= Constants.SSQ_REDBALL_COUNT; ++cnt) {
            listIndex = cnt;

            CheckButton buttonLayout = (CheckButton) LayoutInflater.from(mContext).inflate(R.layout.ssq_redball, this, false);
            buttonLayout.setId(cnt);
            mAllBalls.put(cnt, buttonLayout);

            CheckButton checkButton = buttonLayout; //(CheckButton)buttonLayout.findViewById(R.id.m_ssqball);
            checkButton.setText(cnt >= 10 ? cnt+"" : "0" + cnt);
            checkButton.setWidth(ballLayoutInformation.ballSize);
            checkButton.setHeight(ballLayoutInformation.ballSize);

            LayoutParams checkButtonParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // 是否需要换行
            if (lineButton == 0) {        //new line
                checkButtonParams.addRule(RelativeLayout.BELOW, index_header);
//                total = DisplayUtil.dipToPx(mContext, getPaddingLeft()) ;
                checkButtonParams.topMargin = ballLayoutInformation.ballMargin;
                index_bottom = listIndex;
                index_header = listIndex;
                lineButton = ballLayoutInformation.redBallLineCnt;
            } else {
                checkButtonParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                if (listIndex != index_header) {
                    checkButtonParams.addRule(RelativeLayout.RIGHT_OF, listIndex - 1);
                    checkButtonParams.leftMargin = ballLayoutInformation.ballMargin;
//                    total += ballLayoutInformation.ballMargin;
                }
            }

            --lineButton;

//            total += ballLayoutInformation.ballSize;
            addView(buttonLayout, checkButtonParams);
        }

        //add split line
        int splitLineID = Constants.SSQ_REDBALL_COUNT + Constants.SSQ_BLUEBALL_COUNT + 1;
        {
            View splitine = LayoutInflater.from(mContext).inflate(R.layout.ssq_splitline, this, false);
            splitine.setId(splitLineID);
            LayoutParams splitLineParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            splitLineParams.width = DisplayUtil.dipToPx(mContext, Constants.SSQ_SPLITLINE_WIDTH);
            splitLineParams.height = ballLayoutInformation.ballLines * (ballLayoutInformation.ballSize + ballLayoutInformation.ballMargin) - ballLayoutInformation.ballMargin;
            splitLineParams.leftMargin = ballLayoutInformation.ballSize/2;
            splitLineParams.rightMargin = ballLayoutInformation.ballSize/2;
            splitLineParams.addRule(RelativeLayout.RIGHT_OF, ballLayoutInformation.redBallLineCnt);
            addView(splitine, splitLineParams);
        }

        //add 16 blue balls
        index_bottom = Constants.SSQ_REDBALL_COUNT + 1;
        index_header = 0;
        lineButton = 0;
        for (int cnt =1; cnt <= Constants.SSQ_BLUEBALL_COUNT; ++cnt) {
            listIndex = Constants.SSQ_REDBALL_COUNT + cnt;

            CheckButton checkButton = (CheckButton) LayoutInflater.from(mContext).inflate(R.layout.ssq_blueball, this, false);
            checkButton.setId(listIndex);
            mAllBalls.put(listIndex, checkButton);
            checkButton.setText(cnt < 10 ? "0" + cnt : "" + cnt);
            checkButton.setWidth(ballLayoutInformation.ballSize);
            checkButton.setHeight(ballLayoutInformation.ballSize);

            LayoutParams checkButtonParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (lineButton == 0) {      //new line
                checkButtonParams.addRule(RelativeLayout.BELOW, index_header);
                checkButtonParams.addRule(RelativeLayout.RIGHT_OF, splitLineID);
//                total = DisplayUtil.dipToPx(mContext, getPaddingLeft());
                checkButtonParams.topMargin = ballLayoutInformation.ballMargin;
//                index_bottom = listIndex;
                index_header = listIndex;

                lineButton = ballLayoutInformation.blueBallLineCnt;
            } else {
                checkButtonParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
                if (index_header == listIndex) {
                    checkButtonParams.addRule(RelativeLayout.RIGHT_OF, splitLineID);
                } else {
                    checkButtonParams.addRule(RelativeLayout.RIGHT_OF, listIndex-1);
                    checkButtonParams.leftMargin = ballLayoutInformation.ballMargin;
                }
//                total += ballLayoutInformation.ballMargin;
            }

            --lineButton;

//            total += ballLayoutInformation.ballSize;
            addView(checkButton, checkButtonParams);
        }
    }

    /**
     * 获取布局结构信息
     */
    public class BallLayoutInformation {
        public int ballLines;
        public int redBallLineCnt;
        public int blueBallLineCnt;

        public int ballSize;
        public int ballMargin;

        /**
         * 计算显示所有球体的布局
         * @param width 界面的宽度
         * @param height 界面的高度
         */
        public void cacluteBallLineInformation(int width, int height) {
            ballLines = width < height ? Constants.HORIZONTAL_LINES : Constants.VERTICAL_LINES;

            //最新大小对比
            CheckButton checkButton = (CheckButton) LayoutInflater.from(mContext).inflate(R.layout.ssq_redball, null, false);
            checkButton.setText("00");
            int minBallSize = (int)DisplayUtil.getTextPxSize(checkButton, checkButton.getText().toString()) + DisplayUtil.dipToPx(mContext, Constants.SSQ_BALL_MARGIN);

            //caclute ball information
            redBallLineCnt = Constants.SSQ_REDBALL_COUNT / ballLines + (Constants.SSQ_REDBALL_COUNT % ballLines > 0 ? 1 : 0);
            blueBallLineCnt= Constants.SSQ_BLUEBALL_COUNT / ballLines + (Constants.SSQ_BLUEBALL_COUNT % ballLines > 0 ? 1 : 0);
            float padding = (redBallLineCnt + blueBallLineCnt + 2 ) * (Constants.SSQ_BALL_MARGIN ) * 2;
            ballSize = ((int)(width - padding) / (redBallLineCnt + blueBallLineCnt + 1));
            ballMargin = DisplayUtil.dipToPx(mContext, Constants.SSQ_BALL_MARGIN);

            //空间太小，造成挤压变形重新分配display空间
            if (ballSize < minBallSize) {
                ballSize = minBallSize;

                //只分析width，height在此分析没有意义，因为height需要配合content
                ballMargin = DisplayUtil.dipToPx(mContext, Constants.SSQ_BALL_MARGIN);

                int contentCount = width/(ballSize+ballMargin);
                float maxLine = (float)(Constants.SSQ_REDBALL_COUNT + Constants.SSQ_BLUEBALL_COUNT)/(contentCount-1);
                float minLine = (float)(Constants.SSQ_REDBALL_COUNT + Constants.SSQ_BLUEBALL_COUNT)/(contentCount+1);
                ballLines = (int)(minLine + maxLine)/2 + ((minLine + maxLine)%2 > 0 ? 1 : 0);
                redBallLineCnt = Constants.SSQ_REDBALL_COUNT / ballLines + (Constants.SSQ_REDBALL_COUNT % ballLines > 0 ? 1 : 0);
                blueBallLineCnt= Constants.SSQ_BLUEBALL_COUNT / ballLines + (Constants.SSQ_BLUEBALL_COUNT % ballLines > 0 ? 1 : 0);

                //重新构造ballSize，此时计算出的ballsize只会大于等于minBallSize
                padding = (redBallLineCnt + blueBallLineCnt + 2 ) * (Constants.SSQ_BALL_MARGIN ) * 2;
                ballSize = ((int)(width - padding) / (redBallLineCnt + blueBallLineCnt + 1));
            }
        }
    }

    public SSQDataModel getSSQDataModel() {
        return getSelectSSQ();
    }

    /**
     * 设置当期界面SSQ的选择
     * @param mSSQDataModel
     */
    public void setSSQDataModel(SSQDataModel mSSQDataModel) {
        this.mSSQDataModel = mSSQDataModel;

        resetAllBalls();
        updateUIBalls();

    }

    /**
     * 重置每个child的高亮状态，恢复到默认状态
     */
    public void resetAllBalls() {
        Iterator<Integer> iterator = mAllBalls.keySet().iterator();
        while (iterator.hasNext()) {
            mAllBalls.get(iterator.next()).setCheckstatus(CheckButton.NORMAL);
        }
    }

    /**
     * 根据mSSQDataModel的内容更新界面
     */
    public void updateUIBalls() {
        //设置各个child的高亮状态
        for (String redball:mSSQDataModel.getRedBallList()) {
            int childID = Integer.parseInt(redball);
            mAllBalls.get(childID).setCheckstatus(CheckButton.CHECKED);
        }

        //设置各个child的高亮状态
        for (String blueball:mSSQDataModel.getBlueBallList()) {
            int childID = Integer.parseInt(blueball) + Constants.SSQ_REDBALL_COUNT;
            mAllBalls.get(childID).setCheckstatus(CheckButton.CHECKED);
        }

        invalidate();
    }

    /**
     * 获取用户选择的数据
     * @return
     */
    public SSQDataModel getSelectSSQ() {
        CheckButton temp = null;
        List<String> checkredball = new ArrayList<>();
        List<String> checkblueball = new ArrayList<>();
//        for (int cnt = 1; cnt <= this.getChildCount(); ++cnt) {
//            temp = (CheckButton) this.getChildAt(1);
//            if (temp.isChecked()) {
//                if (temp.getId() > Constants.SSQ_REDBALL_COUNT) {   //This is blue ball
//                    checkblueball.add(temp.getText().toString());
//                } else {
//                    checkredball.add(temp.getText().toString());
//                }
//            }
//        }
        Iterator<Integer> index = mAllBalls.keySet().iterator();
        while (index.hasNext()) {
            temp = mAllBalls.get(index.next());
            if (temp.isChecked()) {
                if (temp.getId() > Constants.SSQ_REDBALL_COUNT) {   //This is blue ball
                    checkblueball.add(temp.getText().toString());
                } else {
                    checkredball.add(temp.getText().toString());
                }
            }
        }

        if (mSSQDataModel == null) {
            mSSQDataModel = new SSQDataModel();
        }

        mSSQDataModel.setBlueBallList(checkblueball);
        mSSQDataModel.setRedBallList(checkredball);

        return mSSQDataModel;
    }
}
