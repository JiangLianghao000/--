package com.jianglianghao.notes.bean;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/3/3122:43
 */

public class SignMSG {
    private String signResult;
    private String signMassage;

    public SignMSG() {
    }

    public SignMSG(String signResult, String signMassage) {
        this.signResult = signResult;
        this.signMassage = signMassage;
    }

    public String getSignResult() {
        return signResult;
    }

    public void setSignResult(String signResult) {
        this.signResult = signResult;
    }

    public String getSignMassage() {
        return signMassage;
    }

    public void setSignMassage(String signMassage) {
        this.signMassage = signMassage;
    }

    @Override
    public String toString() {
        return "SignMSG{" +
                "signResult='" + signResult + '\'' +
                ", signMassage='" + signMassage + '\'' +
                '}';
    }
}
