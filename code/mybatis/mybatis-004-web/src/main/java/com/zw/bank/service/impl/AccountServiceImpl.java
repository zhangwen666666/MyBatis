package com.zw.bank.service.impl;

import com.zw.bank.dao.AccountDao;
import com.zw.bank.dao.impl.AccountDaoImpl;
import com.zw.bank.exceptions.AccountNotExistException;
import com.zw.bank.exceptions.MoneyNotEnoughException;
import com.zw.bank.exceptions.TransferException;
import com.zw.bank.pojo.Account;
import com.zw.bank.service.AccountService;
import com.zw.bank.utils.GenerateDaoProxy;
import com.zw.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {

    // private AccountDao accountDao = new AccountDaoImpl();
    // private AccountDao accountDao = (AccountDao) GenerateDaoProxy.generate(SqlSessionUtil.openSession(), AccountDao.class);
    private AccountDao accountDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);

    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, AccountNotExistException, TransferException {
        SqlSession sqlSession = null;
        try{
            // 1. 判断转出账户的余额是否充足
            Account fromAct = accountDao.selectAccountByActno(fromActno);
            if (fromAct == null) {
                throw new AccountNotExistException("对不起，账户不存在");
            }
            // 2. 如果转出账户余额不足，提示用户
            if (fromAct.getBalance() < money) {
                throw new MoneyNotEnoughException("对不起，余额不足");
            }
            // 3. 如果转出账户余额充足，更新内存中转出和转入账户余额
            Account toAccount = accountDao.selectAccountByActno(toActno);
            if (toAccount == null) {
                throw new AccountNotExistException("对不起，账户不存在");
            }
            fromAct.setBalance(fromAct.getBalance() - money);
            toAccount.setBalance(toAccount.getBalance() + money);
            // 4. 更新数据库中账户余额
            // 开始事务
            sqlSession = SqlSessionUtil.openSession();
            int count = accountDao.updateByActno(fromAct);

            // 模拟异常
            /*String s = null;
            s.toString();*/

            count += accountDao.updateByActno(toAccount);
            if (count != 2) {
                throw new TransferException("转账失败，请重试");
            }
            // 提交事务
            sqlSession.commit();
        }finally {
            SqlSessionUtil.close(sqlSession);
        }
    }
}
