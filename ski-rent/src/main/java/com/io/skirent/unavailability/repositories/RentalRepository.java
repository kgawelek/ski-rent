package com.io.skirent.unavailability.repositories;

import com.io.skirent.equipment.Equipment;
import com.io.skirent.unavailability.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r WHERE (r.from < ?1 AND r.to > ?1) OR (r.from < ?2 AND r.to > ?2) OR (r.from > ?1 AND r.to < ?2)")
    List<Rental> getRentalsByDate(LocalDate dateFrom, LocalDate dateTo);
}
