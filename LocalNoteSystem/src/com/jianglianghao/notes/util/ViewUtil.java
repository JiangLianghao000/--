package com.jianglianghao.notes.util;

import com.jianglianghao.notes.entity.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用来封装一些显示的视图，比如注册成功界面，登陆失败界面
 * @verdion
 * @date 2021/4/114:14
 */

public class ViewUtil {

    /**
     * @param title 界面标题
     * @param text 界面内容
     * @Description 用于显示一些中等界面，参数一：界面的标题，参数二，界面的提示信息
     */
    public static void showView(String title, String text) {
        //应该封装成一个方法
        Stage showErrorStage = new Stage();
        showErrorStage.setMaximized(false);
        showErrorStage.setTitle(title);
        //实现布局1
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);


        showErrorStage.setHeight(200);
        showErrorStage.setWidth(300);
        Text errorText = new Text(5, 5, text);
        errorText.setFont(Font.font("宋体", FontWeight.BOLD, FontPosture.ITALIC, 24));
        pane.getChildren().add(errorText);
        //显示错误信息
        showErrorStage.setScene(new Scene(pane, 100, 100));
        showErrorStage.show();
    }

    /**
     * @param title 界面标题
     * @param labelContent 标签内容
     * @Description 展示一个小提示界面，如显示密码成功提示
     */
    public static void showButtonHint(String title, String labelContent) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Group root = new Group();
        Scene scene = new Scene(root, 300, 130, Color.WHITE);
        GridPane gridpane = new GridPane();
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        Label label = new Label(labelContent);
        GridPane.setHalignment(label, HPos.CENTER);
        gridpane.add(label, 10, 5);
        root.getChildren().add(gridpane);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param user 用户对象
     * @Description 展示一个用户的信息
     */
    public static void showUserMSG(User user) {
        Stage stage = new Stage();
        stage.setTitle("查找的用户信息");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 200, Color.WHITE);
        GridPane gridpane = new GridPane();
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        Label userId = new Label("用户id = " + user.getId());
        Label userName = new Label("用户名字= " + user.getUserName());
        Label userAccount = new Label("用户账号 = " + user.getAccount());
        gridpane.add(userId, 11, 5);
        gridpane.add(userName, 11, 6);
        gridpane.add(userAccount, 11, 7);

        root.getChildren().add(gridpane);
        stage.setScene(scene);
        stage.show();

    }
}
