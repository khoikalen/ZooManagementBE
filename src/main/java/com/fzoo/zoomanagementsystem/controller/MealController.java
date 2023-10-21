package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/meal")
public class MealController {
    @Autowired
    private MealService service;

    @Operation(
            summary = "Create a daily meal",
            description = "Create a daily meal in collection"
    )
    @PostMapping(path = "/daily/{cageID}")
    public void createDailyMeal(
            @PathVariable("cageID") int id){
        service.createDailyMeal(id);
    }
    @Operation(
            summary = "Create a sick meal",
            description = "Create a sick meal in collection"
    )
    @PostMapping(path = "/sick/{animalID}")
    public void createSickMeal(@PathVariable("animalID") int id){
        service.createSickMeal(id);
    }

    @Operation(
            summary = "Add meal",
            description = "add meal to the database"
    )
    @GetMapping
    public void saveMeal(){
         service.saveMeal();
    }


    @Operation(
            summary = "Update food ",
            description = "Update food in meal"
    )
    @PutMapping(path = "{foodID}")
    public void updateMeal(
            @PathVariable("foodID") int id,
            @RequestBody Food food){
        service.update(id,food.getName(),food.getWeight());
    }

    @Operation(
            summary = "Delete meal ",
            description = "Delete meal in database"
    )
    @DeleteMapping(path = "{mealID}")
    public void deleteMeal(@PathVariable ("mealID") int id){
        service.deleteMeal(id);
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
            summary = "Create all daily meal",
            description = "Create all daily meal in the same time"
    )
    @PostMapping(path = "/all/{emailExpert}")
    public void createAllMeal(@PathVariable("emailExpert") String email){
        service.createAllMeal(email);
    }

}
