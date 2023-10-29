package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.dto.MealInCageResponse;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FoodService {


    private final FoodRepository foodRepository;
    private final MealRepository mealRepository;
    private final CageRepository cageRepository;
    private final AnimalRepository animalRepository;

    private final FoodStorageRepository foodStorageRepository;

    public static Set<Food> setFood;

    public Set<Food> setFood(){
        return setFood;
    }


    public void addFood(Food food) {
        if(food.getName()==null||food.getWeight()==0.0f){
            throw new IllegalStateException("Value can not be blank");
        }
        if(setFood==null){
            setFood=new HashSet<>();
        }
        FoodStorage foodStorage = foodStorageRepository.findByName(food.getName())
                .orElseThrow(()-> new IllegalStateException("Does not have food"));
        if(food.getWeight()<0){
            throw new IllegalStateException("Can not input negative value");
        }else if(food.getWeight()>foodStorage.getAvailable()){
            throw new IllegalStateException("Does not have enough food");
        }
        setFood.add(food);


    }

    public Set<Food> getSetFood(){
        return setFood;
    }

    public void clear(){
        setFood.clear();
    }



    public FoodInMealResponse getFoodInDailyMeal(int id) {
        Cage cage = cageRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        Integer mealId = mealRepository.findIdByName(cage.getName());
        if(mealId==null){
            throw new IllegalStateException("Not have food in this meal");
        }
        List<Food>foodList = foodRepository.findFoodByMealId(mealId);
        FoodInMealResponse mealResponse = FoodInMealResponse.builder()
                .id(mealId)
                .name(cage.getName()+" meal")
                .cageId(cage.getId())
                .haveFood(foodList)
                .build();
        return mealResponse;
    }

    public FoodInMealResponse getFoodInSickMeal(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have animal"));
        Integer mealId = mealRepository.findIdByName(animal.getName());
        if(mealId==null){
            throw new IllegalStateException("Not have food in this meal");
        }
        List<Food>foodList = foodRepository.findFoodByMealId(mealId);
        FoodInMealResponse mealResponse = FoodInMealResponse.builder()
                .id(mealId)
                .name(animal.getName()+" sick meal")
                .cageId(animal.getId())
                .haveFood(foodList)
                .build();
        return mealResponse;
    }


    public MealInCageResponse getAllFoodInMealCage(int id){
        Cage cage = cageRepository.findById(id).orElseThrow();
        List<Meal> meals = mealRepository.findByCageId(id);

        if(meals.isEmpty()){
            throw new IllegalStateException("Not have food in this meal");
        }

        List<Food>foodList = new ArrayList<>();




        MealInCageResponse cageResponse = MealInCageResponse.builder()
                .id(cage.getId())
                .cageName(cage.getName())

                .build();

        return cageResponse;

    }

    @Transactional
    public void updateFood(String name, float weight) {
        if(name==null||weight==0.0f){
            throw new IllegalStateException("Value can not be blank");

        }
        for (Food food:setFood
             ) {
            if(food.getName().equals(name)){
                food.setWeight(weight);
            }
        }
    }

    public void deleteFood(String name) {
        for (Food food:setFood
        ) {
            if(food.getName().equals(name)){
                setFood.remove(food);
            }
        }
    }



}
