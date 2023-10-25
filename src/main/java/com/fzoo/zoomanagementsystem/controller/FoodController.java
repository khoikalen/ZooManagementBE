package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/food")
public class FoodController {

    @Autowired
    private FoodService service;

    @Operation(
            summary = "Create food",
            description = "Create food temporary"
    )
    @PostMapping
    public void addFood(@RequestBody Food food){
        service.addFood(food);

    }

    @Operation(
            summary = "List all foods",
            description = "List all foods from the collection"
    )
    @GetMapping()
    public ResponseEntity<Set<Food>> getListFood(){
        return ResponseEntity.ok(service.getSetFood());
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
            summary = "List all sick foods",
            description = "List all sick foods from the database"
    )
    @GetMapping(path = "/sick-meal/{animalID}")
    public FoodInMealResponse getFoodInSickMeal(@PathVariable("animalID") int id){
        return service.getFoodInSickMeal(id);
    }

//    @Operation(
//            summary = "List all foods",
//            description = "List all  foods from the database"
//    )
//    @GetMapping(path = "/all/{cageID}")
//    public List<Food> getAllFood(@PathVariable("cageID") int id){
//        return service.getAllFoodInMealCage(id);
//    }

    @Operation(
            summary = "Update food",
            description = "Update food from collection"
    )

    @PutMapping()
    public void updateFood(@RequestBody Food food){
        service.updateFood(food.getName(),food.getWeight());
    }

    @Operation(
            summary = "Delete food",
            description = "Delete food from collection"
    )
    @DeleteMapping()
    public void deleteFood(@RequestBody Food food){
        service.deleteFood(food.getName());
    }

}
