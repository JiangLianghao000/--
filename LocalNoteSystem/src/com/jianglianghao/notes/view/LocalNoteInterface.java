package com.jianglianghao.notes.view;/**
 * @Description
 * @author JLHWASX   Email:2429890953@qq.com
 * @verdion
 * @date 2021/3/301:23
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LocalNoteInterface extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localNoteInterface.fxml"));
        Parent root = loader.load();
        LocalNoteInterfaceController controller = loader.getController();
        primaryStage.setTitle("笔记本本地界面");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("erciyuan.png")));
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.setMaximized(true);     //设置最大化
        primaryStage.show();
        primaryStage.setResizable(true);
        root.getStylesheets().add(getClass().getResource("TableViewTestCss.css").toExternalForm());
    }
}
