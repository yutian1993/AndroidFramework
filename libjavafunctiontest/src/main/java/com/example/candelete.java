package com.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class candelete {

    public static void main(String[] args)
    {
        List<Integer> temp = new ArrayList<>();
        temp.set(2, 1);
        for (int temp1: temp
             ) {
            System.out.println(temp1);
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String datetime = "20160101";
//        try {
//            Date date = sdf.parse(datetime);
//            Calendar cla = Calendar.getInstance();
//            cla.setTime(date);
//            System.out.println(cla.get(Calendar.WEEK_OF_YEAR));
//            System.out.println(cla.get(Calendar.DAY_OF_WEEK));
//            System.out.println(cla.get(Calendar.DAY_OF_YEAR));
////            System.out.println(cla.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//        Date temp = new Date();
//        System.out.println(sdf.format(temp));

//        Integer val = 1;
//        String valStr = "'" + val + "'";
//        System.out.println(val.getClass().gett);
//        AkClassTest temp = new AkClassTest();
//        System.out.println(temp.getClass().getSimpleName());
//        Field[] fields = AkClassTest.class.getDeclaredFields();
//        for (Field field:
//                fields ) {
//            Annotation[] annotatios = field.getAnnotations();
//            for (Annotation annotation:
//                    annotatios) {
//                System.out.println(annotation.annotationType() == AkClass.class);
//            }
//            System.out.println(field.getType().getSimpleName());
//        }


//        //判斷某個類是否是注解
//        System.out.println("對象是否為注解類型："+AkClassTest.class.isAnnotation());
//        System.out.println("調用類指定的類注解屬性值："+AkClassTest.class.getAnnotation(AkClass.class).value());
//        //獲取某個具體類的所有注解
//        Annotation[] annotations = AkClassTest.class.getAnnotations();
//        for(Annotation annotation : annotations){
//            //判斷當前注解對象是否為自定義注解
//            if(annotation.annotationType() == AkClass.class){
//                System.out.println("類注解名稱:"+AkClass.class.getSimpleName());
//                Method[] methods = AkClass.class.getDeclaredMethods();
//                for(Method method : methods){
//                    System.out.println("類注解方法："+method.getName());
//                }
//                System.out.println();
//            }
//        }
//        System.out.println("獲取某個類中所有方法的所有方法注解");
//        Method[] methods = AkClassTest.class.getMethods();
//        for(Method method : methods){
//            System.out.println("類方法名："+method.getName());
//            System.out.println("調用方法注解的屬值性："+method.getAnnotation(AkMethod.class).value());
//            Annotation[] mAnnotations = method.getAnnotations();
//            for(Annotation mAnnotation : mAnnotations){
//                if(mAnnotation.annotationType() == AkMethod.class){
//                    System.out.println("注解名："+AkMethod.class.getSimpleName());
//                }
//            }
//        }
//    }
    }
}
