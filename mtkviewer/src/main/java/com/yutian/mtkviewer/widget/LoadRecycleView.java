package com.yutian.mtkviewer.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yutian.mtkviewer.R;
import com.yutian.mtkviewer.adapter.ItemListAdapter;
import com.yutian.utillib.CommonUtil.DisplayUtil;
import com.yutian.utillib.CommonUtil.SizeKindUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuwenchuan on 2017/1/18.
 */
public class LoadRecycleView extends RecyclerView {
    public LoadRecycleView(Context context) {
        this(context, null);
    }

    public LoadRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int footerHeight = -1;
    private LayoutManager mLayoutManager;
    enum LAYOUT_KIND {ErrorLayout, GridLayout, LinearLayout, StaggeredGridLayout};
    private LAYOUT_KIND mLayoutKind = LAYOUT_KIND.ErrorLayout;

    // -- footer view
    private CustomDragRecyclerFooterView mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean isBottom;
    private boolean mIsFooterReady = false;
    private LoadMoreListener mloadMoreListener;
    private int maxPullHeight = 50;//最多下拉高度的px值
    private static final int MAX_PULL_LENGTH = 150;//最多下拉150dp
    Handler handler = new Handler();
    private ItemListAdapter mItemListAdapter;

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        mItemListAdapter = (ItemListAdapter)adapter;
    }

    private int getLastVisibleItemPosition() {
        int lastItemPosition = -1;
        switch (mLayoutKind) {
            case ErrorLayout:
                break;
            case GridLayout:
                lastItemPosition = ((GridLayoutManager)mLayoutManager).findLastVisibleItemPosition();
                break;
            case LinearLayout:
                lastItemPosition = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                int[] lastPositions = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
                lastItemPosition = findMax(lastPositions);
                break;
            default:
                break;
        }
        Log.e("wuenchuan", "Last Position:" + lastItemPosition);
        return lastItemPosition;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }

    public boolean ismPullLoading() {
        return mPullLoading;
    }

//    public boolean ismIsRefreshing() {
//        return mIsRefreshing;
//    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        mloadMoreListener = loadMoreListener;
    }

    private void updateFooterHeight(float delta) {
        if(mFooterView==null)return;
        int bottomMargin = mFooterView.getBottomMargin();
//        Log.i("Alex3","初始delta是"+delta);
        if(delta>50)delta = delta/6;
        if(delta>0) {//越往下滑越难滑
            if(bottomMargin>maxPullHeight)delta = delta*0.65f;
            else if(bottomMargin>maxPullHeight * 0.83333f)delta = delta*0.7f;
            else if(bottomMargin>maxPullHeight * 0.66667f)delta = delta*0.75f;
            else if(bottomMargin>maxPullHeight >> 1)delta = delta*0.8f;
            else if(bottomMargin>maxPullHeight * 0.33333f)delta = delta*0.85f;
            else if(bottomMargin>maxPullHeight * 0.16667F && delta > 20)delta = delta*0.2f;//如果是因为惯性向下迅速的俯冲
            else if(bottomMargin>maxPullHeight * 0.16667F)delta = delta*0.9f;
            Log.i("Alex3","bottomMargin是"+mFooterView.getBottomMargin()+" delta是"+delta);
        }

        int height = mFooterView.getBottomMargin() + (int) (delta+0.5);

        if (mEnablePullLoad && !mPullLoading) {
            if (height > 150){//必须拉超过一定距离才加载更多
//            if (height > 1){//立即刷新
                mFooterView.setState(CustomDragRecyclerFooterView.STATE_READY);
                mIsFooterReady = true;
                Log.i("Alex2", "ready");
            } else {
                mFooterView.setState(CustomDragRecyclerFooterView.STATE_NORMAL);
                mIsFooterReady = false;
                Log.i("Alex2", "nomal");
            }
        }
        mFooterView.setBottomMargin(height);


    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 20) {
            Log.i("Alex2", "准备重置高度,margin是" + bottomMargin + "自高是" + footerHeight);
            this.smoothScrollBy(0,-bottomMargin);
            //一松手就立即开始加载
            if(mIsFooterReady){
                startLoadMore();
            }
        }
    }

    private void startLoadMore() {
        if (mPullLoading) return;
        mPullLoading = true;
        if (mFooterView != null) mFooterView.setState(CustomDragRecyclerFooterView.STATE_LOADING);
        Log.i("Alex2", "现在开始加载");
        mIsFooterReady = false;
        if (mloadMoreListener != null) {
            mloadMoreListener.onLoadMore();
        }
    }

    /**
     * 停止loadmore
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            if(mFooterView==null)return;
            mFooterView.show();
            mFooterView.setState(CustomDragRecyclerFooterView.STATE_ERROR);
        }
    }

    /**
     * 设置是否开启上拉加载更多的功能
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mPullLoading = false;
        mEnablePullLoad = enable;
        if (this.getAdapter() instanceof ItemListAdapter) {
            ((ItemListAdapter) this.getAdapter()).setPullLoadMoreEnable(true);
        }
        if(mItemListAdapter!=null) mItemListAdapter.setPullLoadMoreEnable(enable);//adapter和recyclerView要同时设置
        if(mFooterView==null)return;
        if (!mEnablePullLoad) {
//            this.smoothScrollBy(0,-footerHeight);
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            mFooterView.setBottomMargin(0);
            //make sure "pull up" don't show a line in bottom when listview with one page
        } else {
            mFooterView.show();
            mFooterView.setState(CustomDragRecyclerFooterView.STATE_NORMAL);
            mFooterView.setVisibility(VISIBLE);
            //make sure "pull up" don't show a line in bottom when listview with one page
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    public void initView() {
        mLayoutManager = this.getLayoutManager();

        if(mLayoutManager instanceof GridLayoutManager){
            mLayoutKind = LAYOUT_KIND.GridLayout;
        }else if(mLayoutManager instanceof LinearLayoutManager){
            mLayoutKind = LAYOUT_KIND.LinearLayout;
        }else if(mLayoutManager instanceof StaggeredGridLayoutManager){
            mLayoutKind = LAYOUT_KIND.StaggeredGridLayout;
        }
//        layoutManager = new LinearLayoutManager(context);//自带layoutManager，请勿设置
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        layoutManager.offsetChildrenVertical(height*2);//预加载2/3的卡片
//        this.setLayoutManager(layoutManager);
//        Log.i("Alex", "屏幕密度为" + getContext().getResources().getDisplayMetrics().density);
        maxPullHeight = DisplayUtil.dipToPx(getContext(), MAX_PULL_LENGTH);//最多下拉150dp

//        this.footerClickListener = new footerViewClickListener();
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.i("Alex2", "停下了||放手了");
                        if(isBottom)resetFooterHeight();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.i("Alex2", "开始拖了,现在margin是" + (mFooterView == null ? "" : mFooterView.getBottomMargin()));
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.i("Alex2", "开始惯性移动");
                        break;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItemPosition = getLastVisibleItemPosition();
                Log.i("Alex2","mEnable是"+mEnablePullLoad+"lastitemPosition是"+lastItemPosition+" itemcount是"+mLayoutManager.getItemCount());
                if(lastItemPosition == mLayoutManager.getItemCount()-1 && mEnablePullLoad) {//如果到了最后一个
                    isBottom = true;
                    mFooterView = (CustomDragRecyclerFooterView)mLayoutManager.findViewByPosition(getLastVisibleItemPosition());//一开始还不能hide，因为hide得到最后一个可见的就不是footerview了
                    Log.i("Alex2","到底啦！！"+"mfooterView是"+mFooterView);
//                    if(mFooterView!=null) mFooterView.setOnClickListener(footerClickListener);
                    if(footerHeight==-1 && mFooterView!=null){
                        mFooterView.show();
                        mFooterView.setState(CustomDragRecyclerFooterView.STATE_NORMAL);
                        footerHeight = mFooterView.getMeasuredHeight();//这里的测量一般不会出问题
                        Log.i("Alex2", "底部高度为" + footerHeight);
                    }
                    updateFooterHeight(dy);
                }else if(lastItemPosition == mLayoutManager.getItemCount()-1 && mEnablePullLoad){//如果到了倒数第二个
                    startLoadMore();//开始加载更多
                }
                else {
                    isBottom = false;
                }
            }
        });
    }

    @Override
    public void smoothScrollToPosition(final int position) {
        super.smoothScrollToPosition(position);
        final Timer scrollTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int bottomCardPosition = getLastVisibleItemPosition();
                if(bottomCardPosition<position+1){//如果要向下滚
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollBy(0,50);
                        }
                    });
                }else if(bottomCardPosition>position){//如果要向上滚
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollBy(0,-50);
                        }
                    });
                }else {
                    if(scrollTimer!=null)scrollTimer.cancel();
                }
            }
        };
        scrollTimer.schedule(timerTask,0,20);

    }


    public static class CustomDragRecyclerFooterView extends LinearLayout {
        public final static int STATE_NORMAL = 0;
        public final static int STATE_READY = 1;
        public final static int STATE_LOADING = 2;
        public final static int STATE_ERROR = 3;

        private Context mContext;

        private View mContentView;           //Always show view
        private View mProgressBar;           //Show load picture
        private TextView mHintView;          //Show tips

        public CustomDragRecyclerFooterView(Context context) {
            this(context, null);
        }

        public CustomDragRecyclerFooterView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }


        public void setState(int state) {
            mProgressBar.setVisibility(View.INVISIBLE);
//            mHintView.setVisibility(View.INVISIBLE);
            if (state == STATE_READY) {
                mHintView.setVisibility(View.VISIBLE);
                mHintView.setText("松手加载更多");
            } else if (state == STATE_LOADING) {
                mProgressBar.setVisibility(View.VISIBLE);
                mHintView.setVisibility(INVISIBLE);
            } else if(state == STATE_ERROR){
                mProgressBar.setVisibility(GONE);
                mHintView.setVisibility(VISIBLE);
                mHintView.setText("Load error");
            }
            else {
                mHintView.setVisibility(View.VISIBLE);
//                mHintView.setText(R.string.xlistview_footer_hint_normal);
                mHintView.setText("Load more");
            }
        }

        public void setBottomMargin(int height) {
            if (height < 0) return ;
            LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
            lp.bottomMargin = height;
            mContentView.setLayoutParams(lp);
        }

        public int getBottomMargin() {
            LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
            return lp.bottomMargin;
        }


        /**
         * normal status
         */
        public void normal() {
            mHintView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }


        /**
         * loading status
         */
        public void loading() {
            mHintView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        /**
         * hide footer when disable pull load more
         */
        public void hide() {
            LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
            lp.height = 1;//这里如果设为0那么layoutManger就会抓不到
            mContentView.setLayoutParams(lp);
            mContentView.setBackgroundColor(Color.TRANSPARENT);//这里的颜色要和自己的背景色一致
        }

        /**
         * show footer
         */
        public void show() {
            LayoutParams lp = (LayoutParams)mContentView.getLayoutParams();
            lp.height = LayoutParams.WRAP_CONTENT;
            lp.width =  LayoutParams.MATCH_PARENT;
            mContentView.setLayoutParams(lp);
            mContentView.setBackgroundColor(Color.TRANSPARENT);//这里的颜色要和自己的背景色一致
        }

        private void initView(Context context) {
            mContext = context;
            this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.recycle_footerview, null);
            addView(moreView);
            moreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mContentView = moreView.findViewById(R.id.rlContentView);
            mProgressBar = moreView.findViewById(R.id.pbContentView);
            mHintView = (TextView)moreView.findViewById(R.id.ctvContentView);
            mHintView.setText("load more");
//            mProgressBar.setVisibility(VISIBLE);//一直会显示转圈，自动加载更多时使用
        }
    }
}
