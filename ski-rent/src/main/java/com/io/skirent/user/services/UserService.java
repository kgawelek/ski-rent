package com.io.skirent.user.services;

import com.io.skirent.user.Client;
import com.io.skirent.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Client> findClientByEmail(String email) {
        return userRepository.findClientByEmail(email);
    }

    public void addNewClient(Client client) {
        if(userRepository.findClientByEmail(client.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already taken"); // TODO: custom exception or something, return something other than 500
        // TODO: regex here
        userRepository.save(client);
    }


}
