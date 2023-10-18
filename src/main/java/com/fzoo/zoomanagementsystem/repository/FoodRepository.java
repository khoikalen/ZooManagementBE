package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Integer> {

    @Query("SELECT A FROM Food A " +
            "INNER JOIN FoodInMeal B ON B.foodId = A.id " +
            "WHERE B.mealId = :id")
    List<Food> findFoodByMealId(int id);
}
