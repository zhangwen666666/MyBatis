package com.zw.bank.web;

import com.zw.bank.exceptions.AppException;
import com.zw.bank.exceptions.MoneyNotEnoughException;
import com.zw.bank.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 接收数据
        String fromActno = request.getParameter("fromActno");
        String toActno = request.getParameter("toActno");
        double money = Double.parseDouble(request.getParameter("money"));
        // 调用业务方法处理业务
        AccountService accountService = new AccountService();
        try {
            accountService.transfer(fromActno, toActno,money);
            response.sendRedirect(request.getContextPath() + "/success.jsp");
        } catch (MoneyNotEnoughException e) {
            // 余额不足
            response.sendRedirect(request.getContextPath() + "/moneyNotEnough.jsp");
        } catch (AppException e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");

        }
    }
}
