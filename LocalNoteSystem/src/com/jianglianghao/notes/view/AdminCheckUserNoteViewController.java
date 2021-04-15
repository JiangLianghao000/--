package com.jianglianghao.notes.view;

import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.entity.UserGroup;
import com.jianglianghao.notes.entity.UserNote;
import com.jianglianghao.notes.entity.UserWarehouse;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.jianglianghao.notes.view.LocalNoteViewAdminController.searchAccount;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/101:30
 */

public class AdminCheckUserNoteViewController implements Initializable {
    //用户名字
    @FXML
    private Label name;
    //笔记展示区
    @FXML
    private HTMLEditor noteContent;
    //笔记
    @FXML
    private ListView<String> notes;
    //仓库
    @FXML
    private ListView<String> wareHouse;
    //分组
    @FXML
    private ListView<String> group;

    //展示仓库
    @FXML
    void showWareHouse(MouseEvent event) {
        group.getItems().clear();
        if (wareHouse.getSelectionModel().getSelectedItem() == null || wareHouse.getSelectionModel().getSelectedItem().equals("")) {
            ViewUtil.showView("错误提示", "没有选择仓库");
        } else {
            String sql = "select group_name groupName from user_note_group where user_account = ? and wareHouse = ?";
            List<UserGroup> allInstance = JDBCUtils.getAllInstance(UserGroup.class, sql, searchAccount, wareHouse.getSelectionModel().getSelectedItem());
            if (allInstance.size() != 0) {
                for (UserGroup userGroup : allInstance) {
                    group.getItems().add(userGroup.getGroupName());
                }
                group.refresh();
            } else {
                ViewUtil.showView("查看提示", "该仓库没有分组");
            }
        }
    }

    //展示分组
    @FXML
    void showGroup(MouseEvent event) {
        notes.getItems().clear();
        if (group.getSelectionModel().getSelectedItem() == null || group.getSelectionModel().getSelectedItem().equals("")) {
            ViewUtil.showView("错误提示", "没有选择分组");
        } else {
            String sql = "select note_title title from user_note where author_account = ? and note_group = ?";
            List<UserNote> allInstance = JDBCUtils.getAllInstance(UserNote.class, sql, searchAccount, group.getSelectionModel().getSelectedItem());
            if (allInstance.size() != 0) {
                for(UserNote userNote : allInstance){
                    notes.getItems().add(userNote.getTitle());
                }
                notes.refresh();
            }else{
                ViewUtil.showView("错误提示", "该分组没有笔记");
            }
        }
    }

    //展示笔记
    @FXML
    void showNote(MouseEvent event) {
        if(notes.getSelectionModel().getSelectedItem() == null || notes.getSelectionModel().getSelectedItem().equals("")){
            ViewUtil.showView("错误提示", "请选择笔记");
        }else{
            String sql = "select note_content content from user_note where author_account = ? and note_title = ?";
            UserNote instance = JDBCUtils.getInstance(UserNote.class, sql, searchAccount, notes.getSelectionModel().getSelectedItem());
            noteContent.setHtmlText(instance.getContent());
        }
    }

    //删除笔记
    @FXML
    void delete(MouseEvent event) {
        if(notes.getSelectionModel().getSelectedItem() == null || notes.getSelectionModel().getSelectedItem().equals("")){
            ViewUtil.showView("错误提示", "请选择要删除的笔记");
        }else{
            String sql = "delete from user_note where note_title = ? and author_account = ?";
            JDBCUtils.update(sql, notes.getSelectionModel().getSelectedItem(), searchAccount);
            ViewUtil.showView("删除提示", "已删除");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String sql1 = "select user_name userName from note_user where user_account = ?";
        User instance = JDBCUtils.getInstance(User.class, sql1, searchAccount);
        name.setText(instance.getUserName() + "的全部笔记");
        String sql = "select house_name houseName from note_warehouse where user_account = ?";
        List<UserWarehouse> allInstance = JDBCUtils.getAllInstance(UserWarehouse.class, sql, searchAccount);
        if (allInstance.size() != 0) {
            for (UserWarehouse userWarehouse : allInstance) {
                wareHouse.getItems().add(userWarehouse.getHouseName());
            }
        }
    }
}
