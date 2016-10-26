package com.yutian.DBFile.SSQControl;

import com.yutian.DBFile.SSQControl.SSQModel.SSQConfigModel;
import com.yutian.DBFile.SSQControl.SSQModel.SSQPriceLevelModel;
import com.yutian.DBFile.SSQDBModel.PRICELEVEL;
import com.yutian.DBFile.SSQDBModel.SSQCONFIG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuwenchuan on 2016/9/13.
 */
public class SSQConfigControl {

    private static Map<String, String> g_ssqconfig = new HashMap<>();
    private static Map<Integer, SSQPriceLevelModel> g_pricelevel = new HashMap<>();

    private static SSQConfigControl g_ssqConfigControl = null;

    public static SSQConfigControl getInstance() {

        if (g_ssqConfigControl == null) {
            synchronized (SSQConfigControl.class) {
                if (g_ssqConfigControl == null)
                    g_ssqConfigControl = new SSQConfigControl();
            }
        }

        return g_ssqConfigControl;
    }

    //No right to access
    private SSQConfigControl() {
        initcofig();
    }

    //获得数据库中的配置信息
    public Map<String, String> getSSQConfig() {
        return g_ssqconfig;
    }

    //获取奖级
    public Map<Integer, SSQPriceLevelModel> getPriceLevel() {
        return g_pricelevel;
    }

    /**
     * 用来初始化数据库的数据到内存中
     */
    protected void initcofig() {

        List<SSQPriceLevelModel> levelmodels = getAllPriceLevel();
        for (SSQPriceLevelModel model :
                levelmodels) {
            g_pricelevel.put(model.getLevel(), model);
        }

        List<SSQConfigModel> configmodels = getAllSsqConfig();
        for (SSQConfigModel model:
                configmodels) {
            g_ssqconfig.put(model.getName(), model.getValue());
        }

    }

    /**
     * All price level in data base
     * @return all level models
     */
    protected List<SSQPriceLevelModel> getAllPriceLevel() {
        String sql = SQLStatement.generateSelectAllSQL(PRICELEVEL.class);
        List<Object> queryresults = SSQDBControl.getInstance().querystatement(sql, PRICELEVEL.class);

        List<SSQPriceLevelModel> alllevel = new ArrayList<>();
        for (Object object:
                queryresults) {
            alllevel.add(new SSQPriceLevelModel((PRICELEVEL)object));
        }

        return alllevel;
    }

    /**
     * All config value
     * @return all config model
     */
    protected List<SSQConfigModel> getAllSsqConfig() {
        String sql = SQLStatement.generateSelectAllSQL(SSQCONFIG.class);
        List<Object> queryresults = SSQDBControl.getInstance().querystatement(sql, SSQCONFIG.class);

        List<SSQConfigModel> alllevel = new ArrayList<>();
        for (Object object:
                queryresults) {
            alllevel.add(new SSQConfigModel((SSQCONFIG)object));
        }

        return alllevel;
    }

}
