package com.yutian.androidframework.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yutian.androidframework.R;
import com.yutian.androidframework.ui.layout.SSQLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class SSQChooseFragment extends Fragment  {

    private SSQLayout mSsqBalls;
    private Fragment mSsqOperator;

    public SSQChooseFragment() {
        mSsqOperator = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mMainView = inflater.inflate(R.layout.fragment_ssqchoose, container, false);

        mSsqBalls = (SSQLayout)mMainView.findViewById(R.id.ssq_balls);


        return mMainView;
    }

    public void resetCurrentChoose() {
        getFragmentManager().findFragmentById(R.id.fragment_numberchoose);
    }
}
