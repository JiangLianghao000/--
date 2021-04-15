package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.controller.AllUserModifyController;
import com.jianglianghao.notes.util.ViewUtil;
import com.jianglianghao.notes.util.isEqualUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/32:31
 */

public class modifyViewController implements Initializable {

    @FXML
    private Button modifyButton;

    @FXML
    private TextField newMSGAgain;

    @FXML
    private Label MSGLabel;

    @FXML
    private TextField newMSG;

    @FXML
    void modifyButton(MouseEvent event) throws Exception {
        //记录输入的新的信息,因为是一个通用操作，所以用Object来记录
        Object modifyObject = newMSG.getText();
        Object modifyObjectAgain = newMSGAgain.getText();
        //判断表是否有输入
        if (modifyObject.equals("") || modifyObjectAgain.equals("")) {
            ViewUtil.showButtonHint("错误提示", "输入为空");
            return;
        }
        //有输入就判断两次输入是否相等
        boolean judgeEqual = isEqualUtil.judgeEqual(modifyObject, modifyObjectAgain);
        if (judgeEqual == true) {//一致的
            //对信息处理
            ModifyMSG modifyMSG = new AllUserModifyController().modifyPersonMSG(modifyObject);
            ViewUtil.showButtonHint("成功提示", modifyMSG.getModifyResult());
        } else {
            ViewUtil.showButtonHint("错误提示", "两次输入不一样");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<String> strings = StageManage.saveModify.keySet();
        String next = strings.iterator().next();
        MSGLabel.setText(next);
    }


}
