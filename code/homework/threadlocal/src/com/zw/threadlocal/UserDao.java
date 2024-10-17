package com.zw.threadlocal;

public class UserDao {
    public void insert(){
        Thread thread = Thread.currentThread();
        System.out.println(thread);

        Connection connection = DBUtil.getConnection();
        System.out.println(connection);

        System.out.println("User Dao insert");
    }
}
