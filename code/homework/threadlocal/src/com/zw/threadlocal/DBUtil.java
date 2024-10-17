package com.zw.threadlocal;

public class DBUtil {
    // 全局的Map集合
    private static MyThreadLocal<Connection> local = new MyThreadLocal<>();

    // 每次都从这个获取Connection
    public static Connection getConnection(){
        Connection connection = local.get();
        if (connection == null) {
            connection = new Connection();
            local.set(connection);
        }
        return connection;
    }
}
