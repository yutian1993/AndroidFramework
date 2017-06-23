package com.yutian.exceptiontext.AIDL;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuwenchuan on 2017/3/15.
 */
public class Student implements Parcelable {

    public String name;
    public int age;
    public String teacher;

    public Student() {
        this.name = "Xiaoli";
        this.age = 24;
        this.teacher = "Ye";
    }

    protected Student(Parcel in) {
        name = in.readString();
        age = in.readInt();
        teacher = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(teacher);
    }
}
