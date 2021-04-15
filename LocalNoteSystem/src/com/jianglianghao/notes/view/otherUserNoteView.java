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
 * @date 2021/4/91:05
 */

public class otherUserNoteView extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("otherUserNoteView.fxml"));
        primaryStage.setTitle("笔记展示界面");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("erciyuan.png")));
        primaryStage.setScene(new Scene(root,1200, 750));
        primaryStage.show();
        primaryStage.setResizable(false);
        root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
    }
}
