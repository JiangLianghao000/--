package com.jianglianghao.notes.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/514:53
 */

public class NoteShowViewController implements Initializable {


    @FXML
    private Pagination nowPage;

    @FXML
    void switchPage(MouseEvent event) {
        nowPage.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                int currrentPage = nowPage.getCurrentPageIndex();
                HTMLEditor htmlEditor = new HTMLEditor();
                htmlEditor.setHtmlText(eatchPageString[currrentPage]);
                return htmlEditor;
            }
        });
    }

    public void ini() {
        noteContent = StageManage.note.get("笔记");
        //查看笔记长度
        int length = noteContent.length();
        //查看一共有多少页
        int allPage = length / 3000;
        if (length % 3000 != 0) {
            //没除够,页数加一
            allPage++;
        }
        int number = 0;
        //初始化字符串数组
        for (int i = 0; i < allPage; i++) {
            if (allPage == 1) {
                //只有一页
                eatchPageString[0] = noteContent.substring(0, 0 + length);
            } else {
                //有多页
                if (i != allPage - 1) {
                    //没有到最后一页
                    eatchPageString[i] = noteContent.substring(number, number + 3000);
                    number += 3000;
                } else {
                    //到了最后一页
                    eatchPageString[i] = noteContent.substring(number, length);
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ini();
    }

    //专门存放note，用于显示note的时候限制3000字一页
    public static String noteContent;
    public static String[] eatchPageString = new String[20];

}
