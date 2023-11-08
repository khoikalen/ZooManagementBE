package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.exception.MealCreatedException;
import com.fzoo.zoomanagementsystem.exception.NegativeValueException;
import com.fzoo.zoomanagementsystem.exception.WrongMeasureException;
import com.fzoo.zoomanagementsystem.model.*;
import com.fzoo.zoomanagementsystem.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;

    private final CageRepository cageRepository;

    private final FoodService foodService;
    private final ExpertRepository expertRepository;

    private final FoodStorageRepository foodStorageRepository;
    private final FoodInMealRepository foodInMealRepository;
    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;

    boolean create = false;
    ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");


    public void createDailyMeal(int id, String email) throws MealCreatedException {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new IllegalStateException("does not have cage"));
        Expert expert = expertRepository.findExpertByEmail(email);
        Optional<Meal> optionalMeal = mealRepository.findFirst1ByCageIdOrderByDateTimeDesc(id);
        if (!create) {
            if (optionalMeal.isPresent()) {
                throw new MealCreatedException();
            }
        }
        Meal meal = Meal
                .builder()
                .name(cage.getName())
                .cageId(id)
                .dateTime(LocalDateTime.now(zone))
                .expertId(expert.getId())
                .build();
        mealRepository.save(meal);
        create = false;
    }

    public void confirmMeal(int id, String email) throws NegativeValueException, MealCreatedException, WrongMeasureException {
        boolean check = true;
        String checkName = "";
        create = true;
        Meal meal = mealRepository.findById(id).orElseThrow();
        Set<Food> foods = foodRepository.findFoodByMealId(id);
        createDailyMeal(id, email);
        for (Food food : foods
        ) {
            Optional<FoodStorage> foodStorage = foodStorageRepository.findAvailableById(food.getFoodStorageId());
            if (food.getMeasure().equals("gram") || food.getMeasure().equals("g")) {
                float exchange = (float) (food.getQuantity().floatValue() / 1000);
                if (exchange > foodStorage.get().getAvailable().floatValue()) {
                    if (food.getQuantity().floatValue() > foodStorage.get().getAvailable().floatValue()) {
                        check = false;
                        checkName += food.getName() + " and ";
                    }
                }
            } else {
                if (food.getQuantity().floatValue() > foodStorage.get().getAvailable().floatValue()) {
                    check = false;
                    checkName += food.getName() + " and ";
                }
            }
            if (!check) {
                throw new IllegalStateException("Does not have enough " + checkName);
            }
        }
        for (Food food : foods
        ) {
            FoodStorage foodStorage = foodStorageRepository.findAvailableById(food.getFoodStorageId()).orElseThrow(() -> (new IllegalStateException("Can not find that food in storage")));
            if (food.getMeasure().equals("gram") || food.getMeasure().equals("g")) {
                BigDecimal exchange = new BigDecimal((float) (food.getQuantity().floatValue() / 1000));
                foodStorage.setAvailable(foodStorage.getAvailable().subtract(exchange));
            } else {
                foodStorage.setAvailable(foodStorage.getAvailable().subtract(new BigDecimal(food.getQuantity().floatValue())));
            }
            FoodInMealResponse foodInMealResponse = foodService.getFoodInDailyMeal(meal.getCageId());
            foodService.addFood(foodInMealResponse.getId(), food);
        }
        create = false;
    }


//    public void deleteMeal(int id) {
//        boolean exist = mealRepository.existsById(id);
//        if (!exist) {
//            throw new IllegalStateException("does not have food");
//        }
//        List<Integer> listFoodId = foodInMealRepository.findIdByMealId(id);
//        foodInMealRepository.deleteByMealId(id);
//
//        for (int foodId : listFoodId
//        ) {
//            foodInMealRepository.deleteByFoodId(foodId);
//            foodRepository.deleteById(id);
//        }
//
//        mealRepository.deleteById(id);
//    }


//    public void createSickMeal(int id) {
//
//
//        Animal animal = animalRepository.findById(id).orElseThrow(()-> new IllegalStateException("does not have cage"));
//        Optional<Meal> optionalMeal = mealRepository.findFirst1ByNameOrderByDateTimeDesc(animal.getName() + " sick meal");
//        if(!create){
//            if(optionalMeal.isPresent()){
//                throw new IllegalStateException("Meal was created");
//            }
//        }
//        Meal meal = Meal
//                .builder()
//                .name(animal.getName() + " sick meal")
//                .cageId(id)
//                .dateTime(LocalDateTime.now(zone))
//                .type("Sick")
//                .build();
//        mealRepository.save(meal);
//        create = false;
//    }

//    public StaffMealResponse staffMealResponses (int id){
//        Cage cage = cageRepository.findById(id).orElseThrow();
//        List<FoodInMealResponse> foodInMeal = new ArrayList<>();
//        FoodInMealResponse meal = foodService.getFoodInDailyMeal(id);
//        foodInMeal.add(meal);
//        List<Animal>animals = animalRepository.findBycageId(id);
//        for (Animal animal:animals
//             ) {
//            meal = foodService.getFoodInSickMeal(animal.getId());
//            foodInMeal.add(meal);
//        }
//        StaffMealResponse staffMealResponse =StaffMealResponse.builder()
//                .cageId(id)
//                .name(cage.getName())
//                .meal(foodInMeal)
//                .build();
//        return staffMealResponse;
//    }


//    public void saveMeal() {
//        for (Food food:foodService.getSetFood()
//             ) {
//            Optional<FoodStorage> foodStorage = foodStorageRepository.findAvailableByName(food.getName());
//            if(food.getWeight()>foodStorage.get().getAvailable()){
//                throw new IllegalStateException("Does not have enough "+food.getName());
//            }
//            if(create){
//            foodStorage.get().setAvailable(foodStorage.get().getAvailable() - food.getWeight());
//            }
//        }
//        currentMeal.setHaveFood(foodService.getSetFood());
//        mealRepository.save(currentMeal);
//        foodService.clear();
//    }

//    public void createAllMeal(String email) {
//        List<Cage> listCage = cageRepository.findCagesByExpertEmail(email);
//        List<Integer> listMealId = new ArrayList<>();
//        List<Integer> foodId = new ArrayList<>();
//
//        for (Cage cage: listCage
//             ) {
//            int id = cage.getId();
//            Integer mealId = mealRepository.findIdByCageIdAndNameNotContaining(id);
//            if(mealId!=null){
//                listMealId.add(mealId);
//            }
//        }
//        for (int id:listMealId
//             ) {
//            foodId.addAll(foodInMealRepository.findIdByMealId(id));
//        }
//
//        List<Food> listFood = foodRepository.findById(foodId);
//        for (Food food:listFood
//             ) {
//            create =true;
//            update(food.getId(),food.getName(),food.getWeight());
//            create = false;
//        }
//    }

//    public void addMoreFood(int id, Food food) {
//        FoodStorage foodStorage = foodStorageRepository.findByName(food.getName()).orElseThrow(() ->
//                new IllegalStateException("foodStorage with "+ food.getName()+ " does not exits"));
//        if(food.getName()==null||food.getWeight()==0.0f){
//            throw new IllegalStateException("Value can not be blank");
//        }
//        if(food.getWeight()>foodStorage.getAvailable()){
//            throw new IllegalStateException("Does not have enough "+food.getName());
//        }if(food.getWeight() < 0){
//            throw new IllegalStateException("Does not input negative value");
//        }
//        Meal meal = mealRepository.findById(id).orElseThrow();
//        mealRepository.save(meal);
//        foodRepository.save(food);
//        FoodInMeal foodInMeal = FoodInMeal.builder()
//                .mealId(meal.getId())
//                .foodId(food.getId())
//                .build();
//        foodInMealRepository.save(foodInMeal);
//
//    }
}
