package com.jianglianghao.notes.service;

import com.jianglianghao.notes.bean.SignMSG;
import com.jianglianghao.notes.dao.NoteAdminDao;
import com.jianglianghao.notes.dao.NoteUserDao;
import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.util.PasswordUtil;
import com.jianglianghao.notes.util.isEmptyUtil;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 对用户和管理员进行注册的管理
 * @verdion
 * @date 2021/3/3122:46
 */

public class SignService {
    private NoteAdminDao noteAdminDao = new NoteAdminDao();
    private NoteUserDao noteUserDao = new NoteUserDao();

    /**
     * @Description 用户注册
     * @param userName
     * @param account
     * @param password
     * @return
     */
    public SignMSG sign(String userName, String account, String password) throws Exception {
        //进行长度判断
        if(password.length() > 20){
            return new SignMSG("密码太长了", null);
        }
        //对密码加密
        String encryptPassword = PasswordUtil.encryptAES(password, PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
        User user = new User();
        if(isEmptyUtil.judgeMSG(account) || isEmptyUtil.judgeMSG(password) || isEmptyUtil.judgeMSG(userName)){
            return new SignMSG("输入了空的注册信息", null);
        }
        user.setUserName(userName);
        user.setPassword(encryptPassword);
        user.setAccount(account);
        SignMSG signMSG = noteUserDao.sign(user);
        return signMSG;
    }

    /**
     * @Description 管理员注册
     * @param adminName
     * @param account
     * @param password
     * @return
     */
    public SignMSG adiminSign(String adminName, String account, String password) throws Exception {
        //进行长度判断
        if(password.length() > 20){
            return new SignMSG("密码太长了", null);
        }
        //对密码加密
        String encryptPassword = PasswordUtil.encryptAES(password, PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
        Admin admin = new Admin();
        if(isEmptyUtil.judgeMSG(account) || isEmptyUtil.judgeMSG(password) || isEmptyUtil.judgeMSG(adminName)){
            return new SignMSG("输入了空的注册信息", null);
        }
        admin.setAdminName(adminName);
        admin.setPassword(encryptPassword);
        admin.setAccount(account);
        SignMSG signMSG = noteAdminDao.sign(admin);
        return signMSG;
    }
}
