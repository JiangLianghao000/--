package com.jianglianghao.notes.dao;

import com.jianglianghao.notes.bean.LoginMSG;
import com.jianglianghao.notes.bean.SignMSG;
import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.PasswordUtil;

import java.util.List;
import java.util.Random;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/3/3122:36
 */


public class NoteAdminDao {

    //处理管理员注册
    public SignMSG sign(Admin admin){
        //判断账号字数
        if(admin.getAccount().length() > 20){
            return new SignMSG("账号太长,请控制在20字内", null);
        }
        //检查输入的账号和用户名是否为已经注册的:用户表和管理员表
        String checkSql =  "SELECT admin_name adminName, admin_account account FROM note_admin";
        String checkSql1 =  "SELECT user_name userName, user_account account FROM note_user";
        //调用工具类方法
        List<Admin> allAdmin = JDBCUtils.getAllInstance(Admin.class, checkSql);
        for(Admin resultAdmin : allAdmin){
            if(resultAdmin.getAdminName().equals(admin.getAdminName()) || resultAdmin.getAccount().equals(admin.getAccount())){
                return new SignMSG("用户注册信息存在", null);
            }
        }
        List<User> allUser = JDBCUtils.getAllInstance(User.class, checkSql1);
        for(User resultUser : allUser){
            if(resultUser.getUserName().equals(admin.getAdminName()) || resultUser.getAccount().equals(admin.getAccount())){
                return new SignMSG("用户注册信息存在", null);
            }
        }
        //设置user的id:用时间作为种子设置范围在10000的随机数
        long time = System.currentTimeMillis(); //获得当前时间的毫秒数
        Random rd = new Random(time); //作为种子数传入到Random的构造器中
        admin.setId(rd.nextInt(10000));

        String sql = "INSERT INTO note_admin (id, admin_name, admin_account, admin_password)VALUES (?,?,?,?)";
        //实现插入database数据库
        JDBCUtils.update(sql, admin.getId(), admin.getAdminName(), admin.getAccount(),admin.getPassword());
        return new SignMSG("注册成功", null);
    }

    //处理管理员登陆
    public LoginMSG adminLogin(Admin admin) throws Exception {

        String sql = "select admin_password password from note_admin where admin_account = ?";
        Admin instance = JDBCUtils.getInstance(Admin.class, sql, admin.getAccount());
        if(instance == null){
            return new LoginMSG("没有找到该账户", null);
        }else{
            //解密
            String s = PasswordUtil.decryptAES(instance.getPassword(), PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
            if(s.equals(admin.getPassword())){
                return new LoginMSG("账号密码正确", null);
            }else{
                return new LoginMSG("密码不正确", null);
            }
        }
    }
}
