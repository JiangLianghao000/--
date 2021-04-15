package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.controller.AllUserModifyController;
import com.jianglianghao.notes.entity.UserNote;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static com.jianglianghao.notes.view.LocalNoteInterfaceController.*;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/91:05
 */

public class otherUserNoteViewController implements Initializable {
    //文本编译器，展示笔记的
    @FXML
    private HTMLEditor noteContent;

    //展示全部笔记的
    @FXML
    private ListView<String> allNote;

    //展示搜索的用户名字
    @FXML
    private Label name;

    //用于展示用户的点赞个数的
    @FXML
    private Label likeNumbers;


    //展示笔记
    @FXML
    void showNote(MouseEvent event) {
        if (allNote.getSelectionModel().getSelectedItem() == null) {
            ViewUtil.showView("错误提示", "还没有选择笔记");
        } else {
            String sql = "select like_number 'like' from user_note where author_name = ? and note_title = ?";
            List<UserNote> allInstance = JDBCUtils.getAllInstance(UserNote.class, sql, checkTargetName, allNote.getSelectionModel().getSelectedItem());
            likeNumbers.setText("点赞数：" + allInstance.get(0).getLike());
            noteContent.setHtmlText(StageManage.checkUserNoteByName.get(allNote.getSelectionModel().getSelectedItem()));
        }
    }

    //点赞笔记
    @FXML
    void like(MouseEvent event) {
        if (checkTargetName.equals(StageManage.USERS.get("user").getUserName())) {
            //证明点赞是自己的
            ViewUtil.showView("错误提示", "不可以给自己点赞");
            return;
        }
        if (allNote.getSelectionModel().getSelectedItem() == null) {
            ViewUtil.showView("错误提示", "还没有选择笔记");
        } else {
            ModifyMSG modifyMSG = new AllUserModifyController().modifyLikeMSG
                    (StageManage.USERS.get("user").getAccount(), TargetAccount,
                            allNote.getSelectionModel().getSelectedItem());
            ViewUtil.showView("点赞提示", modifyMSG.getModifyResult());
        }
    }

    //取消点赞笔记
    @FXML
    void unlike(MouseEvent event) {
        if (checkTargetName.equals(StageManage.USERS.get("user").getUserName())) {
            //证明取消点赞是自己的
            ViewUtil.showView("错误提示", "不可以给自己取消点赞");
            return;
        }
        if (allNote.getSelectionModel().getSelectedItem() == null) {
            ViewUtil.showView("错误提示", "还没有选择笔记");
        } else {
            ModifyMSG modifyMSG = new AllUserModifyController().modifyUnlikeMSG
                    (StageManage.USERS.get("user").getAccount(), TargetAccount,
                            allNote.getSelectionModel().getSelectedItem());
            ViewUtil.showView("取消点赞提示", modifyMSG.getModifyResult());
        }
    }

    //根据发布时间排序
    @FXML
    void publicDate(MouseEvent event) {
        //先清空
        allNote.getItems().clear();
        //ORDER BY publish_time
        String sql = "select note_title title from user_note where author_name = ? and public_or_not = 'public' order by publishDate";
        List<UserNote> allInstance = JDBCUtils.getAllInstance(UserNote.class, sql, checkTargetName);
        for (UserNote userNote : allInstance) {
            allNote.getItems().add(userNote.getTitle());
        }
        allNote.refresh();
    }

    //根据点赞数排序
    @FXML
    void likeNumber(MouseEvent event) {
        //先清空
        allNote.getItems().clear();
        String sql = "select note_title title from user_note where author_name = ?  and public_or_not = 'public' order by like_number";
        List<UserNote> allInstance = JDBCUtils.getAllInstance(UserNote.class, sql, checkTargetName);
        for (UserNote userNote : allInstance) {
            allNote.getItems().add(userNote.getTitle());
        }
        allNote.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(checkTargetName + "的笔记");
        Set<String> noteTitle = StageManage.checkUserNoteByName.keySet();
        Iterator<String> title = noteTitle.iterator();
        while (title.hasNext()) {
            allNote.getItems().add(title.next());
        }
        allNote.refresh();
    }
}
