package com.fzoo.zoomanagementsystem.controller;


import com.fzoo.zoomanagementsystem.model.FoodStorage;
import com.fzoo.zoomanagementsystem.service.FoodStorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/food-storage")
public class FoodStorageController {
    @Autowired
    private FoodStorageService service;

    @Operation(
            summary = "List all food by type",
            description = "List all food by type from the database"
    )
    @GetMapping(path = "{type}")
    public List<FoodStorage> getFoodInStorage(@PathVariable("type") String type){
        return service.getListFoodInStorage(type);
    }


    @Operation(
            summary = "Add more food to storage",
            description = ""
    )
    @PostMapping()
    public void addFoodToStorage(
                                @RequestBody FoodStorage foodStorage
        ){
         service.addMoreFoodToStorage(foodStorage);
    }


    @Operation(
            summary = "Update food in storage",
            description = ""
    )
    @PutMapping(path = "{foodID}")
    public void UpdateFoodInStorage(@PathVariable("foodID") int id,
                                 @RequestBody FoodStorage foodStorage
    ){
        service.updateFoodInStorage(id,foodStorage);
    }

    @Operation(
            summary = "Delete food in storage",
            description = ""
    )
    @DeleteMapping(path = "{foodID}")
    public void DeleteFoodInStorage(@PathVariable("foodID") int id
    ){
        service.deleteFoodInStorage(id);
    }


    @Operation(
            summary = "List all food by type",
            description = "List all food by type from the database"
    )
    @GetMapping()
    public List<FoodStorage> getAllFoodInStorage(){
        return service.getAll();
    }


}

