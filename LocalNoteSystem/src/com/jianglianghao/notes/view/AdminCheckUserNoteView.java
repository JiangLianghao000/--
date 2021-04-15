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
 * @date 2021/4/101:29
 */

public class AdminCheckUserNoteView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminCheckUserNoteView.fxml"));
        primaryStage.setTitle("用户笔记情况");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("erciyuan.png")));
        primaryStage.setScene(new Scene(root,1240, 730));
        primaryStage.show();
        primaryStage.setResizable(false);
        root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
    }
}
