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
 * @date 2021/3/288:07
 */

public class Main extends Application {

    @Override
        public void start (Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            primaryStage.setTitle("笔记本管理系统");
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("erciyuan.png")));
            primaryStage.setScene(new Scene(root,650, 500));
            primaryStage.show();
            primaryStage.setResizable(false);
            root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
        }

        public static void main (String[]args){
            launch(args);
        }

}
