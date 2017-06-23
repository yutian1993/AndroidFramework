// IStudentInterface.aidl
package com.yutian.aidllib;

// Declare any non-default types here with import statements
import com.yutian.aidllib.Student;

interface IStudentInterface {
    String slogan();
    Student getOneStudent();
}
