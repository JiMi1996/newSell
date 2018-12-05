package com.seller.util.key;

import java.util.Random;

/**
 * @program: newsell
 * @description:
 * @author: JiMi
 * @create: 2018-07-19 21:50
 **/
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     */
    public static String qenUniqueKey(){
        Random random = new Random();

        Integer number = random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(number);
    }

}
