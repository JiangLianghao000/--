package com.jianglianghao.notes.bean;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/31:22
 */

public class CkeckMSG {
    private String checkResult;
    private String checkMassage;

    @Override
    public String toString() {
        return "CkeckMSG{" +
                "checkResult='" + checkResult + '\'' +
                ", checkMassage='" + checkMassage + '\'' +
                '}';
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckMassage() {
        return checkMassage;
    }

    public void setCheckMassage(String checkMassage) {
        this.checkMassage = checkMassage;
    }

    public CkeckMSG() {
    }

    public CkeckMSG(String checkResult, String checkMassage) {
        this.checkResult = checkResult;
        this.checkMassage = checkMassage;
    }
}
