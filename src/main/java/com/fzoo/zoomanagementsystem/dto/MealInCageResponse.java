package com.fzoo.zoomanagementsystem.dto;

import com.fzoo.zoomanagementsystem.model.Food;
import com.fzoo.zoomanagementsystem.model.Meal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealInCageResponse {
    private int id;
    private String cageName;
    private List<FoodInMealResponse> haveMeal;



}
