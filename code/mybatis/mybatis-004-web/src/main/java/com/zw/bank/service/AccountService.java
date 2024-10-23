package com.zw.bank.service;

import com.zw.bank.exceptions.AccountNotExistException;
import com.zw.bank.exceptions.MoneyNotEnoughException;
import com.zw.bank.exceptions.TransferException;

/**
 * 账户业务类接口
 */
public interface AccountService {
    /**
     * 账户转账业务
     * @param fromActno 转出账户
     * @param toActno 转入账户
     * @param money 转账金额
     */
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, AccountNotExistException, TransferException;
}
