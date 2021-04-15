package com.jianglianghao.notes.util;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/1016:14
 */

public class htmlUtil {
    //提纯html文本
    public static String getText(String Html) {
        String text = Html.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        text = text.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return text;
    }
}
