package com.io.skirent.unavailability.repositories;

import com.io.skirent.unavailability.CheckUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheckUpRepository extends JpaRepository<CheckUp, Long> {

    @Query("SELECT c FROM CheckUp c WHERE (c.from <= ?1 AND c.to >= ?1) OR (c.from <= ?2 AND c.to >= ?2) OR (c.from >= ?1 AND c.to <= ?2)")
    List<CheckUp> getCheckUpsByDate(LocalDate dateFrom, LocalDate dateTo);
}
