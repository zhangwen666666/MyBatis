package com.zw.bank.web.servlet;

import com.zw.bank.exceptions.AppException;
import com.zw.bank.exceptions.MoneyNotEnoughException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * 在不使用MVC架构模式的前提下，完成银行账户转账
 */
@WebServlet("/transfer")
public class AccountTransferServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 获取转账相关的信息
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
        /*System.out.println(fromActno);
        System.out.println(toActno);
        System.out.println(money);*/

        // 连接数据库进行转账操作
        // 1. 转账之前要判断余额是否充足
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mvc";
            String user = "root";
            String password = "1234";
            connection = DriverManager.getConnection(url, user, password);
            String sql1 = "select balance from t_act where actno = ?";
            ps = connection.prepareStatement(sql1);
            ps.setString(1, fromActno);
            rs = ps.executeQuery();
            if(rs.next()){
                // 取出余额
                double balance = rs.getDouble("balance");
                if(balance < money){
                    // 余额不足
                    throw new MoneyNotEnoughException("对不起，余额不足");
                }
                // 余额充足
                // 开始转账
                connection.setAutoCommit(false);
                String sql2 = "update t_act set balance = balance - ? where actno = ?";
                ps2 = connection.prepareStatement(sql2);
                ps2.setDouble(1, money);
                ps2.setString(2, fromActno);
                int count = ps2.executeUpdate();

                /*String s = null;
                s.toString();*/

                String sql3 = "update t_act set balance = balance + ? where actno = ?";
                ps3 = connection.prepareStatement(sql3);
                ps3.setDouble(1, money);
                ps3.setString(2, toActno);
                count += ps3.executeUpdate();
                if(count == 2){
                    connection.commit();
                    out.print("转账成功！");
                }else {
                    throw new AppException("App异常，请联系管理员");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // 发生异常之后准备怎么做
            e.fillInStackTrace();
        } catch (MoneyNotEnoughException e) {
            out.print(e.getMessage());
        } catch (AppException e) {
            out.print(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();

        }finally {
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
                    e.fillInStackTrace();
                }
            }
            if(ps2 != null){
                try {
                    ps2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps3 != null){
                try {
                    ps3.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException e) {
                    e.fillInStackTrace();
                }
            }
        }
    }
}
