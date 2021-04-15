package com.jianglianghao.notes.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/41:05
 */

public class UserWriteNoteView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserWriteNote.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("用户编写笔记页面");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("erciyuan.png")));
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.setMaximized(false);     //设置最大化
        primaryStage.show();
        primaryStage.setResizable(false);
        root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
    }
}
