package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.FoodInMeal;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodInMealRepository extends JpaRepository<FoodInMeal,Integer> {
    @Modifying
    @Transactional
//    @Query("DELETE FROM FoodInMeal e WHERE e.food_id = :id")
    void deleteByFoodId(@Param("id") int id);
    @Modifying
    @Transactional
//    @Query("DELETE FROM FoodInMeal e WHERE e.meal_id = :id")
    void deleteByMealId(int id);

    @Query("select e.foodId FROM FoodInMeal e WHERE e.mealId = :id")
    List<Integer> findIdByMealId(int id);



}
