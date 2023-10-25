package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal,Integer> {
    @Query(value = "SELECT m FROM Meal m WHERE m.name LIKE %:name% ")
    Optional<Meal> findByName(String name);

    @Query(value = "SELECT m.id FROM Meal m WHERE m.name LIKE %:name% ")
    Integer findIdByName(String name);

    @Query("SELECT a.id FROM Meal a WHERE a.cageId = :id AND a.name NOT LIKE '%sick%'")
    Integer findIdByCageIdAndNameNotContaining(int id);


    List<Meal> findByCageId(int id);
}
