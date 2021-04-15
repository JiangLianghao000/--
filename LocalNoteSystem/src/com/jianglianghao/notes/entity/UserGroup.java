package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 笔记分组管理，第一个是笔记名，第二个是组名1
 * @verdion
 * @date 2021/4/412:10
 */

public class UserGroup {
    private String noteTitle;
    private String groupName;
    private String wareHouseName;
    private String userAccount;
    private String privateOrNot;

    public UserGroup() {
    }

    public UserGroup(String noteTitle, String groupName, String wareHouseName, String userAccount, String privateOrNot) {
        this.noteTitle = noteTitle;
        this.groupName = groupName;
        this.wareHouseName = wareHouseName;
        this.userAccount = userAccount;
        this.privateOrNot = privateOrNot;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "noteTitle='" + noteTitle + '\'' +
                ", groupName='" + groupName + '\'' +
                ", wareHouseName='" + wareHouseName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", privateOrNot='" + privateOrNot + '\'' +
                '}';
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPrivateOrNot() {
        return privateOrNot;
    }

    public void setPrivateOrNot(String privateOrNot) {
        this.privateOrNot = privateOrNot;
    }
}
