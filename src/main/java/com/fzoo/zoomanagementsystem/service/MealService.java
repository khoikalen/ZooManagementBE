package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    private final CageRepository cageRepository;

    private final FoodService foodService;

    private final FoodStorageRepository foodStorageRepository;
    private final FoodInMealRepository foodInMealRepository;
    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;
    public Meal currentMeal;
    boolean create = false;


    public void createDailyMeal(int id) {

        Cage cage = cageRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        Optional<Meal> optionalMeal = mealRepository.findByName(cage.getName());
        if(optionalMeal.isPresent()){
            throw new IllegalStateException("Meal was created");
        }

            Meal meal = Meal
                    .builder()
                    .name(cage.getName() + " meal")
                    .cageId(id)
                    .build();
            currentMeal = meal;


    }

    public void createSickMeal(int id) {
        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        Optional<Meal> optionalMeal = mealRepository.findByName(animal.getName());
        if(optionalMeal.isPresent()){
            throw new IllegalStateException("Meal was created");
        }
        Meal meal = Meal
                .builder()
                .name(animal.getName() + " sick meal")
                .cageId(id)
                .haveFood(foodService.setFood())
                .build();
        currentMeal = meal;
    }

    public void saveMeal() {
        for (Food food:foodService.getSetFood()
             ) {
            Optional<FoodStorage> foodStorage = foodStorageRepository.findAvailableByName(food.getName());
            if(food.getWeight()>foodStorage.get().getAvailable()){
                throw new IllegalStateException("Does not have enough "+food.getName());
            }
//            foodStorage.get().setAvailable(foodStorage.get().getAvailable() - food.getWeight());
        }
        currentMeal.setHaveFood(foodService.getSetFood());
        mealRepository.save(currentMeal);
        foodService.clear();
    }

    @Transactional
    public void update(int id, String name, float weight) {
        Food food = foodRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("food with does not exits"));
        FoodStorage foodStorage = foodStorageRepository.findByName(name).orElseThrow(() ->
                new IllegalStateException("foodStorage with "+ name+ " does not exits"));
        if(name == null || weight == 0.0f){
            throw new IllegalStateException("Value can not be blank");
        }
        if(weight>foodStorage.getAvailable()){
            throw new IllegalStateException("Does not have enough food " +name);
        }if(weight < 0){
            throw new IllegalStateException("Does not input negative value");
        }

        food.setWeight(weight);
        if(create){
            foodStorage.setAvailable(foodStorage.getAvailable()-food.getWeight());
        }
        foodRepository.save(food);
    }



    @Transactional
    public void deleteFood(int id) {
       boolean exist = foodRepository.existsById(id);
       if(!exist){
           throw new IllegalStateException("does not have food");
       }
        foodInMealRepository.deleteByFoodId(id);
        foodRepository.deleteById(id);

    }

    public void createAllMeal(String email) {
        List<Cage> listCage = cageRepository.findCagesByExpertEmail(email);
        List<Integer> listMealId = new ArrayList<>();
        List<Integer> foodId = new ArrayList<>();

        for (Cage cage: listCage
             ) {
            int id = cage.getId();
            Integer mealId = mealRepository.findIdByCageIdAndNameNotContaining(id);
            if(mealId!=null){
                listMealId.add(mealId);
            }
        }
        for (int id:listMealId
             ) {
            foodId.addAll(foodInMealRepository.findIdByMealId(id));
        }

        List<Food> listFood = foodRepository.findById(foodId);
        for (Food food:listFood
             ) {
            create =true;
            update(food.getId(),food.getName(),food.getWeight());
            create = false;
        }
    }

    public void deleteMeal(int id) {
        boolean exist = mealRepository.existsById(id);
        if(!exist){
            throw new IllegalStateException("does not have food");
        }
        List<Integer> listFoodId = foodInMealRepository.findIdByMealId(id);
        foodInMealRepository.deleteByMealId(id);

        for (int foodId: listFoodId
             ) {
            foodInMealRepository.deleteByFoodId(foodId);
            foodRepository.deleteById(id);
        }

        mealRepository.deleteById(id);
    }


    public void addMoreFood(int id, Food food) {
        FoodStorage foodStorage = foodStorageRepository.findByName(food.getName()).orElseThrow(() ->
                new IllegalStateException("foodStorage with "+ food.getName()+ " does not exits"));
        if(food.getName()==null||food.getWeight()==0.0f){
            throw new IllegalStateException("Value can not be blank");
        }
        if(food.getWeight()>foodStorage.getAvailable()){
            throw new IllegalStateException("Does not have enough "+food.getName());
        }if(food.getWeight() < 0){
            throw new IllegalStateException("Does not input negative value");
        }
        Meal meal = mealRepository.findById(id).orElseThrow();
        mealRepository.save(meal);
        foodRepository.save(food);
        FoodInMeal foodInMeal = FoodInMeal.builder()
                .mealId(meal.getId())
                .foodId(food.getId())
                .build();
        foodInMealRepository.save(foodInMeal);

    }
}
