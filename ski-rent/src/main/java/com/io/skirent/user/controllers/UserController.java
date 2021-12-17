package com.io.skirent.user.controllers;

import com.io.skirent.user.exceptions.*;
import com.io.skirent.user.models.ClientDTO;
import com.io.skirent.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.format.DateTimeParseException;


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
        try {
            try {
                userService.addNewClient(clientDTO);
                httpResponse.sendRedirect("/login.jsp?login=prompt");
            } catch (InvalidNameException e) {
                httpResponse.sendRedirect("/registration.html?registration=failure&reason=invalidName");
            } catch (InvalidSurnameException e) {
                httpResponse.sendRedirect("/registration.html?registration=failure&reason=invalidSurname");
            } catch (DateTimeParseException | InvalidDateException e) {
                httpResponse.sendRedirect("/registration.html?registration=failure&reason=invalidDate");
            } catch (InvalidEmailException e) {
                httpResponse.sendRedirect("/registration.html?registration=failure&reason=invalidEmail");
            } catch (EmailTakenException e) {
                httpResponse.sendRedirect("/registration.html?registration=failure&reason=emailTaken");
            } catch (InvalidPasswordException e) {
                httpResponse.sendRedirect("/registration.html?registration=failure&reason=invalidPassword");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

