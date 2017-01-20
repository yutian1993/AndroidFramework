package com.yutian.mtkviewer.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.yutian.mtkviewer.R;
import com.yutian.mtkviewer.dbcontrol.TreeItemController;
import com.yutian.mtkviewer.dbcontrol.greendao.FARITEM;
import com.yutian.mtkviewer.widget.PictureView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wuwenchuan on 2017/1/16.
 */
public class ItemShowActivity extends AppCompatActivity {

    @Bind(R.id.m_itemshow)
    PictureView mItemShow;

    private TreeItemController mTreeItemController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_itemshow);
        ButterKnife.bind(this);


        String id = getIntent().getStringExtra(getResources().getString(R.string.intent_itemid));
        if (id == null)
            this.finish();

        this.setTitle(getIntent().getStringExtra(getResources().getString(R.string.intent_itemname)));

        mTreeItemController = TreeItemController.getInstance(this);

        if (mTreeItemController == null) {
            this.finish();
        }

        FARITEM faritem = mTreeItemController.getItemByItemID(id);

        if (faritem == null)
            this.finish();

        if(faritem.getWHOLECONTENTIMG() != null){
            mItemShow.setPictureByte(faritem.getWHOLECONTENTIMG());
        }else{
            this.finish();
        }
    }
}
