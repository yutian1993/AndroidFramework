package com.yutian.exceptiontext;

import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yutian.aidllib.IStudentInterface;
import com.yutian.aidllib.Student;
import com.yutian.exceptiontext.widget.CircleView;
import com.yutian.utillib.CommonUtil.ImageUtil;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

//    @Bind(R.id.m_imageviewtest)
//    PictureView mImageView;

//    @Bind(R.id.m_bezierview)
//    CustomView mBezierView;

//    @Bind(R.id.mBezierView)
//    BezierView mBezierView;

//    private IStudentInterface iStudentInterface;
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            System.out.println("connect student service");
//
//            iStudentInterface = IStudentInterface.Stub.asInterface(service);
//            try {
//                System.out.println("wuwenchuan" + iStudentInterface.hello());
//                final Student one = iStudentInterface.getOne();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("wuwenchuan" + one.name);
//                        System.out.println("wuwenchuan" + one.age);
//                        System.out.println("wuwenchuan" + one.teacher);
//                    }
//                });
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            iStudentInterface = null;
//            System.out.println("disconnect student service");
//        }
//    };
    private boolean bindService;
    private IStudentInterface mInterface;
    private static int mInfoStatus = 1;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mInterface = IStudentInterface.Stub.asInterface(service);

            if (mInterface != null) {
                try {
                    System.out.println(mInterface.slogan());
                    Student one = mInterface.getOneStudent();
                    System.out.println(one.name);
                    System.out.println(one.age);
                    System.out.println(one.teacher);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Null");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.chara_fujii_ph));

//        if (mInfoStatus > 0) {
//            startActivity(new Intent().setClass(this, MainActivity.class));
//            --mInfoStatus;
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Intent in = new Intent();
        in.setClassName(this, "com.yutian.aidllib.StudentService");
        in.setPackage("com.yutian.aidllib");
        in.setAction("com.yutian.aidllib.StudentService");
        bindService = bindService(in, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bindService)
            unbindService(mServiceConnection);
    }

    @Bind(R.id.m_textview)
    public TextView m_textview;

    @OnClick(R.id.m_clickException)
    public void click(View view) {
//        int divide = 0;
//        int mDivide = 1;
//
//        for (int i = 0; i < 10 ; ++i) {
//            System.out.println(mDivide/divide);
//        }
//        Matrix matrix = mImageView.getImageMatrix();
//        RectF drawableRect = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
//        RectF viewRect = new RectF(0, 0, (float)(mImageView.getWidth()*2), (float)(mImageView.getHeight()*2));
//        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
//        matrix.postTranslate(12, 12);
//        mImageView.setImageMatrix(matrix);
//        mImageView.invalidate();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient mClient = new OkHttpClient();
//                Request newRequest = new Request.Builder().url("http://www.51zxw.net/show.aspx?id=27296&cid=410").build();
//                Call mCall = mClient.newCall(newRequest);
//                Response newRep = null;
//                try {
//                    newRep = mCall.execute();
//                    System.out.println(newRep.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//        System.out.println("toString '" + m_textview.getText().toString() + "'");

        List alldata = listAvaliableStorage(this);

        String results ="Begin : ---->" + alldata.size() + "\n";
        for ( Object info : alldata) {
            results += ((StorageInfo)info).path + "\n";
        }
        m_textview.setText(results);

    }

    public static class StorageInfo {
        public String path;
        public String state;
        public boolean isRemoveable;

        public StorageInfo(String path) {
            this.path = path;
        }

        public boolean isMounted() {
            return "mounted".equals(state);
        }
    }

    public static List listAvaliableStorage(Context context) {
        ArrayList storagges = new ArrayList();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumePaths", paramClasses);
//            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
//                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) obj;//(String) getPath.invoke(obj, new Object[0]);
                    System.out.println(path);
                    info = new StorageInfo(path);
//                    File file = new File(info.path);
//                    if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
//                        Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
//                        String state = null;
//                        try {
//                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
//                            state = (String) getVolumeState.invoke(storageManager, info.path);
//                            info.state = state;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        if (info.isMounted()) {
//                            info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                            storagges.add(info);
//                        }
//                    }
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        storagges.trimToSize();

        return storagges;
    }

    @OnClick(R.id.m_clickCatchException)
    public void catchException(View view) {
//        int divide = 0;
//        int mDivide = 1;
//
//        try {
//            for (int i = 0; i < 10 ; ++i) {
//                System.out.println(mDivide/divide);
//            }
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(takePictureIntent,1024);

//        ContentResolver temp;
//        temp.openOutputStream("");

//        try {
//            RecoverySystem.installPackage(getApplicationContext(), new File("/data/media/0/ota/update.zip"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        System.out.println(telephonyManager.getPhoneCount());
//        System.out.println(telephonyManager.getDeviceId());

//        System.out.println("Begin exception");
//
//        ExceptionInInitializerError temp = new ExceptionInInitializerError("Just fuck");
//
//        temp.printStackTrace();
//
//        System.out.println("End exception");
//
//        System.err.println();

        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidCAStore");
            keyStore.load(null, null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("End");

    }

//    @Bind(R.id.m_circleview)
    public CircleView m_circleview;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap)extras.get("data");
        m_circleview.setBackground(new BitmapDrawable(imageBitmap));

    }

    @OnClick(R.id.m_sensortest)
    public void sensorlog(View view) {
//        SensorManager sensormanager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
//        Sensor lightSensor = sensormanager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        if (lightSensor != null) {
//            Toast.makeText(this,"Has sensor", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this,"Has sensor", Toast.LENGTH_SHORT).show();
//        }

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });



//        mBezierView.startAnimator();

        Bitmap bitmap = Bitmap.createBitmap(300*2*10,300*2*10, Bitmap.Config.ARGB_8888);
        for (int i = 200; i > 100; --i) {
            Bitmap temp = generateBitmap(bitmap, i*2);
            ImageUtil.saveImageToStorage(temp, "/storage/emulated/0/"+i+".png");
        }
        bitmap.recycle();
    }


    public void apiTestInfor() {
        JsonObjectTest temp = new JsonObjectTest();

        temp.name = "wuwenchuan";
        temp.teacher = "lili";

//        JSONObject objTemp = new JSONObject(temp);
        NotificationManager tNotification = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    }

    class JsonObjectTest {
        private String name;
        private String teacher;

        public String NAME = "name";
        public String TEACHER = "teacher";
    }

    public Bitmap generateBitmap(Bitmap bitmap, int size) {
        size = size * 10;
        bitmap.reconfigure(size, size, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.RED);
        Canvas temp = new Canvas(bitmap);
        temp.clipRect(0,0,size,size);
        return bitmap;
    }

}
