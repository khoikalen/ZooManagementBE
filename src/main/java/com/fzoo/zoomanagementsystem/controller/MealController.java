package com.fzoo.zoomanagementsystem.controller;

import com.fzoo.zoomanagementsystem.dto.FoodInMealResponse;
import com.fzoo.zoomanagementsystem.dto.StaffMealResponse;
import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/meal")
public class MealController {
    @Autowired
    private MealService service;

    @Operation(
            summary = "Create daily meal",
            description = "Create daily meal if animal does not have meal"
    )
    @PostMapping(path = "/daily/{cageID}")
    public void createDailyMeal(
            @PathVariable("cageID") int id,
            @RequestParam String email){
        service.createDailyMeal(id, email);
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
            summary = "Confirm meal everyday",
            description = ""
    )
    @PostMapping(path = "/{mealId}")
    public void confirmMeal(@PathVariable("mealId") int id,
                            @RequestParam String email
    ){
        service.confirmMeal(id,email);
    }


//    @Operation(
//            summary = "Get meal for staff",
//            description = ""
//    )
//    @GetMapping(path = "cage/{cageId}")
//    public StaffMealResponse getMealInStaff(@PathVariable("cageId") int id
//    ){
//        return service.staffMealResponses(id);
//    }






    //    @Operation(
//            summary = "Add meal",
//            description = "add meal to the database"
//    )
//    @GetMapping
//    public void saveMeal(){
//         service.saveMeal();
//    }

//    @Operation(
//            summary = "Create all daily meal",
//            description = "Create all daily meal in the same time"
//    )
//    @PostMapping(path = "/all/{emailExpert}")
//    public void createAllMeal(@PathVariable("emailExpert") String email){
//        service.createAllMeal(email);
//    }



//    @Operation(
//            summary = "Add new food to meal",
//            description = "Add new food to meal "
//    )
//    @PostMapping(path = "/new-food/{mealId}")
//    public void addMoreFood(@PathVariable("mealId") int id,
//                            @RequestBody Food food
//                            ){
//        service.addMoreFood(id,food);
//    }


}
