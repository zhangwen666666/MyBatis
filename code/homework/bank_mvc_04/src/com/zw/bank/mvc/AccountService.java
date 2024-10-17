package com.zw.bank.mvc;

import com.zw.bank.exceptions.AppException;
import com.zw.bank.exceptions.MoneyNotEnoughException;
import com.zw.bank.utils.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * AccountService：专门处理Account业务的一个类。
 * 在该类中应该编写纯业务代码
 */
public class AccountService {

    private AccountDao accountDao = new AccountDao();

    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, AppException {
        try(Connection connection = DBUtil.getConnection()){
            System.out.println(connection);
            // 开启事务
            connection.setAutoCommit(false);

            // 查询账户余额是否充足
            Account fromAct = accountDao.selectByActno(fromActno);
            if(fromAct.getBalance() < money){
                throw new MoneyNotEnoughException("对不起，余额不足");
            }
            // 查询另一个账户
            Account toAct = accountDao.selectByActno(toActno);
            // 修改余额 （只修改了内存中Java对象余额）
            fromAct.setBalance(fromAct.getBalance() - money);
            toAct.setBalance(toAct.getBalance() + money);
            // 更新数据库
            int count = accountDao.update(fromAct);
            // 模拟异常
            String s = null;
            s.toString();
            count += accountDao.update(toAct);
            if( count != 2){
                throw new AppException("账户转账异常");
            }

            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            // 回滚事务
            throw new AppException("账户转账异常");
        }
    }
}
