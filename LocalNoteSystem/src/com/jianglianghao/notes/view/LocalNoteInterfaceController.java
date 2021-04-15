package com.jianglianghao.notes.view;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.controller.AllUserModifyController;
import com.jianglianghao.notes.entity.AdminManager;
import com.jianglianghao.notes.entity.UserGroup;
import com.jianglianghao.notes.entity.UserNote;
import com.jianglianghao.notes.entity.UserWarehouse;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.jianglianghao.notes.util.JDBCUtils.readNoteFromDB;
import static com.jianglianghao.notes.view.NoteShowViewController.noteContent;
import static com.jianglianghao.notes.view.StageManage.*;

;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/3/301:24
 */

public class LocalNoteInterfaceController implements Initializable {

    //笔记仓库
    @FXML
    private ListView<String> noteWareHouse;
    //笔记分组
    @FXML
    private ListView<String> noteGroup;
    //笔记标题
    @FXML
    private ListView<String> noteTitle;
    //添加笔记按钮
    @FXML
    private Button addNoteButton;
    //查看个人信息按钮
    @FXML
    private Button checkMSG;

    @FXML
    private Label name;
    //退出登陆按钮
    @FXML
    private Button loginOut;

    //公告
    @FXML
    private TextArea adminAnnounce;

    //查看公告
    @FXML
    private Button showAnnouncement;

    //输入要查找的用户的账号
    @FXML
    private TextField userAccount;

    //输入要查找的用户的名字
    @FXML
    private TextField checkName;

    @FXML
    void showGroup(MouseEvent event) {

    }

    @FXML
    void showNoteTitle(MouseEvent event) {

    }

    @FXML
    void showNote(MouseEvent event) {

    }

    //退出登录
    @FXML
    void loginOut(MouseEvent event) throws Exception {
        //对map集合清除，防止用户名影响
        StageManage.USERS.clear();
        //对showWarehouse清空
        showWareHouse.clear();
        Stage scene = (Stage) loginOut.getScene().getWindow();
        scene.close();
        new Main().start(new Stage());
    }


    //查看个人信息
    @FXML
    void checkMSG(MouseEvent event) throws Exception {
        new showMSGView().start(new Stage());
    }

    //查看公告：用于管理员更新后可以及时查看
    @FXML
    void showAnnouncement(MouseEvent event) {
        //先清空公告栏
        adminAnnounce.clear();
        String sql = "select admin_announce announce from admin_manager where admin_announce is not null";
        List<AdminManager> admin = JDBCUtils.getAllInstance(AdminManager.class, sql);
        if (admin.size() == 0) {
            ViewUtil.showView("错误提示", "管理员没有发布公告");
        } else {
            adminAnnounce.setText(admin.get(0).getAnnounce());
        }
    }

    //添加笔记
    @FXML
    void addNoteButton(MouseEvent event) throws Exception {
        String sql = "select admin_blacklist blacklist from admin_manager where admin_blacklist is not null";
        List<AdminManager> allInstance = JDBCUtils.getAllInstance(AdminManager.class, sql);
        if (allInstance.size() == 0) {
            //管理员没有拉黑人
            new UserWriteNoteView().start(new Stage());
        } else {
            //管理员拉黑了人，看是否是自己
            for (AdminManager adminManager : allInstance) {
                if (adminManager.getBlacklist().equals(USERS.get("user").getAccount())) {
                    //发现自己是黑名单用户
                    ViewUtil.showView("错误提示", "已被拉黑，无法发布笔记");
                    return;
                }
            }
            new UserWriteNoteView().start(new Stage());
        }

    }

    //点击查看仓库按钮
    @FXML
    void selectHouse(MouseEvent event) {
        //获取选择的仓库：这一步放在开头防止清空之后没办法获取选择的信息
        wareHouseChoice = noteWareHouse.getSelectionModel().getSelectedItem();
        noteWareHouse.getItems().clear();
        //显示分组
        String sql = "select house_name houseName, user_account userAccount from note_warehouse";
        List<UserWarehouse> allInstance = JDBCUtils.getAllInstance(UserWarehouse.class, sql);
        for (UserWarehouse userWarehouse : allInstance) {
            //判断该仓库是不是本用户的仓库
            if (userWarehouse.getUserAccount().equals(StageManage.USERS.get("user").getAccount())) {
                //把数据库中的仓库名字存放到list中，已备用
                showWareHouse.add(userWarehouse.getHouseName());
                //把数据库中的仓库的名字显示出来
                noteWareHouse.getItems().add(userWarehouse.getHouseName());
            }
        }
        if(noteWareHouse.getItems().size() == 0){
            ViewUtil.showView("错误提示", "该仓库没有分组");
        }
        /****************以上是初始显示分组功能，以下是查看分组功能**************************/
        /****************************************************************************/
        //每次刷新都清除一次
        noteGroup.getItems().clear();

        //通过仓库来显示分组
        String sql2 = "select group_name groupName, note_name noteTitle, public_private privateOrNot, wareHouse " +
                "wareHouseName, user_account userAccount  from user_note_group where wareHouse = ?";
        List<UserGroup> userGroups = JDBCUtils.getAllInstance(UserGroup.class, sql2, wareHouseChoice);
        for (UserGroup userGroup : userGroups) {
            //判断该仓库是不是本用户的仓库
            if (userGroup.getUserAccount().equals(StageManage.USERS.get("user").getAccount())) {
                //把数据库中的仓库名字存放到list中，已备用
                showNoteGroup.add(userGroup.getGroupName());
                //把数据库中的群组的名字显示出来
                noteGroup.getItems().add(userGroup.getGroupName());
            }
        }
        //刷新页面
        noteGroup.refresh();
    }

    //点击查看笔记分组按钮
    @FXML
    void selectGroup(MouseEvent event) {
        if(noteGroup.getSelectionModel().getSelectedItem() == null){
            ViewUtil.showView("错误提示", "还没有选择分组");
            return;
        }
        noteTitle.getItems().clear();
        //获取选择得分组
        groupChoice = noteGroup.getSelectionModel().getSelectedItem();
        //显示该分组中所有的笔记
        String sql = "select author_name 'name', note_title title, note_content content, like_number 'like', " +
                "note_group 'group', public_or_not publicOrNot, publishDate publishDate, author_account authorAccount " +
                "from user_note where note_group = ?";
        List<UserNote> allInstance = JDBCUtils.getAllInstance(UserNote.class, sql, groupChoice);
        for (UserNote userNote : allInstance) {
            //判断该笔记该是不是本用户的笔记
            if (userNote.getAuthorAccount().equals(StageManage.USERS.get("user").getAccount())) {
                //把分组得名字存放到list中，已备用
                showNoteTitle.add(userNote.getGroup());
                //把数据库中的名字的名字显示出来
                noteTitle.getItems().add(userNote.getTitle());
            }
        }
        if(noteGroup.getItems().size() == 0){
            ViewUtil.showView("错误提示", "该分组没有笔记");
        }
        noteTitle.refresh();
    }

    //点击查看笔记
    @FXML
    void selectNote(MouseEvent event) throws Exception {
        if (noteTitle.getItems().size() == 0) {
            ViewUtil.showView("错误提示", "没有选择笔记");
        } else {
            noteContent = null;
            StageManage.note.clear();
            //获取点击的笔记信息
            noteTitleChoice = noteTitle.getSelectionModel().getSelectedItem();
            //获取笔记
            String note = readNoteFromDB(noteTitleChoice);
            //把笔记传送到一个新的页面展示
            StageManage.note.put("笔记", note);
            new NoteShowView().start(new Stage());
        }

    }

    //添加新仓库
    @FXML
    void addWarehouse(MouseEvent event) throws Exception {
        //打开添加仓库页面
        modifyNames = "请添加仓库名";
        targetOperating = "新增仓库";
        new modifyMSGView().start(new Stage());
    }

    //删除仓库
    @FXML
    void removeWarehouse(MouseEvent event) {
        targetOperating = "删除一个仓库";
        //获取想要删除的仓库的名字
        wareHouseChoice = noteWareHouse.getSelectionModel().getSelectedItem();
        if (wareHouseChoice == null || wareHouseChoice.equals("")) {
            ViewUtil.showView("错误提示", "没有选择仓库");
        } else {
            ModifyMSG modifyMSG = new AllUserModifyController().modifyMSG(wareHouseChoice);
            ViewUtil.showView("修改提示", modifyMSG.getModifyResult());
        }
        noteTitle.getItems().clear();
        noteTitle.refresh();
        noteGroup.getItems().clear();
        noteGroup.refresh();
    }

    //仓库改信息：名字和权限
    @FXML
    void modifyWarehouseMSG(MouseEvent event) throws Exception {
        wareHouseChoice = noteWareHouse.getSelectionModel().getSelectedItem();
        if (wareHouseChoice == null || wareHouseChoice == "") {
            ViewUtil.showView("错误提示", "没选择仓库");
        } else {
            targetOperating = "修改仓库信息";
            modifyNames = "修改仓库信息";
            new modifyMSGView().start(new Stage());
        }
    }

    //添加分组
    @FXML
    void addGroup(MouseEvent event) throws Exception {
        wareHouseChoice = noteWareHouse.getSelectionModel().getSelectedItem();
        modifyNames = "请添加分组名";
        targetOperating = "新增分组";
        new modifyMSGView().start(new Stage());
    }

    //删除分组
    @FXML
    void deleteGroup(MouseEvent event) {
        targetOperating = "删除分组";
        modifyNames = "删除一个分组";
        //找到想要删除的分组
        groupChoice = noteGroup.getSelectionModel().getSelectedItem();
        if (groupChoice == null || groupChoice.equals("")) {
            ViewUtil.showView("错误提示", "没有选择分组");
            return;
        } else {
            ModifyMSG modifyMSG = new AllUserModifyController().modifyGroupMSG(groupChoice);
            ViewUtil.showView("修改提示", modifyMSG.getModifyResult());
        }
        noteTitle.getItems().clear();
        noteTitle.refresh();
    }

    //修改分组信息：名字和权限
    @FXML
    void modifyGroupMSG(MouseEvent event) throws Exception {
        groupChoice = noteGroup.getSelectionModel().getSelectedItem();
        if (groupChoice == null || groupChoice == "") {
            ViewUtil.showView("错误提示", "没选择分组");
        } else {
            targetOperating = "修改分组信息";
            modifyNames = "修改分组信息";
            new modifyMSGView().start(new Stage());
        }

    }

    //删除笔记
    @FXML
    void deleteNote(MouseEvent event) throws Exception {
        targetOperating = "删除笔记";
        modifyNames = "删除笔记";
        noteTitleChoice = noteTitle.getSelectionModel().getSelectedItem();
        if (noteTitleChoice == null || noteTitleChoice.equals("")) {
            ViewUtil.showView("错误提示", "没有选择笔记");
        } else {
            ModifyMSG modifyMSG = new AllUserModifyController().modifyNoteMSG(noteTitleChoice);
            ViewUtil.showView("删除提示", modifyMSG.getModifyResult());
        }
    }

    //修改笔记
    @FXML
    void modifyUserNote(MouseEvent event) throws Exception {
        String selectNote = noteTitle.getSelectionModel().getSelectedItem();
        if (selectNote == null || selectNote.equals("")) {
            ViewUtil.showView("错误提示", "还没有笔记");
        } else {
            noteChoice = selectNote;
            String sql = "select note_content content from user_note where author_account = ? and note_title = ?";
            UserNote user = JDBCUtils.getInstance(UserNote.class, sql, USERS.get("user").getAccount(), selectNote);
            modifyNote = user.getContent();
            new UserModifyNoteView().start(new Stage());
        }
    }

    //论坛功能
    @FXML
    void showForum(MouseEvent event) throws Exception {
        new ForumView().start(new Stage());
    }

    //点击查找用户按钮
    @FXML
    void checkUserNote(MouseEvent event) throws Exception {
        //先清空map，防止以前的搜索记录对现在的造成影响
        checkUserNoteByName.clear();
        checkUserNoteByAccount.clear();
        String accountText = userAccount.getText();
        String nameText = checkName.getText();
        //查看用什么来查找用户
        if (choiceByWhatWay == null) {
            ViewUtil.showView("错误提示", "请选择要找的方式");
            return;
        }
        if (choiceByWhatWay.equals("按用户账号查找")) {
            if (accountText == null || accountText.equals("")) {
                //表示没有输入账号信息
                ViewUtil.showView("错误提示", "没有输入账号");
            } else {
                checkAccount = accountText;
                //输入了账号,查找用户公开的信息
                String sql = "select note_title title, note_content content, author_name name, author_account authorAccount from user_note where author_account = ? and public_or_not = 'public'";
                List<UserNote> allNotes = JDBCUtils.getAllInstance(UserNote.class, sql, accountText);
                if (allNotes.size() == 0) {
                    //该用户没有笔记
                    ViewUtil.showView("错误提示", "笔记为空或用户不存在");
                } else {
                    //用户有笔记
                    for (UserNote userNote : allNotes) {
                        checkTargetName = userNote.getName();
                        TargetAccount = userNote.getAuthorAccount();
                        //把标题和内容各自存放在key和value中
                        checkUserNoteByName.put(userNote.getTitle(), userNote.getContent());
                    }
                    new otherUserNoteView().start(new Stage());
                    return;
                }
            }
        }

        if (choiceByWhatWay.equals("按名字查找")) {
            if (nameText == null || nameText.equals("")) {
                //该用户没有笔记
                ViewUtil.showView("错误提示", "没有输入名字");
            } else {
                TargetName = nameText;
                //通过用户名字来查找公开的笔记
                String sql = "select note_title title, note_content content, author_name name, author_account authorAccount from user_note where author_name = ? and public_or_not = 'public'";
                List<UserNote> allNotes = JDBCUtils.getAllInstance(UserNote.class, sql, TargetName);
                if (allNotes.size() == 0) {
                    //该用户没有笔记
                    ViewUtil.showView("错误提示", "笔记为空或用户不存在");
                } else {
                    //用户有笔记
                    for (UserNote userNote : allNotes) {
                        checkTargetName = userNote.getName();
                        TargetAccount = userNote.getAuthorAccount();
                        //把标题和内容各自存放在key和value中
                        checkUserNoteByName.put(userNote.getTitle(), userNote.getContent());
                    }
                    new otherUserNoteView().start(new Stage());
                    return;
                }
            }
        }
    }

    //选择提供名字查找
    @FXML
    void checkByUserName(MouseEvent event) {
        choiceByWhatWay = "按名字查找";
    }

    //选择提供账号查找
    @FXML
    void checkByAccount(MouseEvent event) {
        //存储
        choiceByWhatWay = "按用户账号查找";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //显示名字
        this.name.setText(String.valueOf(StageManage.USERS.get("user").getUserName()) + ", 欢迎使用笔记系统");
        String sql = "select admin_announce announce from admin_manager where admin_announce is not null";
        List<AdminManager> admin = JDBCUtils.getAllInstance(AdminManager.class, sql);
        adminAnnounce.setText(admin.get(0).getAnnounce());
    }


    //listView中选择的值：仓库，分组，笔记标题
    public static String wareHouseChoice;
    public static String groupChoice;
    public static String noteTitleChoice;

    //增加两个标识
    //存储要修改的名字，用于label显示
    public static String modifyNames;
    //目标操作，比如点击修改仓库就是“修改仓库名字”
    public static String targetOperating;
    //表示要刷新添加几次
    int refresh = 1;
    //存储要修改的笔记内容,传入另一个界面
    public static String modifyNote;
    //存储要查找哪一个用户的笔记的account，在点赞的时候判断是否是自己的
    public static String checkAccount;
    //存储要查找的用户的name，在点赞的时候判断是否是自己的
    public static String checkTargetName;
    //存储要查找哪一个用户的笔记的用户的account，在点赞的时候判断是否是自己的
    public static String TargetName;
    //存储要查找的用户的账号，方便另一个界面使用
    public static String TargetAccount;
    //存储要查找的用户的title
    public static String checkTitle;
    //存储选择什么样的方式
    public static String choiceByWhatWay;
    //存储选择的笔记
    public static String noteChoice;
}
