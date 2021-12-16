package com.io.skirent.user.controllers;

import com.io.skirent.user.models.ClientDTO;
import com.io.skirent.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void addNewUser(ClientDTO clientDTO, HttpServletResponse httpResponse) {
        try{
            userService.addNewClient(clientDTO);
            httpResponse.sendRedirect("/login.jsp");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

