package com.jianglianghao.notes.service;

import com.jianglianghao.notes.bean.LoginMSG;
import com.jianglianghao.notes.dao.NoteAdminDao;
import com.jianglianghao.notes.dao.NoteUserDao;
import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.util.isEmptyUtil;
/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 处理用户和管理员登陆
 * @verdion
 * @date 2021/3/3122:34
 */

public class LoginService {
    private NoteAdminDao noteAdminDao = new NoteAdminDao();
    private NoteUserDao noteUserDao = new NoteUserDao();

    //用户登陆
    public LoginMSG login(String account, String password) throws Exception {
        User user = new User();
        if(isEmptyUtil.judgeMSG(account) || isEmptyUtil.judgeMSG(password)){
            return new LoginMSG("输入的账号或者密码为空", null);
        }
        user.setAccount(account);
        user.setPassword(password);
        LoginMSG loginMSG = noteUserDao.login(user);
        return loginMSG;
    }

    //管理员登陆
    public LoginMSG adminLogin(String account, String password) throws Exception {
        Admin admin = new Admin();
        if(isEmptyUtil.judgeMSG(account) || isEmptyUtil.judgeMSG(password)){
            return new LoginMSG("输入的账号或者密码为空", null);
        }
        admin.setAccount(account);
        admin.setPassword(password);
        LoginMSG loginMSG = noteAdminDao.adminLogin(admin);
        return loginMSG;
    }
}
