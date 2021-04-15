package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.controller.AllUserModifyController;
import com.jianglianghao.notes.util.ViewUtil;
import com.jianglianghao.notes.util.isEmptyUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/41:06
 */

public class UserWriteNoteViewController implements Initializable {
    //将html文本内容展示在text上
    @FXML
    private TextArea noteContent;
    //编译完成按钮
    @FXML
    private Button editFinishButton;
    //添加分组信息
    @FXML
    private TextField addGroupText;
    //添加标题信息
    @FXML
    private TextField titleText;
    //选择公开按钮
    @FXML
    private RadioButton makeNotePublicButton;
    //笔记内容
    @FXML
    public HTMLEditor noteText;

    //选择私密按钮
    @FXML
    private RadioButton makeNotePrivateButton;

    @FXML
    private ToggleGroup publicOrNot;

    //点击编译完成按键
    @FXML
    void editFinishButton(MouseEvent event) {
        if(privateOrNot == null){
            ViewUtil.showView("错误提示", "请选择私密或者公开");
            return;
        }
        //获取当前日期
        Date currentDate = new Date(new java.util.Date().getTime());
        //获取text文件
        String noteText = noteContent.getText();
        //获取标题
        String title = titleText.getText();
        //获取要添加的分组
        String add_group = addGroupText.getText();
        //判断为null或者""
        boolean judgeMSG = isEmptyUtil.judgeMSG(noteText);
        boolean judgeMSG1 = isEmptyUtil.judgeMSG(title);
        boolean judgeMSG2 = isEmptyUtil.judgeMSG(add_group);
        if(judgeMSG == true || judgeMSG1 == true || judgeMSG2 == true){
            ViewUtil.showButtonHint("错误提示", "输入为空，请检查");
        }else{
            //存入数据库
            String userName = StageManage.USERS.get("user").getUserName();
            String userAccount = StageManage.USERS.get("user").getAccount();
            ModifyMSG addToDBMSG = new AllUserModifyController().modifyMSG(userName, title, noteText, 0, add_group, privateOrNot, currentDate, userAccount);
            ViewUtil.showButtonHint("保存笔记提示", addToDBMSG.getModifyResult());
        }
    }

    @FXML
    void makeNotePublicButton(MouseEvent event) {
        //笔记设置为public
        privateOrNot = "public";
    }

    @FXML
    void makeNotePrivateButton(MouseEvent event) {
        //笔记设置为private
        privateOrNot = "private";
    }

    @FXML
    void showByText(MouseEvent event) {
        noteContent.clear();
        String text = noteText.getHtmlText();
        noteContent.setText(text);
    }

    @FXML
    void showByHtml(MouseEvent event) {
        String htmlText = noteContent.getText();
        noteText.setHtmlText(htmlText);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //选择公开或者隐私
    String privateOrNot;

    public HTMLEditor getNoteText() {
        return noteText;
    }

    public void setNoteText(HTMLEditor noteText) {
        this.noteText =  noteText;
    }
}
