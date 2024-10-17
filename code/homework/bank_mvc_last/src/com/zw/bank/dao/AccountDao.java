package com.zw.bank.dao;

import com.zw.bank.pojo.Account;

import java.util.List;

public interface AccountDao {
    int insert(Account account);
    int deleteByActno(Long id);
    int update(Account account);
    Account selectById(Long id);
    Account selectByActno(String actno);
    List<Account> selectAll();
}
