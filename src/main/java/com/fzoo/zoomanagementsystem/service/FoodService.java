package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.model.Animal;
import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.repository.AnimalRepository;
import com.fzoo.zoomanagementsystem.repository.CageRepository;
import com.fzoo.zoomanagementsystem.repository.FoodRepository;
import com.fzoo.zoomanagementsystem.repository.MealRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private CageRepository cageRepository;
    @Autowired
    private AnimalRepository animalRepository;

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
        public List<Food> getFoodInDailyMeal(int id) {
        Cage cage = cageRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        int mealId = mealRepository.findIdByName(cage.getName());
        List<Food> foodList = foodRepository.findFoodByMealId(mealId);
        return foodList;
    }

    public List<Food> getFoodInSickMeal(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have animal"));
        int mealId = mealRepository.findIdByName(animal.getName());
        List<Food> foodList = foodRepository.findFoodByMealId(mealId);
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
