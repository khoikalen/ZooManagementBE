package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.dto.MealInCageResponse;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FoodService {


    private final FoodRepository foodRepository;
    private final MealRepository mealRepository;
    private final CageRepository cageRepository;
    private final AnimalRepository animalRepository;
    private final FoodInMealRepository foodInMealRepository;

    private final FoodStorageRepository foodStorageRepository;



    public void addFood(int id,Food food) {

        if(food.getName()==null||food.getWeight()==0.0f){
            throw new IllegalStateException("Value can not be blank");
        }
        FoodStorage foodStorage = foodStorageRepository.findByName(food.getName())
                .orElseThrow(()-> new IllegalStateException("Does not have food in food storage"));
        if(food.getWeight()<0){
            throw new IllegalStateException("Can not input negative value");
        }else if(food.getWeight()>foodStorage.getAvailable()){
            throw new IllegalStateException("Does not have enough "+food.getName());
        }
        foodRepository.save(food);
        FoodInMeal foodInMeal = FoodInMeal.builder()
                .foodId(food.getId())
                .mealId(id)
                .build();
        foodInMealRepository.save(foodInMeal);
    }




    public FoodInMealResponse getFoodInDailyMeal(int id) {
        Cage cage = cageRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        Optional<Meal> meal = mealRepository.findFirst1ByNameOrderByDateTimeDesc(cage.getName()+" meal");
        if(meal.isEmpty()){
            throw new IllegalStateException("Not have meal");
        }
        Set<Food>foodList = foodRepository.findFoodByMealId(meal.get().getId());
        FoodInMealResponse mealResponse = FoodInMealResponse.builder()
                .id(meal.get().getId())
                .name(cage.getName()+" meal")
                .cageId(cage.getId())
                .dateTime(meal.get().getDateTime())
                .haveFood(foodList)
                .build();
        return mealResponse;
    }

    public FoodInMealResponse getFoodInSickMeal(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have animal"));
        Optional<Meal> meal = mealRepository.findFirst1ByNameOrderByDateTimeDesc(animal.getName()+" sick meal");

        if(meal.isEmpty()){
            throw new IllegalStateException("Not have meal");
        }
        Set<Food>foodList = foodRepository.findFoodByMealId(meal.get().getId());
        FoodInMealResponse mealResponse = FoodInMealResponse.builder()
                .id(meal.get().getId())
                .name(animal.getName()+" sick meal")
                .cageId(animal.getId())
                .dateTime(meal.get().getDateTime())
                .haveFood(foodList)
                .build();
        return mealResponse;
    }
















//    public MealInCageResponse getAllFoodInMealCage(int id){
//        Cage cage = cageRepository.findById(id).orElseThrow();
//        List<Meal> meals = mealRepository.findByCageId(id);
//
//        if(meals.isEmpty()){
//            throw new IllegalStateException("Not have food in this meal");
//        }
//
//        List<Food>foodList = new ArrayList<>();
//        MealInCageResponse cageResponse = MealInCageResponse.builder()
//                .id(cage.getId())
//                .cageName(cage.getName())
//
//                .build();
//
//        return cageResponse;
//    }

//    @Transactional
//    public void updateFood(String name, float weight) {
//        if(name==null||weight==0.0f){
//            throw new IllegalStateException("Value can not be blank");
//
//        }
//        for (Food food:setFood
//             ) {
//            if(food.getName().equals(name)){
//                food.setWeight(weight);
//            }
//        }
//    }
//
//    public void deleteFood(String name) {
//        for (Food food:setFood
//        ) {
//            if(food.getName().equals(name)){
//                setFood.remove(food);
//            }
//        }
//    }



}
