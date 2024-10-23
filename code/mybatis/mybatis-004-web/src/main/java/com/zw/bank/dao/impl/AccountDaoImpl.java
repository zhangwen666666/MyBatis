package com.zw.bank.dao.impl;

import com.zw.bank.dao.AccountDao;
import com.zw.bank.pojo.Account;
import com.zw.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountDaoImpl implements AccountDao {


    @Override
    public Account selectAccountByActno(String actno) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        return (Account) sqlSession.selectOne("account.selectByActno", actno);
    }

    @Override
    public int updateByActno(Account act) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        return sqlSession.update("account.updateByActno", act);
    }
}
