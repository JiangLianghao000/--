package com.jianglianghao.notes.bean;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用于返回修改的信息的结果
 * @verdion
 * @date 2021/4/222:32
 */

public class ModifyMSG {
    private String modifyResult;
    private String modifyMassage;

    public ModifyMSG(String modifyResult, String modifyMassage) {
        this.modifyResult = modifyResult;
        this.modifyMassage = modifyMassage;
    }

    public ModifyMSG() {
    }

    public String getModifyResult() {
        return modifyResult;
    }

    public void setModifyResult(String modifyResult) {
        this.modifyResult = modifyResult;
    }

    public String getModifyMassage() {
        return modifyMassage;
    }

    public void setModifyMassage(String modifyMassage) {
        this.modifyMassage = modifyMassage;
    }

    @Override
    public String toString() {
        return "ModifyMSG{" +
                "modifyResult='" + modifyResult + '\'' +
                ", modifyMassage='" + modifyMassage + '\'' +
                '}';
    }
}
