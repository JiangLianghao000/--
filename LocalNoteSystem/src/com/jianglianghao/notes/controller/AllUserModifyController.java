package com.jianglianghao.notes.controller;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.service.ModifyService;

import java.sql.Date;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/30:57
 */

public class AllUserModifyController {
    //用户通用的对仓库操作
    public ModifyMSG modifyMSG(Object modifyMSG){
        return new ModifyService().ModifyWareHouse(modifyMSG);
    }

    //用户把笔记传入数据库
    public ModifyMSG modifyMSG(String name, String title, String content, int likeNumber, String group, String publicOrNot, Date currentDate, String user_account){
        return new ModifyService().addNoteToDB(name, title, content, likeNumber, group,  publicOrNot, currentDate, user_account);
    }

    //用户通用的对分组操作
    public ModifyMSG modifyGroupMSG(Object modifyMSG){
        return new ModifyService().ModifyGroup(modifyMSG);
    }

    //用户通用的对笔记的操作
    public ModifyMSG modifyNoteMSG(Object modifyMSG){
        return new ModifyService().ModifyNote(modifyMSG);
    }

    //用户所用的对个人信息的修改
    public ModifyMSG modifyPersonMSG(Object modifyMSG) throws Exception {
        return new ModifyService().modifyMSG(modifyMSG);
    }

    //用户修改笔记
    public ModifyMSG modifyNoteMSG(String title, String content, String publicChoice){
        return new ModifyService().modifyNoteMSG(title, content, publicChoice);
    }

    //用户对笔记点赞的操作
    public ModifyMSG modifyLikeMSG(String yourAccount, String checkAccount, String title){
        return new ModifyService().modifyLikeMSG(yourAccount, checkAccount, title);
    }

    //用户对笔记取消点赞的操作
    public ModifyMSG modifyUnlikeMSG(String yourAccount, String checkAccount, String title){
        return new ModifyService().modifyUnLikeMSG(yourAccount, checkAccount, title);
    }
}
