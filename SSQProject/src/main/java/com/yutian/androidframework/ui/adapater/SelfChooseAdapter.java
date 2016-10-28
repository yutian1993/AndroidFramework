package com.yutian.androidframework.ui.adapater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yutian.androidframework.R;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfDataModel;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfSSQDataModel;
import com.yutian.androidframework.control.ssq.ssqmodel.UISSQDataModel;
import com.yutian.androidframework.ui.layout.SSQResultLayout;
import com.yutian.androidframework.ui.layout.SSQSwipLayout;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.base.util.DisplayUtil;
import com.yutian.base.util.ssq.SSQUtil;
import com.yutian.util.SSQLibUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yutian on 2016/10/16.
 */

public class SelfChooseAdapter extends RecyclerView.Adapter<SelfChooseAdapter.SelfChooseHolder> {

    SelfDataModel mCurrentData = new SelfDataModel();
    List<UISSQDataModel> mData = new ArrayList<>();
    //    List<SSQDataModel> mData = new ArrayList<>();
    Map<String, UISSQDataModel> mDataHas = new TreeMap<>();
    OperatorClick mUIClick;

    Integer mNoteFontSize = null;
    Integer mMoneyFontSize = null;
    CharSequence mNoteText = null;
    CharSequence mMoneyText = null;

    public interface OperatorClick {
        public void click(int uiid, int itemid);
    }

    public SelfChooseAdapter() {
    }

    //    @Override
//    public void onBindViewHolder(SelfChooseHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//        holder.mDataContainer.setmSSQDataModel(mData.get(position));
//        holder.mPour.setText(mData.get(position).getPour() + holder.mPour.getText().toString());
//        holder.mPosition = position;
//    }

    @Override
    public SelfChooseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SelfChooseHolder view = new SelfChooseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ssq_self_result_item,
                parent, false));
        if (mNoteFontSize == null || mMoneyFontSize == null) {
            mNoteFontSize = DisplayUtil.spToPx(parent.getContext(), Constants.SSQ_FONT_NODE);
            mMoneyFontSize = DisplayUtil.spToPx(parent.getContext(), Constants.SSQ_FONT_MONEY);
            mNoteText = parent.getContext().getResources().getText(R.string.ssq_number_unit);
            mMoneyText = parent.getContext().getResources().getText(R.string.ssq_money_unit);
        }
        return view;
    }

    @Override
    public void onBindViewHolder(SelfChooseHolder holder, int position) {
        holder.mDataContainer.setmSSQDataModel(mData.get(position));
        holder.mDataContainer.setNeedSave(mData.get(position).isNeedSave());

        CharSequence mText = Integer.toString(mData.get(position).getCount()) +
                mNoteText + "\n";
        int index = mText.length() - 2;
        mText = mText.toString() + Integer.toString(mData.get(position).getPay()) +
                mMoneyText;
        holder.mPour.setText(SSQUtil.getSSQNotesSpannableSize(mText, index, mNoteFontSize, mMoneyFontSize));

//        if (mData.get(position).isNeedSave()) {
//            holder.mPour.b
//            holder.mPour.setBackgroundColor(Color.parseColor("#FF4081"));
//        } else {
//            holder.mPour.setBackgroundColor(Color.parseColor("#3ffc00"));
//        }

        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelfChooseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SSQSwipLayout mContainer;
        SSQResultLayout mDataContainer;
        TextView mPour;
        int mPosition;

        public SelfChooseHolder(View itemView) {
            super(itemView);
            mContainer = (SSQSwipLayout) itemView;
            mDataContainer = (SSQResultLayout) itemView.findViewById(R.id.m_ssq_item_result);
            mPour = (TextView) itemView.findViewById(R.id.ssq_board);

            itemView.findViewById(R.id.ssq_change).setOnClickListener(this);
            itemView.findViewById(R.id.ssq_delete).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mUIClick.click(v.getId(), mPosition);
            mContainer.openorclose(true, false);
        }
    }

    /**
     * 清空当前Adapter中的所有数据
     *
     * @return 是否清空成功
     */
    public boolean cleanSSQDataModel() {
        mData.clear();
        mDataHas.clear();
        return true;
    }

    /**
     * 添加一个SSQDataModel到List列表
     *
     * @param newmodel
     * @return
     */
    public boolean addSSQDataModel(UISSQDataModel newmodel) {
        String key = "";
        for (String ball : newmodel.getRedBallList()) {
            key += ball;
        }
        for (String ball : newmodel.getBlueBallList()) {
            key += ball;
        }
        //对于重复的添加的选择在原来的基础上再加上当前的选择
        if (mDataHas.get(key) != null) {
            int pour = newmodel.getCount();
            int pay = newmodel.getPay();

            UISSQDataModel uimodel = mDataHas.get(key);
            uimodel.setNeedSave(true);
            uimodel.setNeedUpdate(true);
            uimodel.setCount(pour + uimodel.getCount());
            uimodel.setPay(pay + uimodel.getPay());
        } else {
            mData.add(0, new UISSQDataModel(newmodel));
//            Iterator<String> index = mDataHas.keySet().iterator();
//            String addkey = "";
//            while (index.hasNext()) {
//                addkey = index.next();
//            mDataHas.put(addkey, mDataHas.get(addkey));
//            }
            mDataHas.put(key, mData.get(0));
            mData.get(0).setNeedSave(true);
            mData.get(0).setUiAddDate(new Date());
        }

        return true;
    }

    /**
     * 更新数据中的某个对象
     *
     * @param update
     * @param updatemodel
     * @return
     */
    public boolean updateSSQDataModel(int update, UISSQDataModel updatemodel) {
        if (update >= mData.size() || update < 0)
            return false;
        //mData.set(update, updatemodel.CloneSelf());
        SSQUtil.updateSSQDataModel(mData.get(update), updatemodel);
        return true;
    }

    /**
     * 获取list中指定项
     *
     * @param index
     * @return
     */
    public UISSQDataModel getSSQDataModel(int index) {
        if (index < 0 || index >= mData.size())
            return null;
        return mData.get(index);
    }

    /**
     * 删除list中一个对象
     * @param deleteid list index
     * @return 是否成功删除
     */
    public boolean deleteSSQDataModel(int deleteid) {
        if (deleteid < 0 || deleteid >= mData.size())
            return false;
        UISSQDataModel deletemodel = mData.remove(deleteid);
        String deletekey = "";
        for (String ball : deletemodel.getRedBallList()) {
            deletekey += ball;
        }
        for (String ball : deletemodel.getBlueBallList()) {
            deletekey += ball;
        }
        mDataHas.remove(deletekey);

        //删除Treemap中的索引
//        Iterator<String> keys = mDataHas.keySet().iterator();
//        String key = "";
//        while (keys.hasNext()) {
//            key = keys.next();
//            if (mDataHas.get(key) > deleteid) {
//                mDataHas.put(key, mDataHas.get(key) - 1);
//            }
//        }
//        mDataHas.remove(deletekey);

        return true;
    }

    /**
     * 设置点击事件回调
     *
     * @param operatorClick
     */
    public void OperatorClick(OperatorClick operatorClick) {
        mUIClick = operatorClick;
    }

    public SelfDataModel getmCurrentData() {
        return mCurrentData;
    }

    public void setmCurrentData(SelfDataModel mCurrentData) {
        this.mCurrentData = mCurrentData;
    }

    public List<UISSQDataModel> getmData() {
        return mData;
    }

    public void setmData(List<UISSQDataModel> mData) {
        this.mData = mData;
    }

    public SelfDataModel getSaveSelfDataModel() {
        List<SelfSSQDataModel> needToSave = new ArrayList<>();
        for (UISSQDataModel ssqModel : mData) {
            if (ssqModel.isNeedSave()) {
                needToSave.add(ssqModel);
            }
        }

        mCurrentData.setmSSQDataModel(needToSave);

        if (mCurrentData.getPeriod() == null) {
            mCurrentData.setPeriod(SSQLibUtil.currentPeriod());
        }

        return mCurrentData;
    }

    public SelfDataModel getDeleteDateModel() {
        List<SelfSSQDataModel> needToDelete = new ArrayList<>();
        for (UISSQDataModel ssqModel : mData) {
            needToDelete.add(ssqModel);
        }
        mCurrentData.setmSSQDataModel(needToDelete);
        return mCurrentData;
    }

}
