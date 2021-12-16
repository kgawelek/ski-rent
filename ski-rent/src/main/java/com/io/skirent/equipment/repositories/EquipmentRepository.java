package com.io.skirent.equipment.repositories;

import com.io.skirent.equipment.Category;
import com.io.skirent.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query("SELECT e FROM Equipment e WHERE e.category = ?1")
    Optional<Equipment> findEquipmentByCategory(String category);
}
