package com.yutian.androidframework.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yutian.androidframework.R;

/**
 * Created by wuwenchuan on 2016/9/20.
 */
public class CheckButton extends UIButton {

    public static final int NORMAL = 0;
    public static final int CHECKED = 1;

    private int checkstatus = 0;                    //按钮的状态

    private int checkBackColori;
    private String checkBackColors;                     //check后的按钮状态

    private int checkImagei;                            //设置选中后的背景图片

    private int checkTextColori;
    private String checkTextColors;                     //check后的文字颜色

    public CheckButton(Context context) {
        this(context, null);
    }

    public CheckButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //Set Color
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckButton);
        setCheckBackColori(array.getColor(R.styleable.CheckButton_checkBackColor, 0));
        setCheckTextColori(array.getColor(R.styleable.CheckButton_checkTextColor, 0));
        setCheckImagei(array.getResourceId(R.styleable.CheckButton_checkImage, 0));
        array.recycle();
    }

    /**
     * 设置按钮的check后的背景色
     *
     * @param checkBackColori
     */
    public void setCheckBackColori(int checkBackColori) {
        this.checkBackColori = checkBackColori == 0 ? Color.RED : checkBackColori;
    }

    public void setCheckBackColors(String checkBackColors) {
        this.checkBackColors = checkBackColors;

        if (checkBackColors != null && !checkBackColors.isEmpty())
            setCheckBackColori(Color.parseColor(checkBackColors));
        else
            setCheckBackColori(0);
    }

    @Override
    protected void setStatus(int state) {
        if (checkstatus != NORMAL) {
            super.setStatus(state);

            if (state == MotionEvent.ACTION_UP)
                checkstatus = NORMAL;
        } else {
            if (state == MotionEvent.ACTION_DOWN) {
                setShowBackColori(getBackColoriSelected());
                setShowTextColori(getTextColoriSelected());
                setShowBackImgi(getBackImageiSelected());

            } else if (state == MotionEvent.ACTION_UP) {
                setShowBackColori(checkBackColori);
                setShowTextColori(checkTextColori);
                setShowBackImgi(checkImagei);

                checkstatus = CHECKED;
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            checkstatus = bundle.getInt("checkstatus");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
        } else
            super.onRestoreInstanceState(state);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superParcelable = super.onSaveInstanceState();
        if (superParcelable instanceof Bundle) {
            Bundle bundle = (Bundle) superParcelable;
            bundle.putParcelable("instanceState", super.onSaveInstanceState());
            bundle.putInt("checkstatus", checkstatus);
            return bundle;
        }
        return superParcelable;
    }

    /**
     * 设置当前按钮的状态并重新绘制按钮
     *
     * @param checkstatus
     */
    public void setCheckstatus(int checkstatus) {
        this.checkstatus = checkstatus;

        //update UI
        if (checkstatus == NORMAL) {
            setShowBackColori(getBackColori());
            setShowTextColori(getTextColori());
            setShowBackImgi(getBackImagei());
        } else {
            setShowBackColori(checkBackColori);
            setShowTextColori(checkTextColori);
            if (checkImagei != 0)
                setBackgroundResource(checkImagei);
        }
    }

    /**
     * 设置check后的文字颜色
     */
    public void setCheckTextColori(int checkTextColori) {
        this.checkTextColori = checkTextColori == 0 ? Color.WHITE : checkTextColori;
    }

    public void setCheckTextColors(String checkTextColors) {
        this.checkTextColors = checkTextColors;

        if (checkTextColors != null && !checkTextColors.isEmpty())
            setCheckTextColori(Color.parseColor(checkTextColors));
        else
            setCheckTextColori(0);
    }

    public void setCheckImagei(int checkImagei) {
        this.checkImagei = checkImagei;
    }

    //Overwrite parent method
    @Override
    public void setBackColori(int backColori) {
        super.setBackColori(backColori);
        this.checkBackColori = getBackColori();
    }

    @Override
    public void setBackImagei(int backImagei) {
        super.setBackImagei(backImagei);
        this.checkImagei = getBackImagei();
    }

    @Override
    public void setTextColori(int textColori) {
        super.setTextColori(textColori);
        this.checkTextColori = getTextColori();
    }

    public boolean isChecked() {
        return checkstatus == CHECKED ? true : false;
    }
}
