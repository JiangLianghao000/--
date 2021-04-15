/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用于写按钮控制的
 * @verdion
 * @date 2021/3/2917:44
 */


package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.LoginMSG;
import com.jianglianghao.notes.controller.AllUserLoginController;
import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class mainController implements Initializable {

    @FXML
    private Button managerSignButton;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private TextField account;

    @FXML
    private Button userSignButton;

    @FXML
    private Button adminLogin;

    //用户登陆
    @FXML
    void loginButtonClicked(MouseEvent event) throws Exception {
        //登陆界面
        Stage Main = (Stage) loginButton.getScene().getWindow();
        //判断登陆信息正确，如果正确，进入系统，错误，显示窗口信息：登陆错误
        //获取登陆时输入的信息
        String userAccount = account.getText();
        String userPassword = password.getText();

        LoginMSG login = new AllUserLoginController().login(userAccount, userPassword);
        if (login != null && login.getLoginResult().equals("账号密码正确")) {
            //关闭主界面
            Main.close();
            //创建stage
            Stage stage = new Stage();
            //根据账号获取用户信息
            User user = JDBCUtils.searchName(userAccount);
            //存入map中
            StageManage.USERS.put("user", user);
            new LocalNoteInterface().start(stage);

        } else {
            ViewUtil.showView("错误信息提示", login.getLoginResult());
        }
    }

    //管理员注册
    @FXML
    void managerSignButtonClicker(MouseEvent event) throws Exception {
        Stage Main = (Stage) loginButton.getScene().getWindow();
        Main.close();
        new ManagerSignView().start(new Stage());
    }

    //用户注册
    @FXML
    void userSignButtonClicked(MouseEvent event) throws Exception {
        Stage Main = (Stage) loginButton.getScene().getWindow();
        Main.close();
        new SignInterface().start(new Stage());
    }

    //管理员登陆
    @FXML
    void adminLogin(MouseEvent event) throws Exception {
        //登陆界面
        Stage Main = (Stage) adminLogin.getScene().getWindow();
        //判断登陆信息正确，如果正确，进入系统，错误，显示窗口信息：登陆错误
        //获取登陆时输入的信息
        String adminAccount = account.getText();
        String adminPassword = password.getText();

        //获取管理员登陆的返回信息
        LoginMSG adminsLogin = new AllUserLoginController().adminlogin(adminAccount, adminPassword);
        if (adminsLogin != null && adminsLogin.getLoginResult().equals("账号密码正确")) {
            //把管理员信息存入map
            Admin admin = JDBCUtils.searchAdmin(adminAccount);
            StageManage.Admins.put("admin", admin);
            //关闭主界面
            Main.close();
            //显示用户界面
            new LocalNoteViewAdmin().start(new Stage());
        } else {
            ViewUtil.showView("错误信息提示", adminsLogin.getLoginResult());
        }
    }

    //找回密码
    @FXML
    void findPassword(MouseEvent event) throws Exception {
        new findPasswordView().start(new Stage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getAccount() {
        return account.getText();
    }

    public User getUser() {
        String account = this.account.getText();
        User user = JDBCUtils.searchName(account);
        return user;
    }
}
