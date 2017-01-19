package com.yutian.exceptiontext;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.m_imageviewtest)
    PictureView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.chara_fujii_ph));
    }

    @OnClick(R.id.m_clickException)
    public void click() {
//        int divide = 0;
//        int mDivide = 1;
//
//        for (int i = 0; i < 10 ; ++i) {
//            System.out.println(mDivide/divide);
//        }
        Matrix matrix = mImageView.getImageMatrix();
//        RectF drawableRect = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
//        RectF viewRect = new RectF(0, 0, (float)(mImageView.getWidth()*2), (float)(mImageView.getHeight()*2));
//        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
        matrix.postTranslate(12, 12);
        mImageView.setImageMatrix(matrix);
        mImageView.invalidate();
        Log.e("wuwenchuan", "Click");
    }

    @OnClick(R.id.m_clickCatchException)
    public void catchException() {
        int divide = 0;
        int mDivide = 1;

        try {
            for (int i = 0; i < 10 ; ++i) {
                System.out.println(mDivide/divide);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
