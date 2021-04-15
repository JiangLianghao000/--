package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/418:35
 */

public class UserWarehouse {
    private String houseName;
    private String groupName;
    private String privateOrNot;
    private String userAccount;

    public UserWarehouse() {
    }

    public UserWarehouse(String houseName, String groupName, String privateOrNot, String userAccount) {
        this.houseName = houseName;
        this.groupName = groupName;
        this.privateOrNot = privateOrNot;
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "UserWarehouse{" +
                "houseName='" + houseName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", privateOrNot='" + privateOrNot + '\'' +
                ", userAccount='" + userAccount + '\'' +
                '}';
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPrivateOrNot() {
        return privateOrNot;
    }

    public void setPrivateOrNot(String privateOrNot) {
        this.privateOrNot = privateOrNot;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
