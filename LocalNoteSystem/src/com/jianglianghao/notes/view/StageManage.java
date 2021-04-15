package com.jianglianghao.notes.view;

import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.entity.UserNote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 用来管理登陆的时候对象存放，便于页面调用
 * @verdion
 * @date 2021/4/221:18
 */

public class StageManage {
    //存储登陆的用户，当退出登陆之后会销毁
    public static HashMap<String , User> USERS = new HashMap();

    //存储登陆的用户的账号
    public static HashMap<String,String> loginAccount = new HashMap<String,String>();

    //在修改的时候存储点击了什么修改，比如名字修改就保存名字，id修改就保存id
    public static HashMap<String, String> saveModify = new HashMap<>();

    //存储管理员登陆的admin信息,第一个String是admin
    public static HashMap<String, Admin> Admins = new HashMap<>();

    //存储用户添加的文章的基本信息
    public static HashMap<String, UserNote> userNote = new HashMap<>();

    //定义三个集合：相当于Arraylist
    //1. 用于展示仓库
    public static ObservableList<String> showWareHouse = FXCollections.observableArrayList();
    //2. 用于展示笔记分组
    public static ObservableList<String> showNoteGroup = FXCollections.observableArrayList();
    //3. 用于展示笔记
    public static ObservableList<String> showNoteTitle = FXCollections.observableArrayList();

    //定义集合用于存储从数据库读出的笔记
    public static Map<String ,String> note = new HashMap<>();

    //定义一个集合用于存储管理员发布的公告,方便管理员删除
    public static HashMap<String, String> adminAnnounce = new HashMap<>();

    //定义一个map集合在用户搜索别人笔记的时候可以把搜索到相关的笔记和标题放入map中
    public static HashMap<String, String> checkUserNoteByName = new HashMap<>();

    //定义一个map集合在用户搜索别人笔记的时候可以把搜索到相关的笔记和标题放入map中
    public static HashMap<String, String> checkUserNoteByAccount = new HashMap<>();

    //定义一个map集合放置被管理员删除的笔记集合
    public static  HashMap<String, String> deleteByAdmin = new HashMap<>();


}
