package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/717:51
 */

public class AdminManager {
    private String adminName;
    private String blacklist;
    private String announce;

    @Override
    public String toString() {
        return "AdminManager{" +
                "adminName='" + adminName + '\'' +
                ", blacklist='" + blacklist + '\'' +
                ", announce='" + announce + '\'' +
                '}';
    }

    public AdminManager() {
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public AdminManager(String adminName, String blacklist, String announce) {
        this.adminName = adminName;
        this.blacklist = blacklist;
        this.announce = announce;
    }
}
