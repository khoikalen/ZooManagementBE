package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.service.FoodService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}
