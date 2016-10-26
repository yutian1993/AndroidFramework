package com.yutian.androidframework.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yutian.androidframework.R;

/**
 * Created by wuwenchuan on 2016/10/11.
 */
public class NumberChooseFragement extends Fragment implements View.OnClickListener {

    private TextView mNumberChoose;
    private int mCurrentChoose = 1;
    private static View mNumberMinus;

    /**
     * 双击注数的时候显示Edit dialog
     */
    private View mDialogView;
    private EditText mEditText;
    private LayoutInflater mLayoutInflate;
    private ViewGroup mContainer;

    public NumberChooseFragement() {
        //Do nothing
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflate = inflater;
        mContainer = container;

        View mMainView = mLayoutInflate.inflate(R.layout.numberchoose, container, false);
        mNumberMinus = mMainView.findViewById(R.id.m_numberminus);
        mNumberMinus.setOnClickListener(this);
        mMainView.findViewById(R.id.m_numberplus).setOnClickListener(this);
        mNumberChoose = (TextView) mMainView.findViewById(R.id.m_numberchoose);
        mNumberChoose.setText(Integer.toString(mCurrentChoose));
        mNumberChoose.setOnClickListener(this);
        checkEnable();

        return mMainView;
    }

    /**
     * Dialog初始化
     */
    protected void initDialogView() {
        mDialogView = mLayoutInflate.inflate(R.layout.dialog_ssq_writenumber, mContainer, false);
        mEditText = (EditText) mDialogView.findViewById(R.id.m_numberedit);
    }

    /**
     * 设置输入Dialog
     */
    protected void showDialog() {
        initDialogView();
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(mLayoutInflate.getContext());
        mAlertDialog.setTitle(R.string.ssq_dialog_numberchoose_title);
        mAlertDialog.setIcon(R.drawable.ssq_dialog);
        mAlertDialog.setPositiveButton(R.string.ssq_dialog_numberchoose_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mEditText != null) {
                    setCurrentChoose(Integer.parseInt(mEditText.getText().toString()));
                    onResume();
//                    onActivityResult();
                }
            }
        });
        mAlertDialog.setView(mDialogView);
        mAlertDialog.show();
    }

    @Nullable
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_numberminus:
                --mCurrentChoose;
                break;
            case R.id.m_numberplus:
                ++mCurrentChoose;
                break;
            case R.id.m_numberchoose:
                showDialog();
                return;
            default:
                break;
        }
        mNumberChoose.setText(Integer.toString(mCurrentChoose));
        checkEnable();
    }

    /**
     * 检测减少按钮是否可用
     */
    protected void checkEnable() {
        if (mCurrentChoose == 1)
            mNumberMinus.setEnabled(false);
        else if (!mNumberMinus.isEnabled())
            mNumberMinus.setEnabled(true);
    }

    /**
     * 重置当前选择项
     */
    public void resetCurrentChoose() {
        setCurrentChoose(1);
    }

    public int getCurrentChoose() {
        return mCurrentChoose;
    }

    public void setCurrentChoose(int mCurrentChoose) {
        if (mCurrentChoose <= 0)
            mCurrentChoose = 1;
        this.mCurrentChoose = mCurrentChoose;
        mNumberChoose.setText(Integer.toString(mCurrentChoose));
        checkEnable();
    }
}
