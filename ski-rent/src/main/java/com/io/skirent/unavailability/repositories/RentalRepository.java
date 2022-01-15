package com.io.skirent.unavailability.repositories;

import com.io.skirent.unavailability.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {


}
