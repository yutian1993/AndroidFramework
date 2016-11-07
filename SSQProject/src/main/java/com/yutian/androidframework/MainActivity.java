package com.yutian.androidframework;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.androidframework.control.ssq.PeriodDataManager;
import com.yutian.androidframework.control.ssq.impl.PeriodDataManagerImpl;
import com.yutian.androidframework.control.ssq.ssqmodel.MainItemModel;
import com.yutian.androidframework.control.ssq.ssqmodel.SSQDataModel;
import com.yutian.androidframework.ui.layout.SSQResultLayout;
import com.yutian.base.util.ssq.SSQUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    View view;

    RecyclerView mRecyclerView;

    List<SSQDataModel> mData;

    PeriodDataManager mPeriodDataManager = null;;

//    HomeAdapter myAdapater;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public void generateViewInformation(Activity itemview) {
        if (itemview == null)
            return;

        List<MainItemModel> search = mPeriodDataManager.getTopTenInfor(null);

        if (search.size() > 0) {
            MainItemModel temp = search.get(0);
            ((TextView) itemview.findViewById(R.id.m_mainitem_periodresult))
                    .setText(SSQUtil.setSSQMainItemColorSpannable(
                            Constants.G_TXT_MAINPERIOD_INFOR,
                            Constants.G_COLOR_MAINITEM_TXT,true,
                            temp.getPeriod(), Constants.G_COLOT_MAINITEM_TXT_FLASH_RED));
            ((TextView) itemview.findViewById(R.id.m_mainitem_periodtime))
                    .setText(SSQUtil.setSSQMainItemColorSpannable(
                            Constants.G_TXT_MAINPERIOD_TIME_INFOR,
                            Constants.G_COLOR_MAINITEM_TXT,true,
                            temp.getPeriodTime(), Constants.G_COLOT_MAINITEM_TXT_FLASH_RED,
                            temp.getPeriodWeek(), null));
            ((TextView) itemview.findViewById(R.id.m_mainitem_firstprice))
                    .setText(SSQUtil.setSSQMainItemColorSpannable(
                            Constants.G_TXT_MAINPERIOD_PRICE_INFOR,
                            Constants.G_COLOR_MAINITEM_TXT, true,
                            temp.getFirstPricenNum(), Constants.G_COLOT_MAINITEM_TXT_FLASH_RED));
            ((TextView) itemview.findViewById(R.id.m_mainitem_firstprice_val))
                    .setText(SSQUtil.setSSQMainItemColorSpannable(
                            Constants.G_TXT_MAINPERIOD_PRICE_VAL_INFOR,
                            Constants.G_COLOR_MAINITEM_TXT, true,
                            temp.getFirstPriceVal(), Constants.G_COLOT_MAINITEM_TXT_FLASH_RED));
            ((TextView) itemview.findViewById(R.id.m_mainitem_myprice_infor))
                    .setText(SSQUtil.setSSQMainItemColorSpannable(
                            Constants.G_TXT_MAINPERIOD_MYPRICE_INFOR,
                            Constants.G_COLOR_MAINITEM_TXT, true,
                            temp.getMyPriceNum(),Constants.G_COLOT_MAINITEM_TXT_FLASH_RED));
            ((TextView) itemview.findViewById(R.id.m_mainitem_myprice))
                    .setText(SSQUtil.setSSQMainItemColorSpannable(
                            Constants.G_TXT_MAINPERIOD_MYPRICE,
                            Constants.G_COLOR_MAINITEM_TXT, true,
                            temp.getMyPriceVal(),Constants.G_COLOT_MAINITEM_TXT_FLASH_RED));

            //SSQData
            SSQResultLayout ssqui = ((SSQResultLayout) findViewById(R.id.m_ssq_item_result));
            ssqui.setLimitSize(true);
            ssqui.setmSSQDataModel(temp);
            ssqui.setEnableClick(true);
        }


//        ((TextView) itemview.findViewById(R.id.m_mainitem_periodresult))
//                .setText(String.format(Constants.G_TXT_MAINPERIOD_INFOR, "16130"));
//        ((TextView) itemview.findViewById(R.id.m_mainitem_periodtime))
//                .setText(String.format(Constants.G_TXT_MAINPERIOD_TIME_INFOR, "2016年10月12日", "周四"));
//        ((TextView) itemview.findViewById(R.id.m_mainitem_firstprice))
//                .setText(String.format(Constants.G_TXT_MAINPERIOD_PRICE_INFOR, "1"));
//        ((TextView) itemview.findViewById(R.id.m_mainitem_firstprice_val))
//                .setText(String.format(Constants.G_TXT_MAINPERIOD_PRICE_VAL_INFOR, "1000,000"));
//        ((TextView) itemview.findViewById(R.id.m_mainitem_myprice_infor))
//                .setText(String.format(Constants.G_TXT_MAINPERIOD_MYPRICE_INFOR, "2"));
//        ((TextView) itemview.findViewById(R.id.m_mainitem_myprice))
//                .setText(String.format(Constants.G_TXT_MAINPERIOD_MYPRICE, "1000,00"));
    }

    //    @Bind(R.id.m_testButton)
//    UIButton m_testButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPeriodDataManager = new PeriodDataManagerImpl(this);
        setContentView(R.layout.ssq_main_show_item);
        generateViewInformation(this);


//        ButterKnife.bind(this);
//
//        view = findViewById(R.id.ssq_board);

//        DBConfigControl.getInstance(this).addNewConfigVal("Name", "wuwenchuan");
//        System.out.println(DBConfigControl.getInstance(this).deleteConfigVal("Name"));

//        PeriodDataControl.getInstance(this).getTenDataFromPeriod("16097", true);

//        initdata();
//        mRecyclerView = (RecyclerView)findViewById(R.id.m_self_result);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(myAdapater = new HomeAdapter());


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {


//
//                ObjectAnimator animator = AnimationUtil.scaleAndRotation(view, 1f);
//                if (animator == null)
//                    System.out.println("Null information");
//                animator.start();
//        SSQDataModel mSSQDataModel = new SSQDataModel();
//        List<String> reDballs = new ArrayList<>();
//        reDballs.add("01");
//        reDballs.add("06");
//        reDballs.add("07");
//        reDballs.add("09");
//        reDballs.add("33");
//        List<String> blueballs = new ArrayList<>();
//        blueballs.add("09");
//        mSSQDataModel.setRedBallList(reDballs);
//        mSSQDataModel.setBlueBallList(blueballs);
//        SSQResultLayout temp = ((SSQResultLayout) findViewById(R.id.m_ssq_item_result));
//        temp.setLimitSize(true);
//        temp.setmSSQDataModel(mSSQDataModel);
//        generateViewInformation(this);
//
//                ObjectAnimator nopeAnimator = AnimationUtil.nope(view, DisplayUtil.dipToPx(view.getContext(), 5));
//                nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
//                nopeAnimator.start();
//            }
//        }, 2000);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        WindowManager wm = this.getWindowManager();
//
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//
//        System.out.println("width ===> " + width);
//        System.out.println("height ===> " + height);

//        CheckButton m_checkbutton = (CheckButton) findViewById(R.id.m_checkbutton);
//        m_checkbutton.setShape(GradientDrawable.OVAL);
//        m_checkbutton.setFillet(true);
//        m_checkbutton.setText("1");
//        m_checkbutton.setWidth(50);
//        m_checkbutton.setHeight(50);
//
//        m_checkbutton.setBackColori(ContextCompat.getColor(this, R.color.ballnormal));
//        m_checkbutton.setTextColori(ContextCompat.getColor(this, R.color.balltextnormal));
//        m_checkbutton.setBackColoriSelected(ContextCompat.getColor(this, R.color.redpressed));
//        m_checkbutton.setTextColoriSelected(ContextCompat.getColor(this, R.color.redtextpressed));
//        m_checkbutton.setCheckBackColori(ContextCompat.getColor(this, R.color.redselected));
//        m_checkbutton.setCheckTextColori(ContextCompat.getColor(this, R.color.redtextselected));


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//                SSQLayout mLayout = (SSQLayout)findViewById(R.id.ssq_balls);
//                SSQDataModel mSSQDataModel = new SSQDataModel();
//                List<String> reDballs = new ArrayList<>();
//                reDballs.add("01");
//                reDballs.add("06");
//                reDballs.add("07");
//                reDballs.add("09");
//                reDballs.add("33");
//                reDballs.add("21");
//                List<String> blueballs = new ArrayList<>();
//                blueballs.add("06");
//                blueballs.add("07");
//                blueballs.add("09");
//                mSSQDataModel.setRedBalls(reDballs);
//                mSSQDataModel.setBlueBalls(blueballs);
//                mLayout.setSSQDataModel(mSSQDataModel);
//            }
//        });


        //

    }

    protected void initdata() {
//        mData = new ArrayList<>();
//        SSQDataModel mSSQDataModel = new SSQDataModel();
//        List<String> reDballs = new ArrayList<>();
//        reDballs.add("01");
//        reDballs.add("06");
//        reDballs.add("07");
//        reDballs.add("09");
//        reDballs.add("33");
//        reDballs.add("21");
//        List<String> blueballs = new ArrayList<>();
//        blueballs.add("06");
//        blueballs.add("07");
//        blueballs.add("09");
//        mSSQDataModel.setRedBalls(reDballs);
//        mSSQDataModel.setBlueBalls(blueballs);
//        mData.add(mSSQDataModel);
    }

//    @OnClick(R.id.m_testButton)
//    public void test(View view) {
////        m_testButton.setBackImagei(R.drawable.minus);
////        m_testButton.invalidate();
////        m_testButton.setBackgroundResource(R.drawable.plus);
//    }

//    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            SSQResultLayout view;
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                view = (SSQResultLayout)itemView.findViewById(R.id.m_ssq_item_result);
//            }
//        }
//
//        @Override
//        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            MyViewHolder temp = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.ssq_self_result_item
//                    ,parent,false));
//            return temp;
//        }
//
//        @Override
//        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
//            holder.view.setmSSQDataModel(mData.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mData.size();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Add
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
