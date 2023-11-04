package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.FoodStorage;
import com.fzoo.zoomanagementsystem.repository.FoodStorageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodStorageService {
    @Autowired
    private FoodStorageRepository repository;

    public List<FoodStorage> getListFoodInStorage(String type) {
        return repository.findByType(type);
    }

    public void addMoreFoodToStorage( FoodStorage foodStorage) {
        Optional<FoodStorage> food = repository.findByName(foodStorage.getName().trim().toLowerCase());
        if(food.isPresent()){
            throw new IllegalStateException("Food was existed");
        }
        repository.save(foodStorage);
    }

    @Transactional
    public void updateFoodInStorage(int id, FoodStorage foodStorage) {
        if(foodStorage.getName()==null || foodStorage.getAvailable()==0.0f){
            throw new IllegalStateException("Value can not be blank");
        }
        FoodStorage food =repository.findById(id).orElseThrow();
        food.setName(foodStorage.getName());
        food.setAvailable(foodStorage.getAvailable());
        repository.save(food);
        }

    public void deleteFoodInStorage(int id) {
        FoodStorage foodStorage = repository.findById(id).orElseThrow();
        repository.delete(foodStorage);
    }

    public List<FoodStorage> getAll() {
        return repository.findAll();
    }
}
