package com.io.skirent.equipment.repositories;

import com.io.skirent.equipment.Category;
import com.io.skirent.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query("SELECT e FROM Equipment e WHERE e.category = ?1")
    Optional<Equipment> findEquipmentByCategory(String category);


    @Query(value = "SELECT e FROM Equipment e " +
                    "WHERE e.name IN :names " +
                    "AND e.manufacturer IN :manufacturers " +
                    "AND e.size IN :sizes " +
                    "AND e.category IN :categories ")
    List<Equipment> findEquipmentWithFiltering(@Param("names") List<String> names,
                                               @Param("manufacturers") List<String> manufacturers,
                                               @Param("sizes") List<String> sizes,
                                               @Param("categories") List<Category> categories);




}
