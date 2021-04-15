package com.jianglianghao.notes.view;

import com.jianglianghao.notes.util.PasswordUtil;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/223:05
 */

public class showMSGViewController implements Initializable {
    //修改用户昵称
    @FXML
    private Button modifyName;
    //修改用户密码
    @FXML
    private Button modifyPassword;

    //用户账号
    @FXML
    private Label userAccount;
    //查看用户密码
    @FXML
    private Button showPassword;
    //用户昵称
    @FXML
    private Label userName;
    //用户ID
    @FXML
    private Label userID;


    //修改姓名
    @FXML
    void modifyName(MouseEvent event) throws Exception {
        StageManage.saveModify.clear();
        //跳转到修改界面
        StageManage.saveModify.put("userName", "user_name");
        modifyView modifyView = new modifyView();
        modifyView.start(new Stage());
    }


    //修改密码
    @FXML
    void modifyPassword(MouseEvent event) throws Exception {
        //首先对StageManeger中的saveModify清空，因为这个作用其实是保存了你点击的修改按键，不能让
        //已存在的按键影响判断
        StageManage.saveModify.clear();
        //放入修改的信息
        StageManage.saveModify.put("password", "user_password");
        //展示修改界面
        modifyView modifyView = new modifyView();
        modifyView.start(new Stage());
    }

    //查看密码
    @FXML
    void showPassword(MouseEvent event) throws Exception {
        String userPassword = PasswordUtil.decryptAES(StageManage.USERS.get("user").getPassword(), PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
        ViewUtil.showButtonHint("密码查看窗口", "密码：" + userPassword);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //显示用户id，name，账号，密码要单独显示
        userName.setText(StageManage.USERS.get("user").getUserName());
        userID.setText(String.valueOf(StageManage.USERS.get("user").getId()));
        userAccount.setText(StageManage.USERS.get("user").getAccount());
    }
}
