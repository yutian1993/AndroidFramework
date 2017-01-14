package com.yutian.mtkviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yutian.mtkviewer.R;
import com.yutian.mtkviewer.dbcontrol.greendao.TreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/1/12.
 */
public class TreeItemAdapter extends RecyclerView.Adapter {

    private ArrayList<TreeItem> mDatalist = null;
    private LayoutInflater mLayoutInflater = null;
    private OnTreeItemClickListener mTreeItemClickListener = null;

    public TreeItemAdapter(LayoutInflater layoutInflater, ArrayList<TreeItem> list) {
        this.mDatalist = list;
        mLayoutInflater = layoutInflater;
    }

    public void setOnTreeItemClickListener(OnTreeItemClickListener listener) {
        mTreeItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleViewHolder(mLayoutInflater.inflate(R.layout.treeitem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TreeItem item = mDatalist.get(position);

        if (holder instanceof RecycleViewHolder) {
            ((RecycleViewHolder)holder).bindItem(item);
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    @Override
    public int getItemCount() {
        if (mDatalist != null)
            return mDatalist.size();
        return 0;
    }

    private class RecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.list_item_name);
        }

        void bindItem(final TreeItem treeItem) {
            mTextView.setText(treeItem.getNAME());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTreeItemClickListener.onTreeItemClicked(treeItem);
                }
            });
        }

    }

    public interface OnTreeItemClickListener {
        void onTreeItemClicked(TreeItem item);
    }

    public boolean setNewData(List<TreeItem> data) {
        if (mDatalist == null) {
            mDatalist = new ArrayList<>();
        }
        mDatalist.clear();
        mDatalist.addAll(data);
        return true;
    }

}
