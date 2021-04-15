package com.jianglianghao.notes.service;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.dao.NoteUserDao;

import java.sql.Date;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 对笔记操作
 * @verdion
 * @date 2021/4/32:12
 */

public class ModifyService {
    private NoteUserDao userDao = new NoteUserDao();

    //修改笔记和个人信息
    public ModifyMSG modifyMSG(Object modifyMSG) throws Exception {
        ModifyMSG modify = userDao.modify(modifyMSG);
        if (modify != null && modify.getModifyResult().equals("修改成功，请重新登陆查看")) {
            return modify;
        }
        return modify;
    }

    //添加笔记
    public ModifyMSG addNoteToDB(String name, String title, String content, int likeNumber, String group, String publicOrNot, Date currentDate, String user_account) {
        ModifyMSG modify = userDao.addNoteToDB(name, title, content, likeNumber, group, publicOrNot, currentDate, user_account);
        if (modify != null && modify.getModifyResult().equals("添加笔记成功")) {
            return modify;
        }
        return modify;
    }

    //对仓库进行操作
    public ModifyMSG ModifyWareHouse(Object modifyMSG) {
        ModifyMSG modify = userDao.modifyWareHouse(modifyMSG);
        return modify;
    }

    //对分组进行操作
    public ModifyMSG ModifyGroup(Object modifyMSG) {
        ModifyMSG modify = userDao.modifyGroup(modifyMSG);
        return modify;
    }

    //对笔记进行操作
    public ModifyMSG ModifyNote(Object modifyMSG) {
        ModifyMSG modify = userDao.modifyNote(modifyMSG);
        return modify;
    }

    //对笔记进行修改
    public ModifyMSG modifyNoteMSG(String title, String content, String publicChoice) {
        ModifyMSG modify = userDao.modifyNoteMSG(title, content, publicChoice);
        return modify;
    }

    //对用户笔记进行点赞操作
    public ModifyMSG modifyLikeMSG(String YourAccount, String checkAccount, String title) {
        ModifyMSG modifyLike = userDao.modifyUserNoteToLike(YourAccount, checkAccount, title);
        return modifyLike;
    }

    //对用户笔记进行取消点赞操作
    public ModifyMSG modifyUnLikeMSG(String YourAccount, String checkAccount, String title) {
        ModifyMSG modifyLike = userDao.modifyUserToUnlike(YourAccount, checkAccount, title);
        return modifyLike;
    }
}
