package com.zw.bank.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtil {
    private DBUtil(){}

    private static ResourceBundle bundle = ResourceBundle.getBundle("resource/jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");

    static {
        // 注册驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ThreadLocal<Connection> local = new ThreadLocal<>();

    // 创建连接对象
    public static Connection getConnection() throws SQLException {
        Connection connection = local.get();
        if(connection == null){
            connection = DriverManager.getConnection(url,user,password);
            local.set(connection);
        }
        return connection;
    }

    public static void close(Connection connection, PreparedStatement ps, ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
                local.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
