package com.yutian.mtkviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yutian.mtkviewer.R;
import com.yutian.mtkviewer.dbcontrol.greendao.TreeItem;
import com.yutian.mtkviewer.widget.LoadRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuwenchuan on 2017/1/12.
 */
public class ItemListAdapter<T> extends RecyclerView.Adapter {
    int ITEM_ERROR = -1;
    int ITEM_HEADER = 0;
    int ITEM_ITEM = 1;
    int ITEM_FOOTER = 2;

    private List<T> mDatalist = null;
    private LayoutInflater mLayoutInflater = null;
    private OnTreeItemClickListener mTreeItemClickListener = null;
    protected boolean loadMore = false;
    private RecycleViewHolder mViewHolder = null;

    public ItemListAdapter(LayoutInflater layoutInflater, ArrayList<T> list) {
        this.mDatalist = list;
        mLayoutInflater = layoutInflater;
    }

    public void setOnTreeItemClickListener(OnTreeItemClickListener listener) {
        mTreeItemClickListener = listener;
    }

    public void setPullLoadMoreEnable(boolean enable) {
        this.loadMore = enable;
    }

    public boolean getPullLoadMoreEnable() {
        return loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.e("wuwenchuan--->", "viewtype: " + viewType);
        if (viewType == ITEM_FOOTER)
            return new VHFooter(new LoadRecycleView.CustomDragRecyclerFooterView(parent.getContext()));
        else {
            mViewHolder = new RecycleViewHolder(mLayoutInflater.inflate(R.layout.recycle_treeitem, parent, false));
            return mViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.e("wuwenchuan--->", "BindView position: " + position + " class: " + holder.getClass());

        if (holder.getClass() == RecycleViewHolder.class) {
            T item = mDatalist.get(position);
            ((RecycleViewHolder) holder).bindItem((TreeItem) item);
        } else if (holder instanceof VHFooter) {
            if (!loadMore) ((VHFooter) holder).footerView.hide();//第一次初始化显示的时候要不要显示footerView
        } else {
            throw new IllegalStateException("Illegal state Exception onBindviewHolder");
        }
    }

    /**
     * 根据位置判断这里该用哪个ViewHolder
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
//        if (position == 0)return TYPE_HEADER;
//        else if(isPositonFooter(position))return TYPE_FOOTER;
//        return TYPE_ITEM;
//        Log.e("getItemViewType", "Judgement: " + position);
        if (isPositonFooter(position))
            return ITEM_FOOTER;
        else
            return ITEM_ITEM;
    }

    protected boolean isPositonFooter(int position) {//这里的position从0算起
        if (mDatalist == null && position == 1) return true;//如果没有item
        return position == mDatalist.size();//如果有item(也许为0)
    }

    @Override
    public int getItemCount() {
        if (mDatalist != null)
            return mDatalist.size()+1;
        return 1;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_item_name);
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

    public static class VHFooter extends RecyclerView.ViewHolder {
        public LoadRecycleView.CustomDragRecyclerFooterView footerView;

        public VHFooter(View itemView) {
            super(itemView);
            footerView = (LoadRecycleView.CustomDragRecyclerFooterView) itemView;
        }
    }

    public interface OnTreeItemClickListener {
        void onTreeItemClicked(TreeItem item);
    }

    public boolean setNewData(List<T> data) {
        if (mDatalist == null) {
            mDatalist = new ArrayList<>();
        }
        mDatalist.clear();
        mDatalist.addAll(data);
        return true;
    }

    public boolean addMoreData(List<T> data) {
        if (mDatalist == null) {
            mDatalist = new ArrayList<>();
        }
        return mDatalist.addAll(data);
    }

    public T getItem(int position) {
        if (mDatalist == null)
            return null;

        if (position < 0 || position >= getItemCount()) {
            return mDatalist.get(getItemCount() - 1);
        } else
            return mDatalist.get(position);
    }

    public int getRealItemCount() {
        if (mDatalist != null)
            return mDatalist.size();
        return 0;
    }
}
