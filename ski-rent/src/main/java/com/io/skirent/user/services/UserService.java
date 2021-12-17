package com.io.skirent.user.services;

import com.io.skirent.user.Client;
import com.io.skirent.user.exceptions.*;
import com.io.skirent.user.models.ClientDTO;
import com.io.skirent.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Pattern;

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

    public void addNewClient(ClientDTO clientDTO)
            throws EmailTakenException, DateTimeParseException, InvalidNameException, InvalidSurnameException, InvalidDateException, InvalidEmailException, InvalidPasswordException {

        if(clientDTO.getName().isBlank() || clientDTO.getName().trim().length() < 3)
            throw new InvalidNameException("Invalid name");
        if(clientDTO.getSurname().isBlank()  || clientDTO.getSurname().trim().length() < 3)
            throw new InvalidSurnameException("Invalid surname");

        LocalDate dob = LocalDate.parse(clientDTO.getBirth());
        if(dob.isAfter(LocalDate.now()))
            throw new InvalidDateException("Invalid date");

        if(!Pattern.compile("^(.+)@(\\S+)$").matcher(clientDTO.getEmail()).matches())
            throw new InvalidEmailException("Invalid email");
        if(userRepository.findClientByEmail(clientDTO.getEmail()).isPresent())
            throw new EmailTakenException("Email already taken");

        if(clientDTO.getPassword().isBlank()
            || clientDTO.getPassword().length() < 8
            || clientDTO.getPassword().length() > 64)
            throw new InvalidPasswordException("Invalid password");

        Client client = new Client();
        client.setId(0L); // set mock ID to avoid null pointer, will be calculated by JPA/DB
        client.setName(clientDTO.getName().trim() + " " + clientDTO.getSurname().trim()); // trim removes leading and trailing whitespaces
        client.setDob(dob);
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());

        userRepository.save(client);

    }


}
