package com.yutian.base.util;

/**
 * Created by wuwenchuan on 16-7-20.
 */
public enum SizeKindUtil {

    B("b", 1),
    KB("kb", 1024),
    MB("mb", 1048576),
    GB("gb", 1073741824),
    TB("tb", 1024*1024);      //Integer too large, need double

    public String kind;
    public int standardSize;

    private SizeKindUtil(String kind, int size)
    {
        this.kind = kind;
        this.standardSize = size;
    }

    public String getKind() {
        return kind;
    }

    public int getStandardSize() {
        return standardSize;
    }

    public static String getSize(int length)
    {
        if (length < 1024)
        {
            return new String(length + B.getKind());
        } else if (length < 1048576)
        {
            return new String(length/KB.getStandardSize() + KB.getKind());
        } else if (length < 1073741824)
        {
            return new String(length/MB.getStandardSize() + MB.getKind());
        } else
        {
            return new String(length/GB.getStandardSize() + GB.getKind());
        }
    }
}
