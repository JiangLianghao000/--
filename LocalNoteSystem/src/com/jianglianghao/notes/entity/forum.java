package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 论坛类，只是存有用户发表的评论
 * @verdion
 * @date 2021/4/81:39
 */

public class forum {
    private String user;
    private String userChat;

    public forum() {
    }

    public forum(String user, String userChat) {
        this.user = user;
        this.userChat = userChat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserChat() {
        return userChat;
    }

    public void setUserChat(String user_chat) {
        this.userChat = user_chat;
    }

    @Override
    public String toString() {
        return "forum{" +
                "user='" + user + '\'' +
                ", user_chat=" + userChat +
                '}';
    }
}
