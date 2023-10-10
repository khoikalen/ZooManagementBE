package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.service.FoodService;
import jakarta.persistence.Entity;
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

    @PostMapping
    public void addFood(@RequestBody Food food){
        service.addFood(food);

    }

    @GetMapping()
    public ResponseEntity<Set<Food>> getListFood(){
        return ResponseEntity.ok(service.getListFood());
    }

    @GetMapping(path = "/daily-meal/{cageID}")
    public List<Food> getFoodInDailyMeal(@PathVariable("cageID") int id){
        return service.getFoodInDailyMeal(id);
    }

    @GetMapping(path = "/sick-meal/{animalID}")
    public List<Food> getFoodInSickMeal(@PathVariable("animalID") int id){
        return service.getFoodInSickMeal(id);
    }

    @PostMapping(path = "/clear")
    public void clear(){
        service.clear();
    }

    @PutMapping()
    public void updateFood(@RequestBody Food food){
        service.updateFood(food.getName(),food.getWeight());
    }

    @PostMapping("/delete")
    public void deleteFood(@RequestBody Food food){
        service.deleteFood(food.getName());
    }

}
