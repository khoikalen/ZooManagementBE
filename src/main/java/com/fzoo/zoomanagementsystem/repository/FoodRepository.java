package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Integer> {
}
