package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Food;
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

    @PostMapping()
    public void addFood(@RequestBody Food food){
        service.addFood(food);

    }

    @GetMapping()
    public ResponseEntity<Set<Food>> getListFood(){
        return ResponseEntity.ok(service.getListFood());
    }

    @GetMapping(path = "/meal")
    public List<Food> getFoodInMeal(@RequestBody Cage cage){
        return service.getFoodInMeal(cage.getName());
    }

    @PostMapping(path = "/clear")
    public void clear(){
        service.clear();
    }



    @PutMapping()
    public void updateFood(@RequestBody Food food){
        service.updateFood(food.getName(),food.getWeight());
    }



}
