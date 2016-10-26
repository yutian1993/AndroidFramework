package com.yutian.androidframework.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yutian.androidframework.R;

/**
 * Created by wuwenchuan on 2016/9/19.
 */
public class UIButton extends Button {

    private GradientDrawable gradientDrawable = null;               //控件的样式

    private String backColors = "";                          //背景色
    private int backColori;
    private String backColorsSelected = "";                  //按下后的背景色
    private int backColoriSelected;

    private int backImagei = 0;                                  //背景图
    private int backImageiSelected = 0;                          //按下后的背景图

    private String textColors = "";                          //文字颜色
    private int textColori;
    private String textColorsSelected = "";                  //按下后的文字颜色
    private int textColoriSelected;

    private float radius = 0;                                //圆角半径
    private int shape = 0;                                   //圆角样式，默认为矩形（矩形0，圆形1）
    private Boolean fillet = false;                          //是否设置为圆角

    //当前控件的表现状态
    private int curBackColori;
    private int curTextColori;
    private int curBackImgi;

    public UIButton(Context context) {
        this(context, null);
    }

    public UIButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //Set Color
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UIButton);
        setBackColori(array.getColor(R.styleable.UIButton_backColor, 0));
        setBackImagei(array.getResourceId(R.styleable.UIButton_backImage, 0));
        setTextColori(array.getColor(R.styleable.UIButton_textColor, 0));
        setBackColoriSelected(array.getColor(R.styleable.UIButton_backColorSelected, 0));
        setBackImageiSelected(array.getResourceId(R.styleable.UIButton_backImageSelected, 0));
        setTextColoriSelected(array.getColor(R.styleable.UIButton_textColorSelected, 0));
        setRadius(array.getFloat(R.styleable.UIButton_radius, 0));
        setShape(array.getInt(R.styleable.UIButton_shap, 0));
        setFillet(array.getBoolean(R.styleable.UIButton_fillset, false));
        array.recycle();

        this.init();
    }

    public void init() {
        //设置默认背景色为透明
        if (fillet) {
            if (gradientDrawable == null)
                gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.TRANSPARENT);
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
        }
        //设置默认文字居中
        this.setGravity(Gravity.CENTER);

        //设置Touch事件
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setStatus(event.getAction());
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    System.out.println("Out me");
                return false;
            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //不添加任何代码，为的是让onTouch事件返回false后依旧可以继续监听
            }
        });
    }

    protected void setStatus(int state) {
        if (state == MotionEvent.ACTION_DOWN) {
            setShowBackColori(backColoriSelected);
            setShowTextColori(textColoriSelected);
            setShowBackImgi(backImageiSelected);

        } else if (state == MotionEvent.ACTION_UP) {
            setShowBackColori(backColori);
            setShowTextColori(textColori);
            setShowBackImgi(backImagei);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //绘制自己的颜色
        if (changed) {
            setShowBackColori(curBackColori);
            setShowTextColori(curTextColori);
            setShowBackImgi(curBackImgi);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("curBackColori", curBackColori);
        bundle.putInt("curTextColori", curTextColori);
        bundle.putInt("curBackImgi", curBackImgi);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            curBackColori = bundle.getInt("curBackColori");
            curTextColori = bundle.getInt("curTextColori");
            curBackImgi = bundle.getInt("curBackImgi");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    /**
     * 设置当前展示的背景色
     * @param showBackColori
     */
    protected void setShowBackColori(int showBackColori) {
        if (fillet) {
            if (gradientDrawable == null)
                gradientDrawable = new GradientDrawable();
            this.gradientDrawable.setColor(showBackColori);
        } else {
            this.setBackgroundColor(showBackColori);
        }

        curBackColori = showBackColori;
    }

    /**
     * 设置当前文本颜色
     * @param showTextColori
     */
    protected void setShowTextColori(int showTextColori) {
        if (showTextColori == 0)
            showTextColori = Color.BLACK;

        setTextColor(showTextColori);

        curTextColori = showTextColori;
    }

    /**
     * 设置当前背景图片
     * @param showBackImgi
     */
    protected  void setShowBackImgi(int showBackImgi) {
        if (showBackImgi != 0)
            setBackgroundResource(showBackImgi);

        curBackImgi = showBackImgi;
    }

    /**
     * 设置按钮的背景色，默认为透明
     *
     * @param backColori
     */
    public void setBackColori(int backColori) {
        this.backColori = backColori == 0 ? Color.TRANSPARENT : backColori;

        setShowBackColori(this.backColori);
    }

    public void setBackColoriSelected(int backColoriSelected) {
        this.backColoriSelected = backColoriSelected == 0 ? Color.TRANSPARENT : backColoriSelected;
    }

    public void setBackColors(String backColors) {
        this.backColors = backColors;

        if (backColors != null && !backColors.isEmpty()) {
            setBackColori(Color.parseColor(backColors));
        } else {
            setBackColori(Color.TRANSPARENT);
        }
    }

    public void setBackColorsSelected(String backColorsSelected) {
        this.backColorsSelected = backColorsSelected;

        if (backColorsSelected != null && !backColorsSelected.isEmpty()) {
            setBackColoriSelected(Color.parseColor(backColorsSelected));
        } else {
            setBackColoriSelected(Color.TRANSPARENT);
        }
    }

    public void setBackImagei(int backImagei) {
        this.backImagei = backImagei;

        if (backImagei != 0) {
            this.setBackgroundResource(backImagei);
            curBackImgi = backImagei;
        }
    }

    public void setBackImageiSelected(int backImageiSelected) {
        this.backImageiSelected = backImageiSelected;
    }

    /**
     * 设置是否为圆角
     *
     * @param fillet
     */
    public void setFillet(Boolean fillet) {
        this.fillet = fillet;

        if (fillet) {
            if (gradientDrawable == null)
                gradientDrawable = new GradientDrawable();

            //设置默认的形状
            gradientDrawable.setShape(shape);
            gradientDrawable.setCornerRadius(radius);

            setBackground(gradientDrawable);
        }
    }

    /**
     * 设置半角大小
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;

        if (gradientDrawable == null)
            gradientDrawable = new GradientDrawable();

        gradientDrawable.setCornerRadius(this.radius);
    }

    /**
     * 设置按钮形状
     *
     * @param shape
     */
    public void setShape(int shape) {
        this.shape = shape;
    }

    /**
     * 设置按钮的文本颜色
     *
     * @param textColori
     */
    public void setTextColori(int textColori) {
        this.textColori = textColori;
        this.setTextColor(textColori);
        this.curTextColori = textColori;
    }

    /**
     * 设置按钮的文本颜色
     *
     * @param textColoriSelected
     */
    public void setTextColoriSelected(int textColoriSelected) {
        this.textColoriSelected = textColoriSelected;
    }

    public void setTextColors(String textColors) {
        this.textColors = textColors;

        if (textColors != null && !textColors.isEmpty())
            setTextColori(Color.parseColor(textColors));
        else
            setTextColori(Color.TRANSPARENT);
    }

    public void setTextColorsSelected(String textColorsSelected) {
        this.textColorsSelected = textColorsSelected;

        if (textColorsSelected != null && !textColorsSelected.isEmpty())
            setTextColoriSelected(Color.parseColor(textColorsSelected));
        else
            setTextColori(Color.BLACK);
    }

    public int getBackColoriSelected() {
        return backColoriSelected;
    }
    public int getTextColoriSelected() {
        return textColoriSelected;
    }
    public int getBackImageiSelected() {
        return backImageiSelected;
    }
    public int getBackColori() {
        return backColori;
    }
    public int getBackImagei() {
        return backImagei;
    }
    public int getTextColori() {
        return textColori;
    }
}
