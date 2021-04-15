package com.jianglianghao.notes.view;

import com.jianglianghao.notes.entity.forum;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/80:55
 */

public class ForumViewController implements Initializable {

    //评论内容
    @FXML
    private TextField comment;

    //用户评论总和
    @FXML
    private ListView<String> chatList;


    @FXML
    void deliver(MouseEvent event) {
        String text = null;
        //判断有没有输入评论
        if(comment.getText() == null || comment.getText().equals("")){
            ViewUtil.showView("错误提示", "没有输入评论");
            return ;
        }
        if(StageManage.USERS.size() != 0){
            //证明登陆的是用户
            text = "用户 " + StageManage.USERS.get("user").getUserName() + ":" + comment.getText();
            String sql = "insert into forum values(?, ?)";
            //把评论内容放入数据库
            JDBCUtils.update(sql,text,StageManage.USERS.get("user").getUserName());
        }
        if(StageManage.Admins.size() != 0){
            //证明登陆的是管理员
            text = "管理员 " + StageManage.Admins.get("admin").getAdminName() + ":" + comment.getText();
            String sql = "insert into forum values(?, ?)";
            //把评论内容放入数据库
            JDBCUtils.update(sql,text,StageManage.Admins.get("admin").getAdminName());
        }
        //把评论显示在评论区
        chatList.getItems().add(text);
        chatList.refresh();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //导入原有的数据库里面存在的评论
        String sql = "select user_chat userChat from forum where user_chat is not null";
        List<forum> allInstance = JDBCUtils.getAllInstance(forum.class, sql);
        if(allInstance.size() != 0){
            //表示有评论，添加进listview
            for(forum forum : allInstance){
                chatList.getItems().add(String.valueOf(forum.getUserChat()));
            }
        }
    }
}
