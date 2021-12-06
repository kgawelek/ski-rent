package com.io.skirent.user.controllers;

import com.io.skirent.user.Client;
import com.io.skirent.user.User;
import com.io.skirent.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void addNewUser(@RequestBody Client client) {
        client.setId(0L); // set mock ID to avoid null pointer
        userService.addNewClient(client);
    }

}