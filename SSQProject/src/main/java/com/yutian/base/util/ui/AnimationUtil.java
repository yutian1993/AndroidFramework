package com.yutian.base.util.ui;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;

import com.yutian.androidframework.R;
import com.yutian.base.util.DisplayUtil;

import java.security.KeyFactory;

/**
 * Created by yutian on 2016/10/13.
 */

public class AnimationUtil {

    public static void alertview(View view) {
        ObjectAnimator animator = scaleAndRotation(view, 1f);
        animator.setRepeatCount(0);
        animator.start();
    }

    public static ObjectAnimator scaleAndRotation(View view, float shakeFactor) {
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, .1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f,1.1f),
                Keyframe.ofFloat(.4f,1.1f),
                Keyframe.ofFloat(.5f,1.1f),
                Keyframe.ofFloat(.6f,1.1f),
                Keyframe.ofFloat(.7f,1.1f),
                Keyframe.ofFloat(.8f,1.1f),
                Keyframe.ofFloat(.9f,1.1f),
                Keyframe.ofFloat(1f,1f));

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, .1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f,1.1f),
                Keyframe.ofFloat(.4f,1.1f),
                Keyframe.ofFloat(.5f,1.1f),
                Keyframe.ofFloat(.6f,1.1f),
                Keyframe.ofFloat(.7f,1.1f),
                Keyframe.ofFloat(.8f,1.1f),
                Keyframe.ofFloat(.9f,1.1f),
                Keyframe.ofFloat(1f,1f));

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0));

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(1000);
    }

    public static ObjectAnimator nope(View view, int delta) {

        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).
                setDuration(500);
    }
}
