package com.io.skirent.user.repositories;

import com.io.skirent.user.Client;
import com.io.skirent.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select c from Client c where c.email = ?1")
    Optional<Client> findClientByEmail(String email);

    @Query("select c from Client c where c.id = ?1")
    Optional<Client> findClientById(Long id);
}
