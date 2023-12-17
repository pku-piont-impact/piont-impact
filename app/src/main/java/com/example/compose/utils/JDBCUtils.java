package com.example.compose.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * function： 数据库工具类，连接数据库用
 */
public class JDBCUtils {
    private static final String TAG = "mysql-demo-JDBCUtils";
    private static String driver = "com.mysql.cj.jdbc.Driver";// MySql驱动
    private static String dbName = "project";// 数据库名称
    private static String user = "root";// 用户名
    private static String password = "123456";// 密码
    public static Connection getConn(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Log.v(TAG, "加载JDBC驱动成功");
        }
        catch (ClassNotFoundException e) {
            Log.e(TAG, "加载JDBC驱动失败");
        }

        try {
            String ip = "182.92.182.33";
            String url = "jdbc:mysql://" + ip + ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, user, password);
            Log.d(TAG, "数据库连接成功");
        }
        catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            Log.e(TAG, "连接数据库失败");
        }
        return connection;
    }
}
