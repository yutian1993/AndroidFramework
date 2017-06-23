package com.yutian.exceptiontext.AIDL;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yutian.exceptiontext.IStudentFace;

/**
 * Created by wuwenchuan on 2017/3/15.
 */
public class CollectService extends Service {

    public CollectService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IStudentFace.Stub {

        @Override
        public String hello() throws RemoteException {
            return "Wu Wenchuan";
        }

        @Override
        public Student getOne() throws RemoteException {
            return new Student();
        }
    }

}
