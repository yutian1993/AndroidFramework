package com.yutian.util;

import java.net.HttpURLConnection;
import java.net.URL;


public class WebUtil {

    /**
     * Judge the url whether exits
     * @param url
     * @return
     */
    public static boolean isWebExit(String url)
    {
        try {
            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
            HttpURLConnection.setFollowRedirects(false);
            //到 URL 所引用的远程对象的连接
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
           /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
            con.setRequestMethod("HEAD");
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}