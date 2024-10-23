package com.zw.bank.web;

import com.zw.bank.exceptions.AccountNotExistException;
import com.zw.bank.exceptions.MoneyNotEnoughException;
import com.zw.bank.exceptions.TransferException;
import com.zw.bank.service.AccountService;
import com.zw.bank.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {

    // 为了让这个对象在其他方法中也可以使用，声明为实例变量
    private AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
        // 调用service的转账方法完成转账，处理业务
        try {
            accountService.transfer(fromActno, toActno, money);
            // 调用View层完成展示结果
            // 转账成功
            response.sendRedirect(request.getContextPath() + "/success.html");
        } catch (MoneyNotEnoughException e) {
            response.sendRedirect(request.getContextPath() + "/moneyNotEnough.html");
        } catch (AccountNotExistException e) {
            response.sendRedirect(request.getContextPath() + "/accountNotExist.html");
        } catch (Exception exception) {
            response.sendRedirect(request.getContextPath() + "/error.html");
        }

    }
}
