package com.yutian.androidframework.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.yutian.androidframework.R;
import com.yutian.androidframework.control.ssq.SelfDataManager;
import com.yutian.androidframework.control.ssq.impl.SelfDataManagerImpl;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfDataModel;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfSSQDataModel;
import com.yutian.androidframework.control.ssq.ssqmodel.UISSQDataModel;
import com.yutian.androidframework.ui.adapater.SelfChooseAdapter;
import com.yutian.androidframework.ui.decoration.LineDecoration;
import com.yutian.androidframework.ui.fragment.NumberChooseFragement;
import com.yutian.androidframework.ui.layout.SSQLayout;
import com.yutian.androidframework.control.ssq.Constants;
import com.yutian.androidframework.ui.widgets.UIButton;
import com.yutian.base.util.DirUtil;
import com.yutian.util.SSQLibUtil;

import java.util.List;

/**
 * Created by wuwenchuan on 2016/10/12.
 */
public class SSQBallChooseActivity extends AppCompatActivity implements View.OnClickListener, SelfChooseAdapter.OperatorClick {

    //UI Paramenters
    private SSQLayout mSsqSelected;
    private UIButton mSsqAdd;
    private UIButton mSsqSave;
    private UIButton mSsqCandel;
    private UIButton mSsqReset;
    private UIButton mSsqClear;
    private NumberChooseFragement mNumberChoose;
    //Adapater Parameters
    private boolean isChanged = false;
    private int mUpdateItemID;
    private RecyclerView mRecyclerView;
    private SelfChooseAdapter mSelfChooseAdapter;
    //Update Parameters
    private boolean mUpdateDB = false;
    private String mPeriod = null;
    //DB Interfase
    SelfDataManager mSelfDataManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ssq_add);

        mSelfDataManager = new SelfDataManagerImpl(this);

        initview();
        resetOperator();
        initselfdata();
    }

    void initview() {
        mSsqAdd = (UIButton) findViewById(R.id.m_ssq_add);
        mSsqAdd.setOnClickListener(this);
        mSsqCandel = (UIButton) findViewById(R.id.m_ssq_cancel);
        mSsqCandel.setOnClickListener(this);
        mSsqSave = (UIButton) findViewById(R.id.m_ssq_save);
        mSsqSave.setOnClickListener(this);
        mSsqReset = (UIButton) findViewById(R.id.m_ssq_reset);
        mSsqReset.setOnClickListener(this);
        mSsqClear = (UIButton) findViewById(R.id.m_ssq_clear);
        mSsqClear.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.m_self_result);
        mSsqSelected = (SSQLayout) findViewById(R.id.ssq_balls);
        mNumberChoose = (NumberChooseFragement) getFragmentManager().findFragmentById(R.id.fragment_numberchoose);

        //
        Toolbar mToolbar = (Toolbar) findViewById(R.id.m_ssq_toolbar);
        setSupportActionBar(mToolbar);
    }

    public void initselfdata() {
        mRecyclerView.setAdapter(mSelfChooseAdapter = new SelfChooseAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new LineDecoration(this, LinearLayoutManager.VERTICAL));
        mSelfChooseAdapter.OperatorClick(this);
    }

    public void resetOperator() {
        if (isChanged) {
            mSsqAdd.setVisibility(View.GONE);
            mSsqCandel.setVisibility(View.VISIBLE);
            mSsqSave.setVisibility(View.VISIBLE);
        } else {
            mSsqAdd.setVisibility(View.VISIBLE);
            mSsqCandel.setVisibility(View.GONE);
            mSsqSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_ssq_add:
                UISSQDataModel newSeleted = new UISSQDataModel(mSsqSelected.getSSQDataModel());

                if (newSeleted.getRedBallList().size() < Constants.SSQ_REDBALL_MIN) {
                    alerttouser(R.string.ssq_alert_redball);
                    break;
                }

                if (newSeleted.getBlueBallList().size() < Constants.SSQ_BLUEBALL_MIN) {
                    alerttouser(R.string.ssq_alert_blueball);
                    break;
                }

                int needTopay = SSQLibUtil.needToPay(newSeleted.getRedBallList().size(), newSeleted.getBlueBallList().size());

                needTopay *= mNumberChoose.getCurrentChoose();
                if (needTopay < 0) {
                    alerttouser(R.string.ssq_alert_money);
                    break;
                }

                newSeleted.setCount(mNumberChoose.getCurrentChoose());
                newSeleted.setPay(needTopay);

                mSelfChooseAdapter.addSSQDataModel(newSeleted);
                mSelfChooseAdapter.notifyDataSetChanged();

                break;
            case R.id.m_ssq_cancel:
                isChanged = false;
                resetOperator();
                break;
            case R.id.m_ssq_save:
                UISSQDataModel updateItem = new UISSQDataModel(mSsqSelected.getSSQDataModel());
                if (updateItem.getRedBallList().size() < Constants.SSQ_REDBALL_MIN) {
                    alerttouser(R.string.ssq_alert_redball);
                    break;
                }
                if (updateItem.getBlueBallList().size() < Constants.SSQ_BLUEBALL_MIN) {
                    alerttouser(R.string.ssq_alert_blueball);
                    break;
                }
                needTopay = SSQLibUtil.needToPay(updateItem.getRedBallList().size(), updateItem.getBlueBallList().size());
                needTopay *= mNumberChoose.getCurrentChoose();
                if (needTopay < 0) {
                    alerttouser(R.string.ssq_alert_money);
                    break;
                }
                updateItem.setCount(mNumberChoose.getCurrentChoose());
                updateItem.setPay(needTopay);

                updateItem.setNeedSave(mSelfChooseAdapter.getSSQDataModel(mUpdateItemID).isNeedSave());
                mSelfChooseAdapter.updateSSQDataModel(mUpdateItemID, updateItem);

                //判断更新后的数据是否需要保存到数据库
                if (!mSelfChooseAdapter.getSSQDataModel(mUpdateItemID).isNeedSave() &&
                        mSelfChooseAdapter.getSSQDataModel(mUpdateItemID).isNeedUpdate()) {
                    mSelfDataManager.updateSelfSSQDataModel(mSelfChooseAdapter.getSSQDataModel(mUpdateItemID));
                    mSelfChooseAdapter.getSSQDataModel(mUpdateItemID).setNeedSave(false);
                    mSelfChooseAdapter.getSSQDataModel(mUpdateItemID).setNeedUpdate(false);
                }

                isChanged = false;
                resetOperator();
                mSelfChooseAdapter.notifyDataSetChanged();
                break;
            case R.id.m_ssq_reset:
                mSsqSelected.resetAllBalls();
                if (isChanged) {
                    isChanged = false;
                    resetOperator();
                }
                mNumberChoose.setCurrentChoose(0);
                break;
            case R.id.m_ssq_clear:

                SelfDataModel needToDelete = mSelfChooseAdapter
                        .getDeleteDateModel();
                mSelfDataManager.deleteSelfSSQDateModel(needToDelete);

                mSelfChooseAdapter.cleanSSQDataModel();
                mSelfChooseAdapter.notifyDataSetChanged();
                if (isChanged) {
                    isChanged = false;
                    resetOperator();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void click(int uiid, int itemid) {
        switch (uiid) {
            case R.id.ssq_delete:
                UISSQDataModel needDelete = mSelfChooseAdapter.getSSQDataModel(itemid);
                if (needDelete.isNeedSave() == false || mUpdateDB) {
                    if (!mSelfDataManager.deleteSelfSSQDataModel( mSelfChooseAdapter.getmCurrentData().getPeriod(),
                            needDelete.getId(), needDelete.getNumber())) {
                        alerttouser(R.string.ssq_delete_error);
                        return;
                    }
                } else {
                    //Don't need to save to db
                }
                isChanged = false;
                resetOperator();
                mSelfChooseAdapter.deleteSSQDataModel(itemid);
                mSelfChooseAdapter.notifyDataSetChanged();
                break;
            case R.id.ssq_change:
                mUpdateItemID = itemid;
                mSsqSelected.setSSQDataModel(mSelfChooseAdapter.getSSQDataModel(itemid));
                mNumberChoose.setCurrentChoose(mSelfChooseAdapter.getSSQDataModel(itemid).getCount());
                isChanged = true;
                resetOperator();
                break;
            default:
                break;
        }
    }

    public void alerttouser(int resid) {
        Snackbar snackshow = Snackbar.make(mRecyclerView, resid, Snackbar.LENGTH_SHORT)
                .setAction("Action", null);
        snackshow.getView().setBackgroundColor(Color.parseColor("#FF4081"));
        snackshow.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackshow.show();
    }

    public void alerttouser(String message) {
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ssq_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.m_menu_ssq_save:
                if (mUpdateDB) {

                } else {
                    SelfDataModel needToSave = mSelfChooseAdapter
                            .getSaveSelfDataModel();
                    if (needToSave.getmSSQDataModel().size() == 0)
                        alerttouser(R.string.ssq_save_alert);
                    mSelfDataManager.insertSelfSSQDataModel(needToSave);
                    for (UISSQDataModel update :
                            mSelfChooseAdapter.getmData()) {
                        update.setNeedSave(false);
                    }
                    mSelfChooseAdapter.notifyDataSetChanged();
                }
                return true;

            default:
                //Do nothing
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
