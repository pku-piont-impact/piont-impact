package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.util.Log;

/**
 * function： 数据库工具类，连接数据库用
 */
public class JDBCUtils {
    private static final String TAG = "mysql-demo-JDBCUtils";
    private static String driver = "com.mysql.cj.jdbc.Driver";// MySql驱动
    private static String dbName = "project";// 数据库名称
    private static String user = "root";// 用户名
    private static String password = "1234";// 密码
    public static Connection getConn(){
        java.sql.Connection connection = null;
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);  // 每隔0.1秒尝试连接
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Log.v(TAG, "加载JDBC驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "加载JDBC驱动失败");
                        return;
                    }

                    try {
                        String ip = "10.7.71.98";
                        String url = "jdbc:mysql://" + ip + ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
                        java.sql.Connection conn = DriverManager.getConnection(url, user, password);
                        Log.d(TAG, "数据库连接成功");
                        conn.close();
                        return;
                    } catch (SQLException e) {
                        Log.e(TAG, e.getMessage());
                        Log.e(TAG, "连接数据库失败");
                    }
                }
            }
        });
        thread.start();
        return connection;
    }
}
