package com.jianglianghao.notes.bean;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 返回一个结果信息
 * @verdion
 * @date 2021/3/3122:41
 */

public class LoginMSG {
    private String loginResult;
    private String loginMassage;

    public LoginMSG() {
    }

    public LoginMSG(String loginResult, String loginMassage) {
        this.loginResult = loginResult;
        loginMassage = loginMassage;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setOginResult(String oginResult) {
        this.loginResult = oginResult;
    }

    public String getLodingMassage() {
        return loginMassage;
    }

    public void setLodingMassage(String lodingMassage) {
        loginMassage = lodingMassage;
    }

    @Override
    public String toString() {
        return "LoginMSG{" +
                "oginResult='" + loginResult + '\'' +
                ", LodingMassage='" + loginMassage + '\'' +
                '}';
    }
}
