package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.dto.FoodStatisticResponse;
import com.fzoo.zoomanagementsystem.dto.StaffMealResponse;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
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



    public void addFood(int id,Food foodRequest) {

        if(foodRequest.getName()==null||foodRequest.getWeight()==0.0f){
            throw new IllegalStateException("Value can not be blank");
        }
        FoodStorage foodStorage = foodStorageRepository.findByName(foodRequest.getName())
                .orElseThrow(()-> new IllegalStateException("Does not have food in food storage"));
        if(foodRequest.getWeight()<0){
            throw new IllegalStateException("Can not input negative value");
        }else if(foodRequest.getWeight()>foodStorage.getAvailable()){
            throw new IllegalStateException("Does not have enough "+foodRequest.getName());
        }
        String exist = null;
        int foodId = 0;
        Set<Food>foodList = foodRepository.findFoodByMealId(id);
        for (Food foodExist:foodList
             ) {
                if(foodRequest.getName().equals(foodExist.getName())){
                     exist = foodExist.getName();
                     foodId=foodExist.getId();
                }
        }
        FoodInMeal foodInMeal = null;
        if(exist== null){
            foodRepository.save(foodRequest);
            foodInMeal = FoodInMeal.builder()
                    .foodId(foodRequest.getId())
                    .mealId(id)
                    .build();
            foodInMealRepository.save(foodInMeal);
        }else{
             Food food = foodRepository.findById(foodId).orElseThrow();
             food.setWeight(food.getWeight()+foodRequest.getWeight());
             foodRepository.save(food);
        }


    }




    public FoodInMealResponse getFoodInDailyMeal(int id) {
        Cage cage = cageRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
        Optional<Meal> meal = mealRepository.findFirst1ByNameOrderByDateTimeDesc(cage.getName()+" meal");
        FoodInMealResponse mealResponse = new FoodInMealResponse();
        if(meal.isEmpty()){
            mealResponse = null;
            return mealResponse;
        }
        Set<Food>foodList = foodRepository.findFoodByMealId(meal.get().getId());
             mealResponse = FoodInMealResponse.builder()
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
        FoodInMealResponse mealResponse = new FoodInMealResponse();
        if(meal.isEmpty()){
            mealResponse = null;
            return mealResponse;
        }
        Set<Food>foodList = foodRepository.findFoodByMealId(meal.get().getId());
         mealResponse = FoodInMealResponse.builder()
                .id(meal.get().getId())
                .name(animal.getName()+" sick meal")
                .cageId(animal.getId())
                .dateTime(meal.get().getDateTime())
                .haveFood(foodList)
                .build();
        return mealResponse;
    }



    public StaffMealResponse staffMealResponses (int id){
        Cage cage = cageRepository.findById(id).orElseThrow();
        List<FoodInMealResponse> foodInMeal = new ArrayList<>();
        FoodInMealResponse meal = getFoodInDailyMeal(id);
        if(meal!=null){
            foodInMeal.add(meal);
        }
        List<Animal>animals = animalRepository.findBycageId(id);
        for (Animal animal:animals
        ) {
            meal = getFoodInSickMeal(animal.getId());
            if(meal!=null){
                foodInMeal.add(meal);
            }
        }
        StaffMealResponse staffMealResponse =StaffMealResponse.builder()
                .cageId(id)
                .name(cage.getName())
                .meal(foodInMeal)
                .build();
        return staffMealResponse;
    }



//    public List<FoodStatisticResponse> foodStatisticResponses(){
//        List<Integer> mealId = mealRepository.
//
//
//        return null;
//    }








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
