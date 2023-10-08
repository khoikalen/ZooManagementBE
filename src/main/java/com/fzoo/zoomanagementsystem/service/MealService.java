package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.FoodStorage;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.FoodStorageRepository;
import com.fzoo.zoomanagementsystem.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private CageRepository cageRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    private FoodStorageRepository foodStorageRepository;
    public Meal currentMeal;
    public void createMeal(String name) {

        Optional<Meal> optionalMeal = mealRepository.findByName(name);
        if(optionalMeal.isPresent()){
            throw new IllegalStateException("Meal was created");
        }
        Meal meal = Meal
                .builder()
                .cageInfo(cageRepository.findByName(name).get())
                .name(name + " meal")
                .build();
        currentMeal = meal;
    }

    public void saveMeal() {

        for (Food food:foodService.getListFood()
             ) {
            Optional<FoodStorage> foodStorage = foodStorageRepository.findAvailableByName(food.getName());
            if(food.getWeight()>foodStorage.get().getAvailable()){
                throw new IllegalStateException("does not have enough quantity");
            }
            foodStorage.get().setAvailable(foodStorage.get().getAvailable() - food.getWeight());
        }
        currentMeal.setHaveFood(foodService.getListFood());
        mealRepository.save(currentMeal);
        foodService.clear();

    }




}
