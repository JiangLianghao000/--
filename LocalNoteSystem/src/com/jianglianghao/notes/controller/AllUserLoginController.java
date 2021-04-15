package com.jianglianghao.notes.controller;

import com.jianglianghao.notes.bean.LoginMSG;
import com.jianglianghao.notes.bean.SignMSG;
import com.jianglianghao.notes.service.LoginService;
import com.jianglianghao.notes.service.SignService;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 对用户登陆和注册的参数进行处理
 * @verdion
 * @date 2021/3/3122:34
 */

public class AllUserLoginController {
    private LoginService loginService;
    private SignService signService;

    //用户登陆
    public LoginMSG login(String account, String password) throws Exception {
        return new LoginService().login(account, password);
    }

    //管理员登陆
    public LoginMSG adminlogin(String account, String password) throws Exception {
        return new LoginService().adminLogin(account, password);
    }

    //用户注册
    public SignMSG sign(String userName, String account, String password) throws Exception {
        return new SignService().sign(userName, account, password);
    }

    //管理员注册
    public SignMSG adminSign(String userName, String account, String password) throws Exception {
        return new SignService().adiminSign(userName, account, password);
    }


}
