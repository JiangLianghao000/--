package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.SignMSG;
import com.jianglianghao.notes.controller.AllUserLoginController;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/122:42
 */

public class ManagerSignViewController implements Initializable {

    @FXML
    private TextField userNameSigned;

    @FXML
    private TextField userPasswordSigned;

    @FXML
    private Button backToLoginButton;

    @FXML
    private Button saveAccountButtonToDB;

    @FXML
    private TextField userAccountSigned;

    @FXML
    void signAccount(MouseEvent event) throws Exception {
        //用户注册的名字
        String adminName = userNameSigned.getText();
        //用户注册的账号
        String adminAccount = userAccountSigned.getText();
        //用户注册的密码
        String adminPassword = userPasswordSigned.getText();

        SignMSG adminMSG = new AllUserLoginController().adminSign(adminName, adminAccount, adminPassword);
        if(!adminMSG.getSignResult().equals("注册成功")){
            //显示注册失败界面
            ViewUtil.showView("注册失败界面", adminMSG.getSignResult());
        }
        else{
            //显示注册成功界面
            ViewUtil.showView("注册成功界面", adminMSG.getSignResult());
        }

    }

    @FXML
    void backToLogin(MouseEvent event) throws Exception {
        Stage signInteface = (Stage) backToLoginButton.getScene().getWindow();
        signInteface.close();
        new Main().start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
