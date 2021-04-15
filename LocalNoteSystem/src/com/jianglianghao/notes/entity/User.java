package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用户登陆信息
 * @verdion
 * @date 2021/3/300:56
 */

public class User {
    private int id;
    private String userName;
    private String account;
    private String password;


    public User() {
    }

    public User(int id, String userName, String account, String password) {
        this.id = id;
        this.userName = userName;
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAccountAndPassword{" +
                "user='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    };

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
