package com.io.skirent.user.services;

import com.io.skirent.user.Account;
import com.io.skirent.user.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getTest() {
        return accountRepository.findAll();
    }

}
