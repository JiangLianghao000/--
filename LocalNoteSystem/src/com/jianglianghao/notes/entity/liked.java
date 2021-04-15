package com.jianglianghao.notes.entity;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 点赞
 * @verdion
 * @date 2021/4/918:34
 */

public class liked {
    //点赞的人的账号
    private String likePerson;
    //被点赞的人的账号
    private String belikedPerson;
    //被点赞的标题
    private String likeTitle;

    public liked(String likePerson, String belikedPerson, String likeTitle) {
        this.likePerson = likePerson;
        this.belikedPerson = belikedPerson;
        this.likeTitle = likeTitle;
    }

    public liked() {
    }

    public String getLikePerson() {
        return likePerson;
    }

    public void setLikePerson(String likePerson) {
        this.likePerson = likePerson;
    }

    public String getBelikedPerson() {
        return belikedPerson;
    }

    public void setBelikedPerson(String belikedPerson) {
        belikedPerson = belikedPerson;
    }

    public String getLikeTitle() {
        return likeTitle;
    }

    public void setLikeTitle(String likeTitle) {
        this.likeTitle = likeTitle;
    }

    @Override
    public String toString() {
        return "liked{" +
                "likePerson='" + likePerson + '\'' +
                ", belikedPerson='" + belikedPerson + '\'' +
                ", likeTitle='" + likeTitle + '\'' +
                '}';
    }
}
