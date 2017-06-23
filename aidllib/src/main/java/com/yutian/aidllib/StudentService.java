package com.yutian.aidllib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by wuwenchuan on 2017/3/15.
 */
public class StudentService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new StudentBinder();
    }

    class StudentBinder extends IStudentInterface.Stub {

        @Override
        public String slogan() throws RemoteException {
            return "Hang Xiaoli 加油";
        }

        @Override
        public Student getOneStudent() throws RemoteException {
            Student newStudent = new Student();
            newStudent.name = "Xiaoli";
            newStudent.age = 24;
            newStudent.teacher = "Wenchuan";
            return newStudent;
        }
    }

}
