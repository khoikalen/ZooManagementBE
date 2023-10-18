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
    int findIdByName(String name);

//    @Query(value = "SELECT m.id FROM Meal m WHERE m.cage_id = :id ")
//    int findIdByCageId(int id);




}
