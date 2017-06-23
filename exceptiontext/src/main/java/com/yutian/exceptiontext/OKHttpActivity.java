package com.yutian.exceptiontext;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuwenchuan on 2017/3/17.
 */
public class OKHttpActivity extends Activity {

//    @Bind(R.id.m_textview)
//    TextView mTextView;

//    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.m_sensortest)
    public void cancelTask(View view) {
//        if (!myTask.isCancelled() && myTask.getStatus() == AsyncTask.Status.RUNNING) {
//            myTask.cancel(true);
//        }
    }

    @OnClick(R.id.m_clickCatchException)
    public void onclicktest(View view) {
//        myTask = new MyTask();
//        myTask.execute("wuwenchuan");
    }

    @OnClick(R.id.m_clickException)
    public void onclickview(View view) {
        System.out.println("OK Http!");


//        Runnable temp = new Runnable() {
//            @Override
//            public void run() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://blog.csdn.net/lmj623565791/article/details/47911083")
                .build();
        Call call = mOkHttpClient.newCall(request);
//                Response response = null;
//                    response = call.execute();
//                    System.out.println(response.body().string());
//                    System.out.println("End");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Failure");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                System.out.println(response.body().string());

//                Message webinfo = mHandler.obtainMessage();
//                Bundle infor = new Bundle();
//                infor.putString("context", response.body().string());
//                webinfo.what = 1;
//                webinfo.setData(infor);
//                mHandler.sendMessage(webinfo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        try {
//                            mTextView.setText(response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
            }
        });
        System.out.println("Click End");

    }
//        };
//        new Thread(temp).start();
//}

//    final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    System.out.println(msg.getData().getString("context"));
//                    mTextView.setText(msg.getData().getString("context"));
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    class MyTask extends AsyncTask<String, Integer, Long> {
//        boolean forceFinished;
//
//        public MyTask() {
//            super();
//            forceFinished = false;
//        }
//
//        @Override
//        protected Long doInBackground(String... params) {
////            System.out.println(params[0]);
//            Long result = new Long((long)Math.random());
//            for(int index = 1; !forceFinished && index <= 3; ++index) {
//                try {
//                    Thread.sleep(4000);
//                    System.out.println("Index: " + index);
//                    publishProgress((int)(index/3.0f*100));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return result;
//        }
//
//        //onProgressUpdate方法用于更新进度信息
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            mTextView.setText(String.valueOf(values[0]) + "%");
//        }
//
//        //onPreExecute方法用于在执行后台任务前做一些UI操作
//        @Override
//        protected void onPreExecute() {
////            super.onPreExecute();
//            mTextView.setText("Begin Download!");
//        }
//
//        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
//        @Override
//        protected void onPostExecute(Long aLong) {
////            super.onPostExecute(aLong);
//            mTextView.setText(mTextView.getText() + "\n"  + aLong);
//        }
//
//        //onCancelled方法用于在取消执行中的任务时更改UI
//        @Override
//        protected void onCancelled() {
//            mTextView.append("\n Canceled!");
//            forceFinished = true;
//        }
//    }
}
