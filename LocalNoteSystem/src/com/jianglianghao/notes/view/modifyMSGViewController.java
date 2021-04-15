package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.controller.AllUserModifyController;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.jianglianghao.notes.view.LocalNoteInterfaceController.*;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/518:07
 */

public class modifyMSGViewController implements Initializable {

    @FXML
    private RadioButton choicePrivate;

    @FXML
    private RadioButton choicePublic;

    @FXML
    private TextField modifyName;

    @FXML
    private Label modifyMSG;

    @FXML
    private Button modify;

    //点击修改按钮
    @FXML
    void modify(MouseEvent event) {
        //获取修改的名字,可以修改的，也可以是新增的
        String name = modifyName.getText();
        //看操作是什么
        if(targetOperating.equals("新增仓库")){
            //name = 新改的仓库名
            ModifyMSG modifyMSG = new AllUserModifyController().modifyMSG(name);
            ViewUtil.showView("提示框", modifyMSG.getModifyResult());
        }
        if(targetOperating.equals("修改仓库信息")){
            //修改仓库信息
            ModifyMSG modifyMSG = new AllUserModifyController().modifyMSG(name);
            ViewUtil.showView("提示框", modifyMSG.getModifyResult());
        }
        if(targetOperating.equals("新增分组")){
            //新增分组操作
            ModifyMSG modifyMSG = new AllUserModifyController().modifyGroupMSG(name);
            ViewUtil.showView("提示框", modifyMSG.getModifyResult());
        }
        if(targetOperating.equals("修改分组信息")){
            //修改分组信息
            ModifyMSG modifyMSG = new AllUserModifyController().modifyGroupMSG(name);
            ViewUtil.showView("提示框", modifyMSG.getModifyResult());
        }
    }

    @FXML
    void choicePublic(MouseEvent event) {
        //选择公开
        choicePublicOrNot = "public";
    }

    @FXML
    void choicePrivate(MouseEvent event) {
        //选择私有
        choicePublicOrNot = "private";
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modifyMSG.setText(modifyNames);

    }

    public static String choicePublicOrNot;
}
