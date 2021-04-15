package com.jianglianghao.notes.util;

import java.util.Random;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用当前时间作为种子来生产随机数
 * @verdion
 * @date 2021/4/115:39
 */

public class timeNumberUtil {
    public synchronized void randomNumber() {
        long time = System.currentTimeMillis(); //获得当前时间的毫秒数
        Random rd = new Random(time); //作为种子数传入到Random的构造器中
    }
}
