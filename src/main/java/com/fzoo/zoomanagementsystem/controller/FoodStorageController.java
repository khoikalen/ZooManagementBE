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
    public List<FoodStorage> getFoodInStorage(@PathVariable String type){
        return service.getListFoodInStorage(type);
    }


}
