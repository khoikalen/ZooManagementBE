package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FoodRepository extends JpaRepository<Food,Integer> {

    @Query("SELECT A FROM Food A " +
            "INNER JOIN FoodInMeal B ON B.foodId = A.id " +
            "WHERE B.mealId = :id")
    Set<Food> findFoodByMealId(int id);


    @Query("SELECT A FROM Food A " +
            "INNER JOIN FoodInMeal B ON B.foodId = A.id " +
            "WHERE B.mealId in :id")
    List<Food> findAllFoodByMealId(List<Integer> id);

@Query("select e FROM Food e WHERE e.id in :foodId")
    List<Food> findById(List<Integer> foodId);
}
