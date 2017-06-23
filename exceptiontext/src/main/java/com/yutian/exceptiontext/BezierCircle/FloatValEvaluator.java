package com.yutian.exceptiontext.BezierCircle;

import android.animation.TypeEvaluator;

/**
 * Created by wuwenchuan on 2017/3/14.
 */
public class FloatValEvaluator implements TypeEvaluator{

    int mSum;
    float mValues[];
    float expRatio = 5.0f;
    float cosRatio = 30.0f;

    public FloatValEvaluator(float time, float startValue, float endValue) {
        mSum = (int)time * 60 / 1000;
        mValues = new float[mSum];
        float diff = endValue - startValue;
        float x = 0;
        for (int index = 0; index < mSum; ++index) {
            x = (index*1.0f)/mSum;
            mValues[index] = endValue -  (float)(diff * Math.pow(Math.E, -1*expRatio*x) * Math.cos(cosRatio*x));
        }
    }

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        return mValues[(int)((mSum-1)*fraction)];
    }
}
