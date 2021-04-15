package com.jianglianghao.notes.dao;

import com.jianglianghao.notes.bean.LoginMSG;
import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.bean.SignMSG;
import com.jianglianghao.notes.entity.*;
import com.jianglianghao.notes.util.JDBCUtils;
import com.jianglianghao.notes.util.PasswordUtil;
import com.jianglianghao.notes.util.timeNumberUtil;
import com.jianglianghao.notes.view.StageManage;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import static com.jianglianghao.notes.view.LocalNoteInterfaceController.*;
import static com.jianglianghao.notes.view.modifyMSGViewController.choicePublicOrNot;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description
 * @verdion
 * @date 2021/3/3122:36
 */


public class NoteUserDao {
    //用于为id创造随机数
    timeNumberUtil timeUtil = new timeNumberUtil();


    /**
     * @param user
     * @return
     * @Description 处理用户登陆
     */
    public LoginMSG login(User user) throws Exception {
       String sql = "select user_password password from note_user where user_account=?";
        User instance = JDBCUtils.getInstance(User.class, sql, user.getAccount());
        if(instance == null){
            return new LoginMSG("没有找到该账户", null);
        }else{
            //解密
            String s = PasswordUtil.decryptAES(instance.getPassword(), PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
            if(s.equals(user.getPassword())){
                return new LoginMSG("账号密码正确", null);
            }else{
                return new LoginMSG("密码错误", null);
            }
        }
    }

    /**
     * @param user
     * @return
     * @Description 用户注册
     */
    public SignMSG sign(User user) {
        //判断账号字数
        if(user.getAccount().length() > 20){
            return new SignMSG("账号太长,请控制在20字内", null);
        }
        //检查输入的账号和用户名是否为已经注册的:用户表和管理员表
        String checkSql = "SELECT admin_name adminName, admin_account account FROM note_admin";
        String checkSql1 = "SELECT user_name userName, user_account account FROM note_user";
        //调用工具类方法
        //检查是否注册的信息在admin中有
        List<Admin> allAdmin = JDBCUtils.getAllInstance(Admin.class, checkSql);
        for (Admin resultAdmin : allAdmin) {
            if (resultAdmin.getAdminName().equals(user.getUserName()) || resultAdmin.getAccount().equals(user.getAccount())) {
                return new SignMSG("用户注册信息存在", null);
            }
        }
        //检查是否注册的信息在user中有
        List<User> allUser = JDBCUtils.getAllInstance(User.class, checkSql1);
        for (User resultUser : allUser) {
            if (resultUser.getUserName().equals(user.getUserName()) || resultUser.getAccount().equals(user.getAccount())) {
                return new SignMSG("用户注册信息存在", null);
            }
        }
        //设置user的id:用时间作为种子设置范围在10000的随机数
        long time = System.currentTimeMillis(); //获得当前时间的毫秒数
        Random rd = new Random(time); //作为种子数传入到Random的构造器中
        user.setId(rd.nextInt(10000));

        String sql = "INSERT INTO note_user (id, user_name, user_account, user_password)VALUES (?,?,?,?)";
        //实现插入database数据库
        JDBCUtils.update(sql, user.getId(), user.getUserName(), user.getAccount(), user.getPassword());
        return new SignMSG("注册成功", null);
    }

    /**
     * @param modifyMSG
     * @return
     * @Description 用户修改信息的显示
     */
    public ModifyMSG modify(Object modifyMSG) throws Exception {
        //通过modifyMSG来判断是修改四个信息中的哪一个
        //对用户昵称修改，这时候要修改user_note中的用户昵称
        if (StageManage.saveModify.get("userName") != null) {
            if (StageManage.saveModify.get("userName").equals("user_name")) {
                //这是在修改姓名，首先判断修改的姓名是不是和管理员或者用户的重合了
                //检查修改的信息是否在admin和user中有
                String sqlCheck = "SELECT user_name userName FROM note_user";
                String sqlCheck1 = "SELECT admin_name adminName FROM note_admin";
                ModifyMSG modifySame = JDBCUtils.isModifySame(sqlCheck, sqlCheck1, String.valueOf(modifyMSG));
                if (modifySame.getModifyResult().equals("用户信息存在")) {
                    return modifySame;
                }
                //更新用户知识笔记中的名字
                String sql2 = "update user_note set author_name = ? where author_account = ?";
                //更新用户信息
                String sql = "update note_user set user_name = ? where user_account = ?";
                JDBCUtils.update(sql, String.valueOf(modifyMSG), StageManage.USERS.get("user").getAccount());
                JDBCUtils.update(sql2, String.valueOf(modifyMSG), StageManage.USERS.get("user").getAccount());
                return new ModifyMSG("修改成功，请重新登陆查看", null);
            }
        }
        //对密码修改
        if (StageManage.saveModify.get("password").equals("user_password")) {
            String newPassword = String.valueOf(modifyMSG);
            //检测密码是否大于20
            if(newPassword.length() > 20){
                return new ModifyMSG("密码不可超过20个字", null);
            }
            //对密码进行加密
            String np = PasswordUtil.encryptAES(newPassword, PasswordUtil.key, PasswordUtil.transformation, PasswordUtil.algorithm);
            String sql = "update note_user set user_password = ? where id = ?";
            JDBCUtils.update(sql, np, StageManage.USERS.get("user").getId());
            return new ModifyMSG("修改成功，请重新登陆查看", null);
        }
        return new ModifyMSG("修改成功，请重新登陆查看", null);
    }

    /**
     * @Description 把笔记信息写入数据库
     * @param name 名字
     * @param title 标题
     * @param content 内容
     * @param likeNumber 点赞数
     * @param group 分组
     * @param publicOrNot 是否公开
     * @param currentDate  发表日期
     * @param userAccount 用户账号
     * @return
     */
    public ModifyMSG addNoteToDB(String name, String title, String content, int likeNumber, String group, String publicOrNot, Date currentDate, String userAccount) {
        if (content.length() > 60000) {
            return new ModifyMSG("当前字数" + content.length() + "， 超出60000", null);
        }
        //判断是否笔记添加的分组已经创建了
        String sql = "select group_name groupName from user_note_group";
        List<UserGroup> allInstance = JDBCUtils.getAllInstance(UserGroup.class, sql);
        //找不到笔记组
        if (allInstance.size() == 0) {
            return new ModifyMSG("没有笔记组，请先添加笔记组", null);
        }
        for (UserGroup userGroup : allInstance) {
            if (userGroup.getGroupName().equals(group)) {
                //查看笔记标题是否与已有的笔记标题相同
                String sql2 = "select author_name name from user_note where note_title = ? ";
                List<UserNote> allInstance1 = JDBCUtils.getAllInstance(UserNote.class, sql2, title);
                if (allInstance1.size() > 0) {
                    //表示找到了同标题的文章的作者
                    return new ModifyMSG("文章标题已存在，请另外起名", null);
                }
                //笔记标题没有重复，开始插入数据库
                String sql1 = "insert into user_note values(?, ?, ?, ?, ?, ?, ?, ?)";
                //插入数据库
                JDBCUtils.update(sql1, name, title, content, likeNumber, group, publicOrNot, currentDate, userAccount);
                return new ModifyMSG("添加文章成功", null);
            }
        }
        //没找到对应数据库
        return new ModifyMSG("没找到对应的笔记组", null);
    }

    /**
     * @param modifyMSG
     * @return
     * @Description 对本地仓库进行操作
     */
    public ModifyMSG modifyWareHouse(Object modifyMSG) {
        //如果是新增仓库操作
        if (targetOperating.equals("新增仓库")) {
            if(modifyMSG == null || modifyMSG.equals("") || choicePublicOrNot == null){
                return new ModifyMSG("请输入仓库信息", null);
            }
            //检测仓库在本账号是不是已经存在了
            String sql = "select house_name houseName from note_warehouse where user_account = ?";
            List<UserWarehouse> allInstance = JDBCUtils.getAllInstance(UserWarehouse.class, sql, StageManage.USERS.get("user").getAccount());
            if (allInstance.size() != 0) {
                //当size不等于0，证明数据库中本账号下有仓库
                for (UserWarehouse userWarehouse : allInstance) {
                    if (userWarehouse.getHouseName().equals(modifyMSG)) {
                        return new ModifyMSG("输入的仓库已存在", null);
                    }
                }
            }
            String sql1 = "insert into note_warehouse values(?, null, ?, ?)";
            JDBCUtils.update(sql1, String.valueOf(modifyMSG), choicePublicOrNot, StageManage.USERS.get("user").getAccount());
            return new ModifyMSG("已更新", null);
        }
        if (targetOperating.equals("删除一个仓库")) {
            //删除仓库的时候要把仓库对应的分组的笔记也删除了
            String sql1 = "select group_name groupName from user_note_group where user_account = ? and wareHouse = ?";
            List<UserGroup> user = JDBCUtils.getAllInstance(UserGroup.class, sql1, StageManage.USERS.get("user").getAccount(), modifyMSG);
            if(user.size() != 0){
                for(UserGroup userGroup : user){
                    String sql2 = "delete  from user_note where author_account = ? and note_group = ?";
                    JDBCUtils.update(sql2, StageManage.USERS.get("user").getAccount(), userGroup.getGroupName());
                }
            }
            //对仓库和笔记分组删除
            String sql = "delete from user_note_group where user_account=? and wareHouse=?";
            String sql2 = "delete from note_warehouse where user_account=? and house_name=?";
            JDBCUtils.update(sql, StageManage.USERS.get("user").getAccount(), modifyMSG);
            JDBCUtils.update(sql2, StageManage.USERS.get("user").getAccount(), modifyMSG);
            return new ModifyMSG("删除成功", null);
        }
        if (targetOperating.equals("修改仓库信息")) {
            //对仓库信息进行修改
            //首先是判断是否输入信息不对
            if (modifyMSG == null || modifyMSG.equals("") || choicePublicOrNot == null) {
                return new ModifyMSG("没有输入全部信息", null);
            }
            //检测仓库在本账号是不是已经存在了
            String sql2 = "select house_name houseName from note_warehouse where user_account = ?";
            List<UserWarehouse> allInstance = JDBCUtils.getAllInstance(UserWarehouse.class, sql2, StageManage.USERS.get("user").getAccount());
            if (allInstance.size() != 0) {
                //当size不等于0，证明数据库中本账号下有仓库
                for (UserWarehouse userWarehouse : allInstance) {
                    if (userWarehouse.getHouseName().equals(modifyMSG)) {
                        return new ModifyMSG("输入的仓库已存在", null);
                    }
                }
            }
            String sql3="update user_note_group set wareHouse=? where user_account=? and wareHouse=?";
            String sql ="update note_warehouse set house_name=? where user_account=?and house_name=?";
            String sql1 = "update note_warehouse set private_or_not = ? where user_account = ? and house_name = ?";
            JDBCUtils.update(sql1, choicePublicOrNot, StageManage.USERS.get("user").getAccount(), wareHouseChoice);
            JDBCUtils.update(sql, modifyMSG, StageManage.USERS.get("user").getAccount(), wareHouseChoice);
            JDBCUtils.update(sql3, modifyMSG, StageManage.USERS.get("user").getAccount(), wareHouseChoice);
            return new ModifyMSG("修改成功", null);
        }
        return null;
    }

    /**
     * @param modifyMSG
     * @return
     * @Description 对本地分组进行操作
     */
    public ModifyMSG modifyGroup(Object modifyMSG) {
        if (targetOperating.equals("新增分组")) {
            //新增分组操作
            //1. 获取要添加进那个分组
            String wareChoice = wareHouseChoice;
            if (wareChoice == null || wareChoice.equals("")) {
                //没有选择仓库的时候
                return new ModifyMSG("请输入仓库", null);
            }
            //2. 查看本账号下分组有没有重名
            String sql = "select group_name groupName from user_note_group where user_account = ?";
            List<UserGroup> user = JDBCUtils.getAllInstance(UserGroup.class, sql, StageManage.USERS.get("user").getAccount());
            //当存在仓库的时候，需要判断是不是重命了
            if (user.size() != 0) {
                for (UserGroup userGroup : user) {
                    if (userGroup.getGroupName().equals(modifyMSG)) {
                        //如果相同
                        return new ModifyMSG("已存在分组", null);
                    }
                }
            }
            //不相同，开始添加
            String sql1 = "insert into user_note_group values(?, null, ?, ?, ?)";
            JDBCUtils.update(sql1, modifyMSG, choicePublicOrNot, wareHouseChoice, StageManage.USERS.get("user").getAccount());
            return new ModifyMSG("添加成功", null);
        }
        if (targetOperating.equals("删除分组")) {
            String sql = "delete from user_note_group where group_name=? and user_account=?";
            String sql1 = "delete from user_note where author_account=? and note_group=?";
            JDBCUtils.update(sql, modifyMSG, StageManage.USERS.get("user").getAccount());
            JDBCUtils.update(sql1, StageManage.USERS.get("user").getAccount(), modifyMSG);
            return new ModifyMSG("删除成功", null);
        }
        if (targetOperating.equals("修改分组信息")) {

            //对分组信息进行修改
            //首先是判断是否输入信息不对
            if (modifyMSG == null || modifyMSG.equals("") || choicePublicOrNot == null) {
                return new ModifyMSG("没有输入全部信息", null);
            }
            //再检查是否和本账号下其他组别重命名
            String sql3 = "select group_name groupName from user_note_group where user_account = ?";
            List<UserGroup> user = JDBCUtils.getAllInstance(UserGroup.class, sql3, StageManage.USERS.get("user").getAccount());
            //当存在仓库的时候，需要判断是不是重命了
            if (user.size() != 0) {
                for (UserGroup userGroup : user) {
                    if (userGroup.getGroupName().equals(modifyMSG)) {
                        //如果相同
                        return new ModifyMSG("已存在分组", null);
                    }
                }
            }
            String sql4 = "update user_note_group set group_name = ? where user_account = ? and group_name = ?";
            String sql5 = "update user_note set note_group = ? where author_account = ? and note_group = ?";
            String sql2 = "update user_note_group set public_private = ? where user_account = ? and group_name = ?";
            JDBCUtils.update(sql2, choicePublicOrNot, StageManage.USERS.get("user").getAccount(), groupChoice);
            JDBCUtils.update(sql4, modifyMSG, StageManage.USERS.get("user").getAccount(), groupChoice);
            JDBCUtils.update(sql5, modifyMSG, StageManage.USERS.get("user").getAccount(), groupChoice);
            String sql = "update user_note n ,user_note_group  u\n" +
                    "set n.note_group = ?, u.group_name = ?\n" +
                    "where u.user_account = n.author_account\n" +
                    "AND n.note_group = u.group_name\n" +
                    "AND u.user_account = ?\n" +
                    "AND u.group_name = ?";
            JDBCUtils.update(sql, modifyMSG, modifyMSG, StageManage.USERS.get("user").getAccount(), groupChoice);

            return new ModifyMSG("修改成功", null);
        }
        return null;
    }

    /**
     * @param modifyMSG
     * @return
     * @Description 对笔记进行内容的修改，标题的修改和公开权限的修改
     * @Description 对本地笔记进行操作
     */
    public ModifyMSG modifyNote(Object modifyMSG) {
        if (targetOperating.equals("删除笔记")) {
            //对笔记进行删除
            String sql = "delete from user_note where note_title = ? and author_account = ? and note_group = ?";
            JDBCUtils.update(sql, modifyMSG, StageManage.USERS.get("user").getAccount(), groupChoice);
            return new ModifyMSG("已删除", null);
        }
        return null;
    }

    public ModifyMSG modifyNoteMSG(String title, String content, String publicChoice) {
        ModifyMSG modifyMSG = null;
        //对title判断,如果没有输入就证明使用原来的标题
        if (title != null && !title.equals("")) {
            //证明要修改title
            //先判断在本地笔记用户下是否存在同名的笔记title
            String sql = "select note_title title from user_note where author_account = ? and note_title = ?";
            List<UserNote> user = JDBCUtils.getAllInstance(UserNote.class, sql, StageManage.USERS.get("user").getAccount(), title);
            if (user.size() == 0) {
                //证明没有找到标题，可以直接修改
                String sql1 = "update user_note set note_title = ? where author_account = ? and note_title=?";
                JDBCUtils.update(sql1, title, StageManage.USERS.get("user").getAccount(), noteChoice);
                modifyMSG = new ModifyMSG("已修改", null);
            } else {
                //证明找到了同名的title
                modifyMSG = new ModifyMSG("标题重复了", null);
                return modifyMSG;
            }
        }
        //下面对笔记公有私有进行修改
        if (publicChoice != null) {
            String sql2 = "update user_note set public_or_not = ? where author_account = ? and note_title=?";
            JDBCUtils.update(sql2, publicChoice, StageManage.USERS.get("user").getAccount(), noteChoice);
        }
        //下面对笔记内容进行修改
        if (content == null || content.equals("")) {
            modifyMSG = new ModifyMSG("没有输入笔记", null);
            return modifyMSG;
        } else {
            String sql3 = "update user_note set note_content = ? where author_account = ? and note_title=?";
            JDBCUtils.update(sql3, content, StageManage.USERS.get("user").getAccount(), noteChoice);
            modifyMSG = new ModifyMSG("已修改", null);
        }
        return modifyMSG;
    }

    /**
     * @param YourAccount  你的账号
     * @param checkAccount 你要点赞的账号
     * @param title        你要点赞的文章
     * @return
     * @Description 把点赞的情况存入数据库
     */
    public ModifyMSG modifyUserNoteToLike(String YourAccount, String checkAccount, String title) {
        //首先判断此人是否给该用户的该文章点过赞了
        String sql3 = "select like_person likePerson from liked where beliked_person = ? and like_title = ?";
        List<liked> allInstance1 = JDBCUtils.getAllInstance(liked.class, sql3, checkAccount, title);
        if (allInstance1.size() != 0) {
            for (liked like : allInstance1) {
                if(like.getLikePerson().equals(YourAccount)){
                    return new ModifyMSG("已经点赞过一次",null);
                }
            }
        }
        //把点赞的人和被点赞的人和被点赞的标题存入数据库
        String sql = "insert into liked values(?, ?, ?)";
        JDBCUtils.update(sql, YourAccount, checkAccount, title);
        //从like表中查找给该笔记点赞的个数
        String sql1 = "select like_person likePerson, beliked_person belikedPerson, like_title likeTitle from liked where beliked_person = ? and like_title = ?";
        List<liked> allInstance = JDBCUtils.getAllInstance(liked.class, sql1, checkAccount, title);
        int count = allInstance.size();
        //更新user_note中的点赞数据
        String sql2 = "update user_note set like_number = ? where author_account = ? and note_title = ?";
        JDBCUtils.update(sql2, count, checkAccount, title);
        return new ModifyMSG("点赞成功", null);
    }

    public ModifyMSG modifyUserToUnlike(String YourAccount, String checkAccount, String title){
        //首先判断数据库中关于该用户对目标用户的点赞存储还有没有
        String sql = "select like_title likeTitle from liked where like_person = ? and beliked_person = ?";
        List<liked> allInstance = JDBCUtils.getAllInstance(liked.class, sql, YourAccount, checkAccount);
        for(liked like : allInstance){
            if(like.getLikeTitle().equals(title)){
                //在liked数据库中删除被赞的记录
                String sql2 = "delete from liked where like_person = ? and beliked_person = ? and like_title = ?";
                JDBCUtils.update(sql2, YourAccount, checkAccount, title);
                //重新计算liked数据库中当前用户下当前文章的赞个数
                String sql3 = "select like_person likePerson, beliked_person belikedPerson, like_title likeTitle from liked where beliked_person = ? and like_title = ?";
                List<liked> allInstance1 = JDBCUtils.getAllInstance(liked.class, sql3, checkAccount, title);
                int count = allInstance1.size();
                //更新user_note中的点赞数据
                String sql4 = "update user_note set like_number = ? where author_account = ? and note_title = ?";
                JDBCUtils.update(sql4, count, checkAccount, title);
                return new ModifyMSG("取消点赞成功", null);
            }
        }
        return new ModifyMSG("没有笔记或已取消过赞", null);
    }

}
