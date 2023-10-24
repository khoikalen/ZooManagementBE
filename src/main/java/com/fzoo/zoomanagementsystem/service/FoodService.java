package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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


    public List<Food> getFoodInDailyMeal(int id) {
        Cage cage = cageRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        Integer mealId = mealRepository.findIdByName(cage.getName());
        if(mealId==null){
            throw new IllegalStateException("Not have food in this meal");
        }
        return foodRepository.findFoodByMealId(mealId);
    }

    public List<Food> getFoodInSickMeal(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have animal"));
        Integer mealId = mealRepository.findIdByName(animal.getName());
        if(mealId==null){
            throw new IllegalStateException("Not have food in this meal");
        }
        return foodRepository.findFoodByMealId(mealId);
    }


//    public List<Food> getAllFoodInMealCage(int id){
//        List<Food>foodList = getFoodInDailyMeal(id);
//        List<Integer> animalId = animalRepository.findIdBycageId(id);
//
//
//        return foodList;
//    }

    @Transactional
    public void updateFood(String name, float weight) {
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
