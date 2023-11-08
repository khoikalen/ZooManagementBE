package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.dto.FoodStatisticResponse;
import com.fzoo.zoomanagementsystem.dto.StaffMealResponse;
import com.fzoo.zoomanagementsystem.exception.NegativeValueException;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/food")
public class FoodController {

    @Autowired
    private FoodService service;

    @Operation(
            summary = "Create food",
            description = "Create food temporary"
    )
    @PostMapping(path = "{mealId}")
    public void addFood(@PathVariable("mealId")int id,
                        @RequestBody Food food
                        ) throws NegativeValueException {
        service.addFood(id,food);

    }


    @Operation(
            summary = "List all daily foods",
            description = "List all daily foods from the database"
    )
    @GetMapping(path = "/daily-meal/{cageID}")
    public FoodInMealResponse getFoodInDailyMeal(@PathVariable("cageID") int id){
        return service.getFoodInDailyMeal(id);
    }


    @Operation(
            summary = "Delete food in meal",
            description = "Delete food in meal in database"
    )
    @DeleteMapping("/food/{foodID}")
    public void deleteFoodInMeal(
            @PathVariable("foodID") int id
    ){
        service.deleteFood(id);
    }


    @Operation(
            summary = "Get all food",
            description = ""
    )
    @GetMapping()
    public List<FoodStatisticResponse> getAllFood(){
        return service.foodStatisticResponses();
    }




    @Operation(
            summary = "Update food ",
            description = "Update food in meal"
    )
    @PutMapping(path = "{foodID}")
    public void updateFood(
            @PathVariable("foodID") int id,
            @RequestBody Food food) throws NegativeValueException{
        service.update(id,food.getName(),food.getQuantity());
    }




//    @Operation(
//            summary = "List all foods",
//            description = "List all foods from the collection"
//    )
//    @GetMapping()
//    public ResponseEntity<Set<Food>> getListFood(){
//        return ResponseEntity.ok(service.getSetFood());
//    }

//    @Operation(
//            summary = "List all foods",
//            description = "List all foods from the database"
//    )
//    @GetMapping(path = "/all/{cageID}")
//    public MealInCageResponse getAllFood(@PathVariable("cageID") int id){
//        return service.getAllFoodInMealCage(id);
//    }

//    @Operation(
//            summary = "Update food",
//            description = "Update food from collection"
//    )
//
//    @PutMapping()
//    public void updateFood(@RequestBody Food food){
//        service.updateFood(food.getName(),food.getWeight());
//    }
//
//    @Operation(
//            summary = "Delete food",
//            description = "Delete food from collection"
//    )
//    @DeleteMapping()
//    public void deleteFood(@RequestBody Food food){
//        service.deleteFood(food.getName());
//    }

}
