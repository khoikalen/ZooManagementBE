package com.fzoo.zoomanagementsystem.controller;


import com.fzoo.zoomanagementsystem.model.FoodStorage;
import com.fzoo.zoomanagementsystem.service.FoodStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/food-storage")
public class FoodStorageController {
    @Autowired
    private FoodStorageService service;
    @GetMapping(path = "{type}")
    public List<FoodStorage> getFoodInStorage(@PathVariable String type){
        return service.getListFoodInStorage(type);
    }


}
