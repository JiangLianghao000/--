package com.jianglianghao.notes.view;

import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.PasswordUtil;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/1110:02
 */

public class findPasswordViewController implements Initializable {
    //展示的密码
    @FXML
    private TextField password;
    //输入要查找的账号
    @FXML
    private TextField account;
    //查找
    @FXML
    void find(MouseEvent event) throws Exception {
        password.clear();
        if(account.getText() == null || account.getText().equals("")){
            ViewUtil.showView("错误提示", "没有输入要查找的账号");
        }else{
            String sql = "select user_password password from note_user where  user_account = ?";
            String sql1 = "select admin_password password from note_admin where admin_account = ?";
            User findUserInstance = JDBCUtils.getInstance(User.class, sql, account.getText());
            Admin findAdminInstance = JDBCUtils.getInstance(Admin.class, sql1, account.getText());
            if(findUserInstance != null){
                //找到了，密码解密
                String desPassword = PasswordUtil.decryptAES(findUserInstance.getPassword(), PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
                //展示
                password.setText(desPassword);
            }else if(findAdminInstance != null){
                //找到了，密码解密
                String desPassword1 = PasswordUtil.decryptAES(findAdminInstance.getPassword(), PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
                //展示
                password.setText(desPassword1);
            }else{
                ViewUtil.showView("错误提示", "输入账号有误");
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
