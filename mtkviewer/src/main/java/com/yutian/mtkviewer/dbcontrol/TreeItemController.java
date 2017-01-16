package com.yutian.mtkviewer.dbcontrol;

import android.content.Context;

import com.yutian.mtkviewer.MtkHelperApplication;
import com.yutian.mtkviewer.config.AppValues;
import com.yutian.mtkviewer.dbcontrol.greendao.FARITEM;
import com.yutian.mtkviewer.dbcontrol.greendao.FARITEMDao;
import com.yutian.mtkviewer.dbcontrol.greendao.TreeItem;
import com.yutian.mtkviewer.dbcontrol.greendao.TreeItemDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by wuwenchuan on 2017/1/12.
 */
public class TreeItemController {

    private static TreeItemController gTreeItemCongroller = null;
    private static TreeItemDao gTreeItemDao = null;
    private static FARITEMDao getFARIDDao = null;
    private static Context gAppContext = null;

    /**
     * Get TreeItemController Instance
     * @param context context
     * @return
     */
    public static TreeItemController getInstance(Context context) {

        //Init object
        if (gTreeItemCongroller == null) {
            synchronized (TreeItemController.class) {
                if (gTreeItemCongroller == null) {
                    gAppContext = context.getApplicationContext();

                    if (MtkHelperApplication.getDaoSession(gAppContext) != null) {
                        gTreeItemCongroller = new TreeItemController();
                        gTreeItemDao = MtkHelperApplication.getDaoSession(gAppContext).getTreeItemDao();
                        getFARIDDao = MtkHelperApplication.getDaoSession(gAppContext).getFARITEMDao();
                    }

                }
            }
        }

        return gTreeItemCongroller;
    }

    /**
     * Get first tree item
     * @param kind SW or HW
     * @return
     */
    public List<TreeItem> getFirstTreeByTopParent(String kind) {
        if (kind == null || kind.length() != 2)
            kind = "SW";

        kind = kind.toUpperCase();
        if (!(kind.contains("SW") || kind.contains("HW")))
            kind = "SW";

        QueryBuilder queryBuilder = gTreeItemDao.queryBuilder();
        queryBuilder.where(TreeItemDao.Properties.FARID_PARENT.eq(""), TreeItemDao.Properties.TOP_PARENT.eq(kind));
        queryBuilder.orderDesc(TreeItemDao.Properties.FARID);
        List<TreeItem> mListVals = queryBuilder.list();

        for (TreeItem treeitem : mListVals) {
            System.out.println(treeitem.getNAME());
            System.out.println(treeitem.getFARID());
        }

        return mListVals;
    }

    /**
     * Get Second tree
     * @param parentid parent ID
     * @return
     */
    public List<TreeItem> getSecondTreeByParent(String parentid) {
        if (parentid == null)
            parentid = "FAQ00013";

        QueryBuilder queryBuilder = gTreeItemDao.queryBuilder();
        queryBuilder.where(TreeItemDao.Properties.FARID_PARENT.eq(parentid));
        queryBuilder.orderDesc(TreeItemDao.Properties.FARID);
        List<TreeItem> mListVals = queryBuilder.list();

        for (TreeItem treeitem : mListVals) {
            System.out.println(treeitem.getNAME());
            System.out.println(treeitem.getFARID());
        }

        return mListVals;
    }

    /**
     * Get item list
     * @param parentid parent ID
     * @param lastid last id
     * @return
     */
    public List<TreeItem> getItemListByParent(String parentid, String lastid) {
        if (parentid == null)
            parentid = "FAQ00013";

        QueryBuilder queryBuilder = gTreeItemDao.queryBuilder();
        queryBuilder.where(TreeItemDao.Properties.FARID_PARENT.eq(parentid));
        if (lastid != null) {
            queryBuilder.where(TreeItemDao.Properties.FARID.gt(lastid));
        }
        queryBuilder.orderDesc(TreeItemDao.Properties.FARID);
        queryBuilder.limit(AppValues.DB_QUERY_LIMIT_COUNTS);
        List<TreeItem> mListVals = queryBuilder.list();

        for (TreeItem treeitem : mListVals) {
            System.out.println(treeitem.getNAME());
            System.out.println(treeitem.getFARID());
        }

        return mListVals;
    }

    public FARITEM getItemByItemID(String id) {
        if (id == null)
            return null;

        QueryBuilder queryBuilder = getFARIDDao.queryBuilder();
        queryBuilder.where(FARITEMDao.Properties.FAQID.eq(id));
        List<FARITEM> mListVals = queryBuilder.list();

        if (mListVals.size() == 0)
            return null;

        return mListVals.get(0);
    }

}
