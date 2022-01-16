package com.io.skirent.unavailability.repositories;

import com.io.skirent.unavailability.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    @Query("SELECT r FROM Repair r WHERE (r.from <= ?1 AND r.to >= ?1) OR (r.from <= ?2 AND r.to >= ?2) OR (r.from >= ?1 AND r.to <= ?2)")
    List<Repair> getRepairsByDate(LocalDate dateFrom, LocalDate dateTo);
}
