package com.seller.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-08-15 11:30
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class FunctionalInterfaceTest {

    @FunctionalInterface
    interface Check<T extends String>{
        boolean check(T t);
    }
    @FunctionalInterface
    interface Out<T>{
        void achievement(T t);
    }

   public void rollCall(List<? extends String> list,Check check,Out out){
        for(String name : list){
            if(check.check(name)){
                out.achievement(name);
            }
        }
   }

    @Test
    public void testLambda(){
        List<String> demoList = Arrays.asList("小明","吉米","老汤","小芳");
        rollCall(demoList,
                name -> name.startsWith("小"),
                name -> {
                    String rate = name + "不是单身狗";
                    System.out.println(rate);
                }
        );
    }
}
