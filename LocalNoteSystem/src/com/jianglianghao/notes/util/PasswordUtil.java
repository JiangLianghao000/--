package com.jianglianghao.notes.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 主要是对密码进行加密，AES
 * @verdion
 * @date 2021/4/118:21
 */

public class PasswordUtil {

    /**
     * @AES加密
     * @param password 加密的原文
     * @param key 密钥
     * @param transformation 获取Cipher对象的算法
     * @param algorithm 获取密钥的算法，加密类型
     * @return 密文
     */
    public static String encryptAES(String password, String key, String transformation, String algorithm) throws Exception {
        //获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        //创建加密规则
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        //初始化
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        //开始加密
        byte[] passwordBytes = cipher.doFinal(password.getBytes());
        //使用base64转码
        String encode = Base64.encode(passwordBytes);
        return encode;
    }

    /**
     *
     * @param password 从数据库中获取的密码
     * @param key 密钥
     * @param transformation 获取Cipher对象的算法
     * @param algorithm 获取密钥的算法，加密类型
     * @return 解密结果
     * @throws Exception
     */
    public static String decryptAES(String password, String key, String transformation, String algorithm) throws  Exception{
        //获取Cipher对象
        Cipher cipher = Cipher.getInstance(transformation);
        //指定密钥规则
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        //进行解密
        byte[] bytes = cipher.doFinal(Base64.decode(password));

        return new String(bytes);
    }

    //密钥都定义成一样的
    public static String key = "2429890953123456";
    //获取Cipher对象的算法
    public static String transformation = "AES";
    //获取密钥的算法
    public static String algorithm = "AES";

}
