package com.yutian.utillib.CommonUtil;

/**
 * Created by wuwenchuan on 2017/1/10.
 */
public class FileUtil {

    /**
     * Get file name
     *
     * @param filepath file path or just a string
     * @return file name
     */
    public static String getFileName(String filepath, String split) {
        if (filepath == null || filepath.length() == 0)
            return null;

        String result = null;
        split = split == null ? "/" : split;
        int index = filepath.lastIndexOf(split);
        System.out.println(index);
        if (index != -1) {
            result = filepath.substring(index + 1, filepath.length());
        } else {
            split = "";
            index = filepath.lastIndexOf(split);
            if (index != -1)
                result = filepath.substring(index + 1, filepath.length());
        }

        return result;
    }

}
