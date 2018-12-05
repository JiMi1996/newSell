package com.seller.test;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-21 15:24
 **/
public class javaTest {
    //全局变量有默认值，局部咩有
    static int c1;

    public static void main(String []args){
        int i = 128;
        byte b = (byte)i;
        System.out.println(b);


        int c2;
        //局部变量需要赋值才可以使用
        c2 = 1;
        System.out.println(c1);
        System.out.println(c2);

        int a = 60;
        System.out.println(a >> 2);
        System.out.println(a >>> 2);
    }
}
