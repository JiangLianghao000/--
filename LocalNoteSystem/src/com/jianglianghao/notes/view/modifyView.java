package com.jianglianghao.notes.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 修改密码，账号，id，昵称界面
 * @verdion
 * @date 2021/4/32:30
 */

public class modifyView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("modifyView.fxml"));
        primaryStage.setTitle("修改界面");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("erciyuan.png")));
        primaryStage.setScene(new Scene(root,300, 130));
        primaryStage.show();
        primaryStage.setResizable(false);
        root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
    }
}
