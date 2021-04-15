package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.controller.AllUserModifyController;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

import static com.jianglianghao.notes.view.LocalNoteInterfaceController.modifyNote;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用户修改笔记的界面
 * @verdion
 * @date 2021/4/815:07
 */

public class UserModifyNoteViewController implements Initializable {

    //文章内容
    @FXML
    private HTMLEditor modifyNoteContent;

    //要修改的文章标题
    @FXML
    private TextField modifyTitle;

    //用于展示htmleditor上面的文本，同时也可以在这里修改之后传回modifyNoteContent
    @FXML
    private TextArea noteText;

    //完成修改
    @FXML
    void finishModify(MouseEvent event) {
        ModifyMSG modifyMSG = new AllUserModifyController().modifyNoteMSG(modifyTitle.getText(), noteText.getText(), choice);
        ViewUtil.showView("修改提示", modifyMSG.getModifyResult());
    }

    //选择公开
    @FXML
    void choicePublic(MouseEvent event) {
        choice = "public";
    }

    //选择私有
    @FXML
    void choicePrivate(MouseEvent event) {
        choice = "private";
    }

    //选择查看按钮，把text区域的文本在htmleditor展示出来
    @FXML
    void showInEditor(MouseEvent event) {
        if (noteText.getText() == null || noteText.getText().equals("")) {
            ViewUtil.showView("错误提示", "还没有输入信息");
        } else {
            //获取内容
            String note = noteText.getText();
            //展示
            modifyNoteContent.setHtmlText(note);
        }
    }

    //把htmleditor中的内容在文本区中展示出来
    @FXML
    void showInText(MouseEvent event) {
        if (modifyNoteContent.getHtmlText() == null || modifyNoteContent.getHtmlText().equals("")) {
            ViewUtil.showView("错误提示", "笔记为空，请输入");
        } else {
            String note = modifyNoteContent.getHtmlText();
            noteText.clear();
            noteText.setText(note);
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //把笔记内容显示在上面
        modifyNoteContent.setHtmlText(modifyNote);
        noteText.setText(modifyNote);
    }

    private String choice;
}
