package com.io.skirent.user.controllers;

import com.io.skirent.user.Account;
import com.io.skirent.user.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping
    public List<Account> getTest() {
        return accountService.getTest();
    }



}
