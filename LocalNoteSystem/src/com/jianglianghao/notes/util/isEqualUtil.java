package com.jianglianghao.notes.util;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/38:49
 */

public class isEqualUtil {

    /**
     * @Description 判断两个对象值是否相同
     * @param o1 第一个对象
     * @param o2 第二个对象
     * @return
     */
    public static boolean judgeEqual(Object o1, Object o2){
        if(o1.equals(o2) == true){
            return true;
        }else{
            return false;
        }
    }
}
