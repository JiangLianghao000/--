package com.jianglianghao.notes.util;

import com.jianglianghao.notes.bean.ModifyMSG;
import com.jianglianghao.notes.entity.Admin;
import com.jianglianghao.notes.entity.User;
import com.jianglianghao.notes.entity.UserNote;
import com.jianglianghao.notes.view.StageManage;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author JLHWASX   Email:2429890953@qq.com
 * @Description 操作数据库的工具类
 * @verdion 1.0
 * @date 2021/3/31 1:03
 */

public class JDBCUtils {

    /**
     * @Description 获取连接
     * @verdion 1.0
     */
    public static Connection getCollection() throws Exception {
        //1.读取配置文件中4个基本信息
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driverClass = bundle.getString("driverClass");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        PreparedStatement preparedStatement = null;
        //2. 加载驱动
        Class.forName(driverClass);
        //3. 获取连接
        Connection collection = DriverManager.getConnection(url, user, password);
        return collection;
    }

    /**
     * @Description 关闭资源
     * @verdion 1.0
     */
    public static void closeResource(Connection collection, Statement preparedStatement){
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(collection != null){
            try {
                collection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @Description 关闭资源
     * @verdion 2.0
     */
    public static void closeResource(Connection collection, Statement preparedStatement, ResultSet resultSet){
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(collection != null){
            try {
                collection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description 获取数据库任一表的所有记录
     * @param clazz 反射类
     * @param sql sql语句
     * @param args 填充占位符的参数
     * @param <T> 泛型
     * @return 返回list<T>
     */
    public static <T> List<T> getAllInstance(Class<T> clazz, String sql, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getCollection();
            ps = conn.prepareStatement(sql);
            for(int i = 0;i < args.length;i++){
                ps.setObject(i + 1, args[i]);
            }

            //执行，获取结果集
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取列数
            int columnCount = rsmd.getColumnCount();
            //创建集合对象
            ArrayList<T> list = new ArrayList<T>();
            while(rs.next()){
                T t = clazz.newInstance();
                //给T对象属性赋值的过程
                for(int i = 0;i < columnCount;i++){
                    //获取每个列的列值:通过ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //通过ResultSetMetaData
                    //获取列的列名：getColumnName() --不推荐使用
                    //获取列的别名：getColumnLabel()
//					String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    //通过反射，将对象指定名columnName的属性赋值为指定的值columnValue
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    /**
     * @Description //通用的增删改操作,占位符个数应该和可变形参个数一致
     * @param sql sql语句
     * @param args 参数
     */
    public static void update(String sql, Object ... args){

        Connection collection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1. 获取连接
            collection  = JDBCUtils.getCollection();
            //2. 预编译sql语句，返回prepareStatement
            preparedStatement = collection.prepareStatement(sql);
            //3. 填充占位符
            for(int i = 0; i < args.length; i++){
                //第一个从1开始，第二个从0开始
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4. 执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5. 关闭资源
            JDBCUtils.closeResource(collection,preparedStatement);
        }
    }

    /**
     * @Description 通过用户账号来返回该用户信息
     * @param account 账号
     * @return 返回用户对象
     */
    public static User searchName(String account){
        boolean userFind = isEmptyUtil.judgeMSG(account);
        if(userFind == true){
            return null;
        }
        String sql = "select id, user_name userName, user_account account, user_password password From note_user where user_account = ?";
        List<User> allInstance = getAllInstance(User.class, sql, account);
        if(allInstance.size() != 0){
            Iterator<User> iterator = allInstance.iterator();
            return iterator.next();
        }
        return null;
    }

    public static Admin searchAdmin(String account){
        boolean adminFind = isEmptyUtil.judgeMSG(account);
        if(adminFind == true){
            return null;
        }
        String sql = "select id, admin_name adminName, admin_account account, admin_password password From note_admin where admin_account = ?";
        List<Admin> allInstance = getAllInstance(Admin.class, sql, account);
        Iterator<Admin> iterator = allInstance.iterator();
        return iterator.next();
    }


    /**
     *
     * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
     * @author shkstart
     * @date 上午11:42:23
     * @param clazz 反射
     * @param sql sql语句
     * @param args 填充的参数
     * @return 返回一个对象
     */
    public static <T> T getInstance(Class<T> clazz,String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getCollection();

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);

                    // 获取每个列的列名
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);

        }
        return null;
    }

    /**
     * @Description 检测修改的信息是否与其他用户相同
     * @param sql1 sql语句
     * @param sql2 sql语句
     * @param modifyName 要修改的名字
     * @return
     */
    public static ModifyMSG isModifySame(String sql1, String sql2, String modifyName){
        //检查输入的账号和用户名是否为已经注册的:用户表和管理员表
        String checkSql =  sql1;
        String checkSql1 = sql2;
        //调用工具类方法
        //检查是否信息在admin中有
        List<Admin> allAdmin = JDBCUtils.getAllInstance(Admin.class, checkSql1);
        for(Admin resultAdmin : allAdmin){
            if(resultAdmin.getAdminName().equals(modifyName)){
                return new ModifyMSG("用户信息存在", null);
            }
        }
        //检查是否信息在user中有
        List<User> allUser = JDBCUtils.getAllInstance(User.class, checkSql);
        for(User resultUser : allUser){
            if(resultUser.getUserName().equals(modifyName)){
                return new ModifyMSG("用户信息存在", null);
            }
        }
        return new ModifyMSG("用户信息不存在, 可以注册", null);
    }

    /**
     * @Description 通过用户昵称来返回该用户信息
     * @param userName 用户名字
     * @return User 用户对象
     */
    public static User searchUserByName(String userName){
        boolean userFind = isEmptyUtil.judgeMSG(userName);
        if(userFind == true){
            return null;
        }
        String sql = "select id, user_name userName, user_account account, user_password password From note_user where user_name = ?";
        List<User> allInstance = getAllInstance(User.class, sql, userName);
        if(allInstance.size() != 0){
            Iterator<User> iterator = allInstance.iterator();
            return iterator.next();
        }
        return null;

    }

    /**
     * @param title 标题
     * @return 内容
     */
    //从文件中读取指定的文章
    public static String  readNoteFromDB(String title){
        String sql = "select note_content content from user_note where author_account = ? and note_title = ?";
        List<UserNote> user = getAllInstance(UserNote.class, sql, StageManage.USERS.get("user").getAccount(), title);
        if(user.size() == 0){
            return null;
        }
        return user.get(0).getContent();
    }

}
