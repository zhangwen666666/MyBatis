package com.zw.bank.dao;

import com.zw.bank.pojo.Account;

/**
 * 账户DAO对象，负责t_act表中数据的CRUD
 * DAO中的方法与业务不挂钩
 */
public interface AccountDao {
    Account selectAccountByActno(String actno);
    int updateByActno(Account act);
}
