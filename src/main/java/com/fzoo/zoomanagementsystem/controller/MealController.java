package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/meal")
public class MealController {
    @Autowired
    private MealService service;

    @PostMapping()
    public void createMeal(@RequestBody Cage cage){
        service.createMeal(cage.getName());
    }

    @GetMapping
    public void checkMeal(){
         service.saveMeal();
    }



}
