package com.io.skirent.user.services;

import com.io.skirent.user.Client;
import com.io.skirent.user.models.ClientDTO;
import com.io.skirent.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Client> findClientByEmail(String email) {
        return userRepository.findClientByEmail(email);
    }

    public void addNewClient(ClientDTO clientDTO) throws IllegalArgumentException {
        if(userRepository.findClientByEmail(clientDTO.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already taken"); // TODO: custom exception (?)

        try {
            LocalDate dob = LocalDate.parse(clientDTO.getBirth());
            if(dob.isAfter(LocalDate.now()))
                throw new IllegalArgumentException("Invalid date"); // TODO: custom exception (?)

            // TODO: regex here ??? probably not needed as we have annotations
            Client client = new Client();
            client.setId(0L); // set mock ID to avoid null pointer, will be calculated by JPA/DB
            client.setName(clientDTO.getName().trim() + " " + clientDTO.getSurname().trim()); // trim removes leading and trailing whitespaces
            client.setDob(dob);
            client.setEmail(clientDTO.getEmail());
            client.setPassword(clientDTO.getPassword());

            userRepository.save(client);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing date"); // TODO: custom exception (?)
        }
    }


}
