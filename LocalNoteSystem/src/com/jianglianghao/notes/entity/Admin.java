package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 管理员数据
 * @verdion
 * @date 2021/3/3118:28
 */

public class Admin{
    private int id;
    private String adminName;
    private String password;
    private String account;

    public Admin() {
    }

    public Admin(int id, String adminName, String password, String account) {
        this.id = id;
        this.adminName = adminName;
        this.password = password;
        this.account = account;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdminAccountAndPassword{" +
                "adminName='" + adminName + '\'' +
                ", passWord='" + password + '\'' +
                ", account='" + account + '\'' +
                '}';
    }

}
