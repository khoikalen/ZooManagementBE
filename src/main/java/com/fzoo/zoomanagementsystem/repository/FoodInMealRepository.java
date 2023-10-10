package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.FoodInMeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodInMealRepository extends JpaRepository<FoodInMeal,Integer> {
//    void deleteByMealId(int id);
//
//    void deleteByFoodId(int id);
}
