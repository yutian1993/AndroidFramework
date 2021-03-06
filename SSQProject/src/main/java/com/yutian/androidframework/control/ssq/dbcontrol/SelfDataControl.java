package com.yutian.androidframework.control.ssq.dbcontrol;

import android.content.Context;

import com.yutian.androidframework.SSQApplication;
import com.yutian.androidframework.control.ssq.ssqmodel.SelfSSQDataModel;
import com.yutian.base.database.srcgen.DaoSession;
import com.yutian.base.database.srcgen.MYBUY;
import com.yutian.base.database.srcgen.MYBUYDao;
import com.yutian.base.database.srcgen.MYSSQDATA;
import com.yutian.base.database.srcgen.MYSSQDATADao;
import com.yutian.base.database.srcgen.MYWINDao;
import com.yutian.base.util.DateUtil;

import java.util.List;

import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 将数据库的操作上升的control层
 * Created by wuwenchuan on 2016/10/24.
 */
public class SelfDataControl {
    private static final String TAG = SelfDataControl.class.getSimpleName();
    private static SelfDataControl gSelfChooseChontrol;
    private static Context gAppContext;
    private DaoSession gDaoSession;

    private MYBUYDao mBuyDao = null;
    private MYSSQDATADao mSSQDataDao = null;
    private MYWINDao mWinDao = null;
    private DaoSession mDaoSession = null;

    public static SelfDataControl getInstance(Context context)
    {
        if (gSelfChooseChontrol == null) {
            synchronized (SelfDataControl.class) {
                if (gSelfChooseChontrol == null) {
                    gAppContext = context.getApplicationContext();
                    gSelfChooseChontrol = new SelfDataControl();
                    gSelfChooseChontrol.mDaoSession = SSQApplication.getDaoSession(gAppContext);
                    gSelfChooseChontrol.mBuyDao = gSelfChooseChontrol.mDaoSession.getMYBUYDao();
                    gSelfChooseChontrol.mSSQDataDao = gSelfChooseChontrol.mDaoSession.getMYSSQDATADao();
                    gSelfChooseChontrol.mWinDao = gSelfChooseChontrol.mDaoSession.getMYWINDao();
                }
            }
        }

        return gSelfChooseChontrol;
    }

    /**
     * 查看某一期自身的购买
     * @param period
     * @return
     */
    public List<SelfSSQDataModel> getBuyInformation(String period) {
        QueryBuilder<MYSSQDATA> myssqdata = mSSQDataDao.queryBuilder();
        Join mybuy = myssqdata.join(MYBUY.class, MYBUYDao.Properties.ID);
        mybuy.where(MYBUYDao.Properties.PERIOD.eq(period));
        List<MYSSQDATA> results = myssqdata.list();
        return SelfSSQDataModel.convertMySSQDatasToSelfSSQDataModels(results);
    }

    /**
     * 获取指定ID的购买详情
     * @param id 购买的ID
     * @return
     */
    public List<SelfSSQDataModel> getSelfSSQDataModels(String id) {
        QueryBuilder myssqdata = mSSQDataDao.queryBuilder();
        myssqdata.where(MYSSQDATADao.Properties.ID.eq(id));
        List<MYSSQDATA> results = myssqdata.list();
        return SelfSSQDataModel.convertMySSQDatasToSelfSSQDataModels(results);
    }

    /**
     * 删除一个SelfSSQDATA的一个ID对应的所有选项数据
     * @param id 数据对应的ID号
     * @return
     */
    public boolean deleteSelfSSQDataModels(String id) {
        if (getSelfSSQDataModels(id).size() > 0) {
            mDaoSession.getDatabase().execSQL("DELETE FROM MYSSQDATA WHERE ID ='" + id + "'");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除一个SelfSSQDATA
     * @param id 数据对应的ID号
     * @param number 数据对应的序列号(-1时删除所有数据)
     * @return
     */
    public boolean deleteSelfSSQDataModel(String id, int number) {
        if (number == -1)
            return deleteSelfSSQDataModels(id);

        if (getSelfSSQDataModel(id, number) != null) {
            mDaoSession.getDatabase().execSQL("DELETE FROM MYSSQDATA WHERE ID ='" + id +
                    "' AND NUMBER = '" + number + "'");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除指定期数和ID号的记录数
     * @param period 期数
     * @param id ID号
     * @param number 删除的数量（为-1时，删除该条记录）
     * @return
     */
    public boolean deleteBuyInfor(String period, String id, int number) {
        if (getSelfBuyInfor(period, id) != null) {
            if (number == -1) {   //删除该条记录
                mDaoSession.getDatabase().execSQL("DELETE FROM MYBUY WHERE ID ='" + id +
                        "' AND PERIOD = '" + period + "'");
            } else {
                MYBUY dbBuy = getSelfBuyInfor(period, id);
                if (dbBuy.getNUMBER() <= 1) {
                    mDaoSession.getDatabase().execSQL("DELETE FROM MYBUY WHERE ID ='" + id +
                            "' AND PERIOD = '" + period + "'");
                } else {
                    mDaoSession.getDatabase().execSQL("UPDATE MYBUY SET NUMBER = '" +
                            (dbBuy.getNUMBER() - 1) + "' WHERE " +
                            "PERIOD ='" + dbBuy.getPERIOD() + "' AND ID ='" + dbBuy.getID() + "'");
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取一个指定period的购买信息
     * @param period period
     * @return
     */
    public List<MYBUY> getSelfBuyInfor(String period) {
        QueryBuilder mybuy = mBuyDao.queryBuilder();
        mybuy.where(MYBUYDao.Properties.PERIOD.eq(period));
        return mybuy.list();
    }

    /**
     * 获取一个指定period && id的购买信息
     * @param period 期数
     * @param id ID
     * @return
     */
    public MYBUY getSelfBuyInfor(String period, String id) {
        QueryBuilder mybuy = mBuyDao.queryBuilder();
        mybuy.where(MYBUYDao.Properties.PERIOD.eq(period),MYBUYDao.Properties.ID.eq(id));
        List<MYBUY> results = mybuy.list();
        return results.size() == 0 ? null : results.get(0);
    }

    /**
     * 获取指定ID和序列号的购买
     * @param id ID
     * @param number 序列号
     * @return 查询到的数据
     */
    public SelfSSQDataModel getSelfSSQDataModel(String id, int number) {
        QueryBuilder<MYSSQDATA> myssqdata = mSSQDataDao.queryBuilder();
        myssqdata.where(MYSSQDATADao.Properties.ID.eq(id));
        myssqdata.where(MYSSQDATADao.Properties.NUMBER.eq(number));
        List<MYSSQDATA> result = myssqdata.list();
        if (result.size() == 0)
            return null;
        else
            return SelfSSQDataModel.convertMySSQDataToSelfSSQDataModel(result.get(0));
    }

    /**
     * 插入购买记录信息(向MYBUY表中插入或者更新数据)
     * @param mybuy
     * @return
     */
    public boolean insertSelfBuyInfor(MYBUY mybuy) {
        MYBUY dbBuy = getSelfBuyInfor(mybuy.getPERIOD(), mybuy.getID());
        if (dbBuy == null) {
            mBuyDao.insert(mybuy);
        } else {
//            datas.get(0).setNUMBER(mybuy.getNUMBER() + datas.get(0).getNUMBER());
//            mBuyDao.update(datas.get(0));
            mDaoSession.getDatabase().execSQL("UPDATE MYBUY SET NUMBER = '"+
                    (mybuy.getNUMBER() + dbBuy.getNUMBER()) +"' WHERE " +
                    "PERIOD ='" + mybuy.getPERIOD() +"' AND ID ='" + mybuy.getID() + "'");
        }

        return true;
    }

    /**
     * 判断指定的ID是否有满足的红球和蓝球，防止重复添加
     * @param id 需要对比的ID
     * @param redballs 红球
     * @param blueballs 蓝球
     * @return
     */
    public MYSSQDATA checkSelfSSQData(String id, String redballs, String blueballs) {
        QueryBuilder<MYSSQDATA> myssqdata = mSSQDataDao.queryBuilder();
        myssqdata.where(MYSSQDATADao.Properties.ID.eq(id),
                MYSSQDATADao.Properties.REDBALL.eq(redballs),
                MYSSQDATADao.Properties.BLUEBALL.eq(blueballs));
        List results = myssqdata.list();
        return results.size() == 0 ? null : (MYSSQDATA)results.get(0);
    }

    /**
     * 添加一个自己的选择到数据库
     * @param ssqdatamodel SSQDataModel
     * @return 对应ID的序列号
     */
    public int insertSelfSSQDataModel(SelfSSQDataModel ssqdatamodel) {
        int number = 1;
        MYSSQDATA searchData = checkSelfSSQData(ssqdatamodel.getId(),
                ssqdatamodel.getRedballs(), ssqdatamodel.getBlueballs());
        if (searchData == null) {
            number = getMaxIndexByID(ssqdatamodel.getId());
            MYSSQDATA newObj = SelfSSQDataModel.convertSelfSSQDataModelToMySSQData(ssqdatamodel);
            newObj.setNUMBER(number);
            mSSQDataDao.insert(newObj);
        } else {
            number = searchData.getNUMBER();
            mDaoSession.getDatabase().execSQL("UPDATE MYSSQDATA SET COUNT = '" +
                    ssqdatamodel.getCount() + "' WHERE " +
                    "ID ='" + searchData.getID() + "' AND NUMBER ='" + searchData.getNUMBER() + "'");
            ssqdatamodel.setDbDumplicate(true);
//            searchData.setCOUNT(searchData.getCOUNT() + ssqdatamodel.getCount());
//            mSSQDataDao.update(searchData);
        }
        return number;
    }

    /**
     * 更新ssqDataModel信息到数据库
     * @param ssqDataModel
     * @return
     */
    public boolean updateSelfSSQDataModel(SelfSSQDataModel ssqDataModel) {
        List<MYSSQDATA> myssqdatas = mSSQDataDao.queryBuilder().where(
                MYSSQDATADao.Properties.ID.eq(ssqDataModel.getId()),
                        MYSSQDATADao.Properties.NUMBER.eq(ssqDataModel.getNumber())
        ).list();

        if (myssqdatas.size() == 1) {
            MYSSQDATA update = SelfSSQDataModel.updateSelfSSQDataModelToMySSQData(ssqDataModel,myssqdatas.get(0));
            String sql = "UPDATE MYSSQDATA SET RED1='"+ssqDataModel.getRed1()+"',RED2='"+ssqDataModel.getRed2()+
                    "',RED3='"+ssqDataModel.getRed3()+"',RED4='"+ssqDataModel.getRed4()+"',RED5='"+ssqDataModel.getRed5()+
                    "',RED6='"+ssqDataModel.getRed6()+"',BLUE='"+ssqDataModel.getBlue()+"',REDBALL='"+ssqDataModel.getRedballs()+
                    "',BLUEBALL='"+ssqDataModel.getBlueballs()+"',COUNT= '"+ssqDataModel.getCount()+"'" +
                    "WHERE ID='" + ssqDataModel.getId()+"' AND NUMBER='"+ssqDataModel.getNumber()+"'";
            System.out.println(sql);
            mDaoSession.getDatabase().execSQL(sql);
//            mSSQDataDao.update(update);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 在一起添加的过程中，ID号是唯一的，只是序列号不一样，
     * 后面的添加仍然使用该ID号，只是序列号会不断增加，删掉的序列号不会重复使用
     * @param id
     * @return
     */
    public int getMaxIndexByID(String id) {
        QueryBuilder<MYSSQDATA> builder = mSSQDataDao.queryBuilder();
        builder.where(MYSSQDATADao.Properties.ID.eq(id));
        builder.orderDesc(MYSSQDATADao.Properties.NUMBER).limit(1);
        List result = builder.list();
        if (result.size() == 0)
            return 1;
        else
            return ((MYSSQDATA)result.get(0)).getNUMBER() + 1;
    }


}
