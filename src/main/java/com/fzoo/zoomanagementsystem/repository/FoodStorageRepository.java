package com.fzoo.zoomanagementsystem.repository;

import com.fzoo.zoomanagementsystem.model.FoodStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodStorageRepository extends JpaRepository<FoodStorage,Integer> {
    List<FoodStorage> findByType(String type);

    Optional<FoodStorage> findAvailableByName(String name);
}
