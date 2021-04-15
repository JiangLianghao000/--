package com.jianglianghao.notes.view;

import com.jianglianghao.notes.entity.AdminManager;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.ViewUtil;
import com.jianglianghao.notes.util.isEmptyUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.jianglianghao.notes.view.StageManage.Admins;
import static com.jianglianghao.notes.view.StageManage.adminAnnounce;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/4/20:21
 */

public class LocalNoteViewAdminController implements Initializable {
    //上层显示用户
    @FXML
    private Label adminName;
    //查找时候，输入的名字
    @FXML
    private TextField findUserByNameText;
    //查找的时候，输入的账号
    @FXML
    private TextField findUserByAccounTextt;
    //退出登陆
    @FXML
    private Button backLogin;
    //查找按钮
    @FXML
    private Button findUserByNameAndAccountButton;
    //选择按钮
    @FXML
    private RadioButton choiceFindByName;

    @FXML
    private ToggleGroup FindUser;
    //选择按钮
    @FXML
    private RadioButton choiceFindByAccount;

    //管理员写的公告
    @FXML
    private TextArea announce;

    //查看所写的公告
    @FXML
    private TextArea showAnnounce;

    //添加的黑名单用户
    @FXML
    private TextField blacklist;

    //解除的黑名单用户
    @FXML
    private TextField outBlacklist;

    //管理员要搜索的名单
    @FXML
    private TextField userAccount;


    //发布公告:设置为只能看到一位管理员发布的公告
    @FXML
    void sendAnnounce(MouseEvent event) {
        //没有输入公告
        if (announce.getText() == null || announce.getText().equals("")) {
            ViewUtil.showView("错误提示", "没有输入公告");
        } else {
            //输入了
            //每发布一次就清空一次，防止对更新的公告造成影响
            adminAnnounce.clear();
            //对数据库也要清除
            String clearSQL = "delete from admin_manager where admin_announce is not null";
            JDBCUtils.update(clearSQL);
            //获取公告内容
            String announceText = announce.getText();
            //把公告放入map中,发布一次存一次
            adminAnnounce.put(Admins.get("admin").getAdminName(), Admins.get("admin").getAdminName() + ": \n" + announceText);
            //把公告存入数据库
            String sql = "insert into admin_manager values(?, ?, null)";
            JDBCUtils.update(sql, announceText, Admins.get("admin").getAdminName());
            ViewUtil.showView("发布提示", "发布成功");
        }
    }

    //查看公告
    @FXML
    void checkAnnounce(MouseEvent event) {
        //提取数据库中的公告
        String sql = "select admin_announce announce from admin_manager where admin_announce is not null";
        List<AdminManager> admin = JDBCUtils.getAllInstance(AdminManager.class, sql);
        if (admin.size() == 0) {
            ViewUtil.showView("错误提示", "还没有发布公告");
        } else {
            for (AdminManager adminManager : admin) {
                showAnnounce.setText(adminManager.getAnnounce());
            }
            if (adminAnnounce.get(Admins.get("admin").getAdminName()) != null) {
                //此时证明已经修改过公告了
                showAnnounce.clear();
                showAnnounce.setText(adminAnnounce.get(Admins.get("admin").getAdminName()));
            }
        }


    }

    //完成对公告的操作
    @FXML
    void finish(MouseEvent event) {

    }

    //选择按钮，选择通过名字查找
    @FXML
    void choiceFindByName(MouseEvent event) {
        //通过不同选择赋值
        findByWhatChoice = findUserByNameText.getText();
    }

    //选择按钮，选择通过账号查找
    @FXML
    void choiceFindByAccount(MouseEvent event) {
        //通过不同选择赋值
        findByWhatChoice = findUserByAccounTextt.getText();
    }

    //查找按钮
    @FXML
    void findUserByNameAndAccounButtont(MouseEvent event) {
        //判断是否为null或者没有输入
        boolean judge = isEmptyUtil.judgeMSG(findByWhatChoice);
        if (judge == true) {
            ViewUtil.showButtonHint("错误提示", "没有输入信息");
        } else {
            //当选择了根据名字查找
            if (findByWhatChoice.equals(findUserByNameText.getText())) {
                //选择的是通过名字查找
                User user = JDBCUtils.searchUserByName(findByWhatChoice);
                //判断是否为null
                if (user == null) {
                    ViewUtil.showView("错误提示", "没有找到用户");
                } else {
                    ViewUtil.showUserMSG(user);
                }
                return;
            }
            //当选择了根据账号查找
            if (findByWhatChoice.equals(findUserByAccounTextt.getText())) {
                //选择的是通过名字查找
                User user = JDBCUtils.searchName(findByWhatChoice);
                //判断是否为null
                if (user == null) {
                    ViewUtil.showView("错误提示", "没有找到用户");
                } else {
                    ViewUtil.showUserMSG(user);
                }
            }
            return;
        }
    }


    //退出登陆
    @FXML
    void backLogin(MouseEvent event) throws Exception {
        //清空存储信息
        StageManage.Admins.clear();
        Stage stage = (Stage) backLogin.getScene().getWindow();
        stage.close();
        new Main().start(new Stage());
    }

    //添加黑名单按钮
    @FXML
    void addToBlacklist(MouseEvent event) {
        String targetUser = blacklist.getText();
        if (targetUser == null || targetUser.equals("")) {
            ViewUtil.showView("错误提示", "还没有输入要添加的账号");
        } else {
            //查询数据库有没有这个用户
            String sql = "select user_account account from note_user ";
            List<User> allInstance = JDBCUtils.getAllInstance(User.class, sql);
            if (allInstance.size() == 0) {
                ViewUtil.showView("错误提示", "没有该用户");
            } else {
                for (User user : allInstance) {
                    if (user.getAccount().equals(targetUser)) {
                        //找到了该用户
                        String sql1 = "insert into admin_manager values(null, ?, ?)";
                        JDBCUtils.update(sql1, Admins.get("admin").getAdminName(), targetUser);
                        ViewUtil.showView("添加成功提示", "添加成功");
                        return;
                    }
                }
                ViewUtil.showView("错误提示", "没有该用户");
            }
        }
    }

    //解除黑名单按键
    @FXML
    void pullOutBlacklist(MouseEvent event) {
        String targetUser = outBlacklist.getText();
        if (targetUser == null || targetUser.equals("")) {
            ViewUtil.showView("错误提示", "还没有输入要解除的账号");
        } else {
            //查询数据库有没有这个用户
            String sql = "select admin_blacklist blacklist from admin_manager where admin_blacklist is not null";
            List<AdminManager> allInstance = JDBCUtils.getAllInstance(AdminManager.class, sql);
            if (allInstance.size() == 0) {
                //没有用户，就证明了
                ViewUtil.showView("错误提示", "没有该用户");
            } else {
                for (AdminManager adminManager : allInstance) {
                    if (adminManager.getBlacklist().equals(targetUser)) {
                        //找到了该用户
                        String sql1 = "delete from admin_manager where admin_blacklist = ?";
                        JDBCUtils.update(sql1, targetUser);
                        ViewUtil.showView("解除提示", "解除黑名单成功");
                        return;
                    }
                }
                ViewUtil.showView("错误提示", "没有改用户");
            }
        }
    }

    //查找用户的时候，选择不同的按钮会赋值不同
    private String findByWhatChoice = "";

    //进入论坛
    @FXML
    void forum(MouseEvent event) throws Exception {
        new ForumView().start(new Stage());
    }

    //查找用户的笔记，有违规的可以物理删除
    @FXML
    void checkUserNotes(MouseEvent event) throws Exception {
        if (userAccount.getText() == null || userAccount.getText().equals("")) {
            ViewUtil.showView("错误提示", "没有输入账号");
        } else {
            searchAccount = userAccount.getText();
            //看该用户是否存在
            String sql = "select user_name userName from note_user where user_account = ?";
            List<User> allInstance = JDBCUtils.getAllInstance(User.class, sql, searchAccount);
            if (allInstance.size() != 0) {
                new AdminCheckUserNoteView().start(new Stage());
            }else{
                ViewUtil.showView("错误提示", "该用户不存在");
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminName.setText("管理员: " + Admins.get("admin").getAdminName() + "，欢迎登陆");
    }

    //存放管理员想要查找的用户的信息
    public static String searchAccount;

}
