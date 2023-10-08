package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.repository.FoodRepository;
import com.fzoo.zoomanagementsystem.repository.MealRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private MealRepository mealRepository;

    Set<Food> setFood;
    public void addFood(Food food) {

        if(setFood==null){
            setFood=new HashSet<>();
        }
        setFood.add(food);


    }

    public Set<Food> getListFood(){
        return setFood;
    }

    public void clear(){
        setFood.clear();
    }


        public List<Food> getFoodInMeal(String name) {
        int id = mealRepository.findIdByName(name);
        List<Food> foodList = foodRepository.findFoodByMealId(id);
        return foodList;
    }

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
