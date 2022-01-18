package com.io.skirent.equipment.repositories;

import com.io.skirent.equipment.Category;
import com.io.skirent.equipment.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceRepository extends JpaRepository<Prices, Long> {
    @Query("SELECT p FROM Prices p WHERE p.category = ?1")
    List<Prices> findPricesByCategory(Category category);
}
