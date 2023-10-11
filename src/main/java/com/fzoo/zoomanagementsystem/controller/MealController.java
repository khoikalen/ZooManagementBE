package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.model.Cage;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.Meal;
import com.fzoo.zoomanagementsystem.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/meal")
public class MealController {
    @Autowired
    private MealService service;

    @PostMapping(path = "{cageID}")
    public void createMeal(
            @PathVariable("cageID") int id){
        service.createMeal(id);
    }

    @GetMapping
    public void saveMeal(){
         service.saveMeal();
    }


    @PutMapping(path = "{foodID}")
    public void updateMeal(
            @PathVariable("foodID") int id,
            @RequestBody Food food){
        service.update(id,food.getName(),food.getWeight());
    }



    @PostMapping(path = "/sick/{animalID}")
    public void createSickMeal(@PathVariable("animalID") int id){
            service.createSickMeal(id);
    }

//    @DeleteMapping()
//    public void deleteMeal(){
//        service.delete();
//    }
//
//
//    @DeleteMapping("{foodID}")
//    public void deleteFoodInMeal(
//            @PathVariable("foodID") int id
//    ){
//        service.deleteFood(id);
//    }

    @PostMapping(path = "/all")
    public void createAllMeal(){
        service.createAllMeal();
    }

}
