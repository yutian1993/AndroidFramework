package com.yutian.DBFile.SSQControl.SSQModel;

import com.yutian.DBFile.SSQDBModel.SSQCONFIG;

/**
 * Created by wuwenchuan on 2016/9/13.
 */
public class SSQConfigModel {
    Integer id;                //ID

    String name;           //属性名称

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    String value;          //属性值

    public SSQConfigModel() {}

    public SSQConfigModel(SSQCONFIG ssqconfig)
    {
        this.id = ssqconfig.getID();
        this.name = ssqconfig.getNAME();
        this.value = ssqconfig.getVALUE();
    }


}
