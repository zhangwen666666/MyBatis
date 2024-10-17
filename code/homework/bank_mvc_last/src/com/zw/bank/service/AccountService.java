package com.zw.bank.service;

import com.zw.bank.exceptions.AppException;
import com.zw.bank.exceptions.MoneyNotEnoughException;

public interface AccountService {
    void transfer(String fromActno, String toActno, double money)
            throws MoneyNotEnoughException, AppException;
}
