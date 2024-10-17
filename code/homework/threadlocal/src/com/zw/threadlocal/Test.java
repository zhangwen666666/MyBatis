package com.zw.threadlocal;

public class Test {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        System.out.println(thread);
        // 获取Connection对象
        Connection connection = new Connection();

        // 调用service
        UserService userService = new UserService();
        userService.save();
    }
}
