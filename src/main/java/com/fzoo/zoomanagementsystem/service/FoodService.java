package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.dto.FoodStatisticResponse;
import com.fzoo.zoomanagementsystem.dto.ItemMapper;
import com.fzoo.zoomanagementsystem.dto.StaffMealResponse;
import com.fzoo.zoomanagementsystem.exception.NegativeValueException;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {


    private final FoodRepository foodRepository;
    private final MealRepository mealRepository;
    private final CageRepository cageRepository;
    private final AnimalRepository animalRepository;
    private final FoodInMealRepository foodInMealRepository;
    private final ExpertRepository expertRepository;

    private final FoodStorageRepository foodStorageRepository;

    @Autowired
    ItemMapper itemMapper;

    public void addFood(int id, Food foodRequest) throws NegativeValueException{

        if (foodRequest.getName() == null || foodRequest.getQuantity() == 0.0f) {
            throw new IllegalStateException("Value can not be blank");
        }
        FoodStorage foodStorage = foodStorageRepository.findByName(foodRequest.getName())
                .orElseThrow(() -> new IllegalStateException("Does not have food in food storage"));
        if (foodRequest.getQuantity() < 0) {
            throw new NegativeValueException();
        } else if (foodRequest.getQuantity() > foodStorage.getAvailable().floatValue()) {
            throw new IllegalStateException("Does not have enough " + foodRequest.getName());
        }
        String exist = null;
        int foodId = 0;
        Set<Food> foodList = foodRepository.findFoodByMealId(id);
        for (Food foodExist : foodList
        ) {
            if (foodRequest.getName().equals(foodExist.getName())) {
                exist = foodExist.getName();
                foodId = foodExist.getId();
            }
        }
        FoodInMeal foodInMeal = null;
        if (exist == null) {
            foodRepository.save(foodRequest);
            foodInMeal = FoodInMeal.builder()
                    .foodId(foodRequest.getId())
                    .mealId(id)
                    .build();
            foodInMealRepository.save(foodInMeal);
        } else {
            Food food = foodRepository.findById(foodId).orElseThrow();
            food.setQuantity(food.getQuantity() + foodRequest.getQuantity());
            foodRepository.save(food);
        }

    }


    public FoodInMealResponse getFoodInDailyMeal(int id) {
        Cage cage = cageRepository.findCageById(id);
        Optional<Meal> meal = mealRepository.findFirst1ByCageIdOrderByDateTimeDesc(id);
        Expert expert = expertRepository.findExpertById(meal.get().getExpertId());
        FoodInMealResponse mealResponse = new FoodInMealResponse();
        if (meal.isEmpty()) {
            mealResponse = null;
            return mealResponse;
        }
        Set<Food> foodList = foodRepository.findFoodByMealId(meal.get().getId());
        mealResponse = FoodInMealResponse.builder()
                .id(meal.get().getId())
                .cageName(cage.getName())
                .expertEmail(expert.getEmail())
                .dateTime(meal.get().getDateTime())
                .haveFood(foodList)
                .build();
        return mealResponse;
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



    public List<FoodStatisticResponse> foodStatisticResponses() {
        List<Integer> mealId = mealRepository.findIAllId();
        List<Food> foodList = foodRepository.findAllFoodByMealId(mealId);
        Map<String, Food> groupedItems = foodList.stream()
                .collect(Collectors.toMap(Food::getName, item -> item, (existing, replacement) -> {
                    existing.setQuantity(existing.getQuantity() + replacement.getQuantity());
                    return existing;
                }));
        List<Food> distinctFood = new ArrayList<>(groupedItems.values());
        List<FoodStatisticResponse> responses = distinctFood.stream()
                .map(itemMapper::convertToFoodStatisticResponse)
                .collect(Collectors.toList());
        return responses;
    }


    @Transactional
    public void update(int id, String name, float quantity)throws NegativeValueException {
        Food food = foodRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("food with does not exits"));
        FoodStorage foodStorage = foodStorageRepository.findByName(name).orElseThrow(() ->
                new IllegalStateException("foodStorage with " + name + " does not exits"));
        if (name == null || quantity == 0.0f) {
            throw new IllegalStateException("Value can not be blank");
        }
        if (quantity > foodStorage.getAvailable().floatValue()) {
            throw new IllegalStateException("Does not have enough food " + name);
        }
        if (quantity < 0) {
            throw new NegativeValueException();
        }

        food.setQuantity(quantity);
        foodRepository.save(food);
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
//    public FoodInMealResponse getFoodInSickMeal(int id) {
//        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have animal"));
//        Optional<Meal> meal = mealRepository.findFirst1ByNameOrderByDateTimeDesc(animal.getName()+" sick meal");
//        FoodInMealResponse mealResponse = new FoodInMealResponse();
//        if(meal.isEmpty()){
//            mealResponse = null;
//            return mealResponse;
//        }
//        Set<Food>foodList = foodRepository.findFoodByMealId(meal.get().getId());
//         mealResponse = FoodInMealResponse.builder()
//                .id(meal.get().getId())
//                .name(animal.getName()+" sick meal")
//                .cageId(animal.getId())
//                .dateTime(meal.get().getDateTime())
//                .haveFood(foodList)
//                .build();
//        return mealResponse;
//    }


//    public StaffMealResponse staffMealResponses (int id){
//        Cage cage = cageRepository.findById(id).orElseThrow();
//        List<FoodInMealResponse> foodInMeal = new ArrayList<>();
//        FoodInMealResponse meal = getFoodInDailyMeal(id);
//        if(meal!=null){
//            foodInMeal.add(meal);
//        }
//        List<Animal>animals = animalRepository.findBycageId(id);
//        for (Animal animal:animals
//        ) {
//            meal = getFoodInSickMeal(animal.getId());
//            if(meal!=null){
//                foodInMeal.add(meal);
//            }
//        }
//        StaffMealResponse staffMealResponse =StaffMealResponse.builder()
//                .cageId(id)
//                .name(cage.getName())
//                .meal(foodInMeal)
//                .build();
//        return staffMealResponse;
//    }


}
