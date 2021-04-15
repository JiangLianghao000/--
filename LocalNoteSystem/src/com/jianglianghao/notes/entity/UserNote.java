package com.jianglianghao.notes.entity;

import java.sql.Date;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 数据库用户笔记表的类
 * @verdion
 * @date 2021/4/412:09
 */

public class UserNote {
    private String name;
    private String title;
    private String content;
    private String group;
    //点赞个数
    private int like;
    private String publicOrNot;
    private Date publishDate;
    private String authorAccount;

    public UserNote() {
    }

    public UserNote(String name, String title, String content, String group, int like, String publicOrNot, Date publishDate, String authorAccount) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.group = group;
        this.like = like;
        this.publicOrNot = publicOrNot;
        this.publishDate = publishDate;
        this.authorAccount = authorAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getPublicOrNot() {
        return publicOrNot;
    }

    public void setPublicOrNot(String publicOrNot) {
        this.publicOrNot = publicOrNot;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    @Override
    public String toString() {
        return "UserNote{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", group='" + group + '\'' +
                ", like=" + like +
                ", publicOrNot='" + publicOrNot + '\'' +
                ", publishDate=" + publishDate +
                ", authorAccount='" + authorAccount + '\'' +
                '}';
    }
}
