package com.yutian.mtkviewer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;

import com.yutian.mtkviewer.R;
import com.yutian.mtkviewer.adapter.TreeItemAdapter;
import com.yutian.mtkviewer.dbcontrol.TreeItemController;
import com.yutian.mtkviewer.dbcontrol.greendao.TreeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wuwenchuan on 2017/1/16.
 */
public class SecondItemsActivity extends AppCompatActivity {

    @Bind(R.id.m_items_list)
    RecyclerView mItemListView;
    @Bind(R.id.m_swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;

    private TreeItemAdapter mItemsAdapter;
    private LinearLayoutManager mLayoutManager;
    private TreeItemController mTreeItemController;
    private Context mContext;
    private String mCurrentID;
    private String mLastID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconditems);

        ButterKnife.bind(this);

        initData();
        initView();

        generateItemsList();
    }

    public void initData() {
        mContext = this;

        mTreeItemController = TreeItemController.getInstance(this);

        if (mTreeItemController == null) {
            this.finish();
        }
    }

    public void initView() {
        mItemListView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mItemListView.addItemDecoration(
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mSwiperefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwiperefreshlayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwiperefreshlayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mLayoutManager.findLastVisibleItemPosition();
                List<TreeItem> dataList = mTreeItemController.getItemListByParent(mCurrentID, mItemsAdapter.getItemID(-1));

                if (dataList.size() == 0) {
                    Snackbar.make(mItemListView, "No more data!", Snackbar.LENGTH_SHORT).show();
                } else {
                    mItemsAdapter.addMoreData(dataList);
                    mItemsAdapter.notifyDataSetChanged();
                }
                mSwiperefreshlayout.setRefreshing(false);
                mItemListView.invalidate();
            }
        });
    }

    public void generateItemsList() {
        String id = getIntent().getStringExtra(getResources().getString(R.string.intent_itemid));
        if (id == null)
            this.finish();

        this.setTitle(getIntent().getStringExtra(getResources().getString(R.string.intent_itemname)));

        mCurrentID = id;
        //generate item list
        List<TreeItem> dataList = mTreeItemController.getItemListByParent(mCurrentID, null);

        if (mItemsAdapter == null) {
            mItemsAdapter = new TreeItemAdapter(LayoutInflater.from(this), null);
            mItemsAdapter.setOnTreeItemClickListener(new TreeItemAdapter.OnTreeItemClickListener() {
                @Override
                public void onTreeItemClicked(TreeItem item) {
                    Intent startIntent = new Intent(getApplicationContext(),
                            ItemShowActivity.class);
                    startIntent.putExtra(getResources().getString(R.string.intent_itemid), item.getFARID());
                    startIntent.putExtra(getResources().getString(R.string.intent_itemname), item.getNAME());
                    startActivity(startIntent);

                }
            });
            mItemListView.setAdapter(mItemsAdapter);
        }

        mItemsAdapter.setNewData(dataList);
        mItemsAdapter.notifyDataSetChanged();
        mItemListView.invalidate();
    }


}
