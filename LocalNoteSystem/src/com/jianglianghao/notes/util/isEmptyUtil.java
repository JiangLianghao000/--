package com.jianglianghao.notes.util;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/3/3122:49
 */

public class isEmptyUtil {
    /**
     * @param MSG 要判断的信息
     * @return
     * @Description 没有数据或者null返回true
     */
    public static boolean judgeMSG(String MSG) {
        if (MSG == null || MSG.equals("")) {
            return true;
        }
        return false;
    }
}
